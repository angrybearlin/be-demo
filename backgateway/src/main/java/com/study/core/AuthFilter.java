package com.study.core;

import com.alibaba.fastjson.JSONObject;
import com.study.common.JwtUtil;
import com.study.common.ResultJson;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@ConfigurationProperties(prefix = "white.ignore")
public class AuthFilter implements GlobalFilter, Ordered {
    private List<String> urls;

    @Resource(name = "lkxRedisTemplate")
    private RedisTemplate<String, Object> redisTemplate;
    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 地址匹配工具
        AntPathMatcher pathMatcher = new AntPathMatcher();
        // 获取request和response
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().set("Content-type", "application/json;charset=utf-8");
        // 获取请求地址
        URI uri = request.getURI();
        String path = uri.getPath();
        // 匹配白名单 如果请求地址和白名单匹配，则通过验证
        for (String whiteUrl: urls) {
            if (pathMatcher.match(whiteUrl, path)) {
                return chain.filter(exchange);
            }
        }
        // 如果没匹配到 就要从请求头获取token
        String token = request.getHeaders().getFirst("token");
        // 如果token是空，未登录
        if (token == null) {
            return error(response, ResultJson.unlogin("未登录"));
        }
        // 如果有token 从token中解析用户id
        String userId = null;
        try {
            userId = JwtUtil.decode(token);
        } catch (Exception e) {
            e.printStackTrace();
            return error(response, ResultJson.unlogin("非法请求"));
        }
        // 到数据库中查询用户权限
        String key = "ums::login::" + userId;
        // 先判断是否存在这个key,如果不存在说明登录超时
        if (!redisTemplate.hasKey(key)) {
            return error(response, ResultJson.unlogin("登录超时，需要重新登录"));
        }
        // 如果key存在，则向后置30分钟
        redisTemplate.expire(key, 30, TimeUnit.MINUTES);
        // 取出数据
        Object obj = redisTemplate.opsForValue().get(key);
        // 反序列化为List
        List<String> urls = JSONObject.parseArray(obj.toString(), String.class);
        // 循环权限列表，如果当前请求和权限列表中任意一个地址匹配，则可以通过
        for (String url: urls) {
            if (pathMatcher.match(url, path)) {
                // 在请求参数上多拼接一个userId，让controller可以直接获取到登录用户的id
                String query = uri.getQuery();
                // 定义新的
                StringBuilder builder = new StringBuilder();
                if (StringUtils.isNotBlank(query)) {
                    builder.append(query).append("&");
                }
                builder.append("userId=" + userId);
                // uri中query只读，无法修改，必须重新构建一个uri
                URI newUri = UriComponentsBuilder.fromUri(uri).replaceQuery(builder.toString()).build().toUri();
                // request中uri是只读的，无法修改，必须重新构建一个新的request
                ServerHttpRequest newRequest = request.mutate().uri(newUri).build();
                // exchange中request是只读的，无法修改，必须重新构建一个新的exchange
                ServerWebExchange newExchange = exchange.mutate().request(newRequest).build();
                return chain.filter(newExchange);
            }
        }
        return error(response, ResultJson.unlogin("没有权限"));
    }

    private Mono<Void> error(ServerHttpResponse response, ResultJson resultJson) {
        DataBuffer dataBuffer = response.bufferFactory().wrap(JSONObject.toJSONString(resultJson).getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Flux.just(dataBuffer));
    }
    @Override
    public int getOrder() {
        return 0;
    }
}

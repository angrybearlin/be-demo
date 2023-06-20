package com.study.util;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.study.common.BaseEntity;

import java.util.HashMap;
import java.util.Map;

public class Coding {
    public static void main(String[] args) {
        // 生成的表名
        String table = "ums_role_resource";
        String project = "admin";
        // 生成路径的配置
        String parent = System.getProperty("user.dir");
        // 定义entity的路径
        String entityPath = parent + "/entity/src/main/java/com/study/entity";
        // 定义mapper接口的生成路径
        String mapperPath = parent + "/" + project + "/src/main/java/com/study/mapper";
        String xmlPath = parent + "/" + project + "/src/main/resources/com/study/mapper";
        // 定义service接口的生成路径
        String servicePath = parent + "/" + project + "/src/main/java/com/study/service";
        String serviceImplPath = parent + "/" + project + "/src/main/java/com/study/service/impl";
        String controllerPath = parent + "/" + project + "/src/main/java/com/study/controller";
        // 创建生成器对象
        AutoGenerator mpg = new AutoGenerator();
        // 创建全局配置对象
        GlobalConfig gc = new GlobalConfig();
        // 配置生成代码之后不打开目录
        gc.setOpen(false);
        // 配置作者
        gc.setAuthor("linkexuan");
        // 配置service接口名字
        gc.setServiceName("%sService");
        mpg.setGlobalConfig(gc);
        // 创建数据源配置对象
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUrl("jdbc:mysql://127.0.0.1:3307/shop?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true");
        dsc.setUsername("root");
        dsc.setPassword("123456");
        mpg.setDataSource(dsc);
        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.study");
        // 配置路径
        Map<String, String> pathInfo = new HashMap<>();
        pathInfo.put("entity_path", entityPath);
        pathInfo.put("mapper_path", mapperPath);
        pathInfo.put("xml_path", xmlPath);
        pathInfo.put("service_path", servicePath);
        pathInfo.put("service_impl_path", serviceImplPath);
        pathInfo.put("controller_path", controllerPath);
        pc.setPathInfo(pathInfo);

        mpg.setPackageInfo(pc);
        // 策略配置
        StrategyConfig sc = new StrategyConfig();
        sc.setNaming(NamingStrategy.underline_to_camel);
        sc.setColumnNaming(NamingStrategy.underline_to_camel);
        sc.setRestControllerStyle(true);
        sc.setEntityLombokModel(true);
        sc.setSuperEntityClass(BaseEntity.class);
        sc.setSuperEntityColumns("id");
        sc.setControllerMappingHyphenStyle(false);
        sc.setInclude(table);
        mpg.setStrategy(sc);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        // 调用生成代码方法
        mpg.execute();
    }
}

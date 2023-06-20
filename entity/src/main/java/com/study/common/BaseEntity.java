package com.study.common;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity {
    public BaseEntity(String id) {
        this.id = id;
    }

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    private Long sort;
}

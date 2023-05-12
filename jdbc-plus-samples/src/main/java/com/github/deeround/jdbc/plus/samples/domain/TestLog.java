package com.github.deeround.jdbc.plus.samples.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @TableName test_log
 */
@TableName(value = "test_log")
@Data
public class TestLog implements Serializable {
    /**
     *
     */
    @TableId
    private String id;

    /**
     *
     */
    private String name;

    /**
     *
     */
    private String tenantId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
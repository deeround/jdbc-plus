package com.github.deeround.jdbc.plus.samples.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName test_user
 */
@TableName(value ="test_user")
@Data
public class TestUser implements Serializable {
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
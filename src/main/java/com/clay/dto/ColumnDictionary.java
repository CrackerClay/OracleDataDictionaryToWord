package com.clay.dto;

import lombok.Data;

/**
 * 表信息获取
 *
 * @author Clay
 * @date 2022-03-21 10:32:14
 */
@Data
public class ColumnDictionary {

    /**
     * 列名
     */
    private String columnName;
    /**
     * 注释
     */
    private String comments;
    /**
     * 数据类型
     */
    private String dataType;
    /**
     * 数据长度
     */
    private String dataLength;
    /**
     * 是否主键
     */
    private String isPrimaryKey;

}

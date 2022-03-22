package com.clay.dto;

import lombok.Data;

import java.util.List;

/**
 * 表信息获取
 *
 * @author Clay
 * @date 2022-03-21 10:32:14
 */
@Data
public class TableDictionary {

    /**
     * 表名
     */
    private String tableName;

    /**
     * 表说明
     */
    private String comments;

    /**
     * 列信息
     */
    private List<ColumnDictionary> columnDictionaries;

}

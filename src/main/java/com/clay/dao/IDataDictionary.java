package com.clay.dao;

import com.clay.dto.ColumnDictionary;
import com.clay.dto.TableDictionary;

import java.util.List;

/**
 * 数据字典获取
 *
 * @author Clay
 */
public interface IDataDictionary {

    /**
     * 获取表数据字典信息
     *
     * @return TableDictionary
     */
    List<TableDictionary> queryTableDictionaries();

    /**
     * 获取列信息
     *
     * @param tableName 表名
     * @return TableDictionary
     */
    List<ColumnDictionary> queryColumnDictionaries(String tableName);

}

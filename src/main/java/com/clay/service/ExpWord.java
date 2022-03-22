package com.clay.service;

import com.clay.dao.DataSource;
import com.clay.dao.Impl.OracleDataDictionaryImpl;
import com.clay.dto.ColumnDictionary;
import com.clay.dto.TableDictionary;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.plugin.table.LoopRowTableRenderPolicy;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.HashMap;
import java.util.List;

/**
 * @author Clay
 * @date
 */
public class ExpWord {

    public static void main(String[] args) throws Exception {
        DataSource.initDataSource();
        JdbcTemplate jdbcTemplate = DataSource.jdbcTemplate;
        OracleDataDictionaryImpl oracleDataDictionary = new OracleDataDictionaryImpl(jdbcTemplate);
        // 读取表
        List<TableDictionary> tableDictionaries = oracleDataDictionary.queryTableDictionaries();

        // 读取表列注释
        for (TableDictionary tableDictionary : tableDictionaries) {
            List<ColumnDictionary> columnDictionaries = oracleDataDictionary.queryColumnDictionaries(tableDictionary.getTableName());
            tableDictionary.setColumnDictionaries(columnDictionaries);
            System.err.println("读取表：" + tableDictionary.getTableName() + "列注释");
        }

        // 表格行循环插件
        LoopRowTableRenderPolicy hackLoopTableRenderPolicy = new LoopRowTableRenderPolicy();
        Configure config = Configure.builder().bind("columnDictionaries", hackLoopTableRenderPolicy).build();

        XWPFTemplate template = XWPFTemplate.compile("src/main/resources/template.docx", config).render(new HashMap<String, Object>(1) {
            {
                put("tableDictionaries", tableDictionaries);
            }
        });
        template.writeToFile("target/out_example.docx");

    }
}

package com.clay.dao.Impl;

import com.clay.dao.IDataDictionary;
import com.clay.dto.ColumnDictionary;
import com.clay.dto.TableDictionary;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * @author Clay
 * @date
 */
public class OracleDataDictionaryImpl implements IDataDictionary {

    private final JdbcTemplate jdbcTemplate;

    public OracleDataDictionaryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<TableDictionary> queryTableDictionaries() {
        String sql = "select table_name, comments from user_tab_comments where 1 = 1 order by table_name ";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(TableDictionary.class));
    }

    @Override
    public List<ColumnDictionary> queryColumnDictionaries(String tableName) {
        String sql = "select t.column_name, t.comments, t.data_type, t.data_length, nvl2(b.position, 'PK', null) isPrimarykey, column_id " +
                "  from (select t2.table_name, t2.column_name, t3.comments, t2.data_type, t2.data_length, column_id " +
                "           from user_tab_columns t2, user_col_comments t3 " +
                "          where t2.table_name = t3.table_name " +
                "            and t3.column_name = t2.column_name " +
                "            and t2.table_name = ? " +
                "          order by t2.table_name, t2.column_id) t " +
                "  left join (select * " +
                "               from user_cons_columns " +
                "              where constraint_name in " +
                "                    (select constraint_name " +
                "                       from user_constraints " +
                "                      where constraint_type = 'P' " +
                "                        and table_name = ?)) b " +
                "    on t.table_name = b.table_name " +
                "   and t.column_name = b.column_name " +
                " order by column_id ";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ColumnDictionary.class), tableName, tableName);
    }

}

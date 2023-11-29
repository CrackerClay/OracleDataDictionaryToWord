Oracle数据字典导出Word

**SQL纪要**

**Oarcle**

```sql
-- 表信息
select table_name, comments from user_tab_comments where 1 = 1 order by table_name


-- 根据表获取列信息
select t.column_name, t.comments, t.data_type, t.data_length, nvl2(b.position, 'PK', null) isPrimarykey, column_id 
  from (select t2.table_name, t2.column_name, t3.comments, t2.data_type, t2.data_length, column_id 
           from user_tab_columns t2, user_col_comments t3 
          where t2.table_name = t3.table_name 
            and t3.column_name = t2.column_name 
            and t2.table_name = ? 
          order by t2.table_name, t2.column_id) t 
  left join (select * 
               from user_cons_columns 
              where constraint_name in 
                    (select constraint_name 
                       from user_constraints 
                      where constraint_type = 'P' 
                        and table_name = ?)) b 
    on t.table_name = b.table_name 
   and t.column_name = b.column_name 
 order by column_id
```

**高斯**

```sql
-- 表信息
 SELECT b.table_name, b.comments
 FROM MY_TABLES a left join MY_TAB_COMMENTS b
     on a.table_name = b.table_name
 where b.table_name is not null
order by b.table_name;




-- 根据表获取列信息
select a.*, decode(b.constraintdef, null, null, 'PK') isPrimarykey from (
    select t.table_name, t.column_name, c.comments, t.data_type, t.data_length, t.column_id
     from MY_TAB_COLUMNS t
     left join  MY_COL_COMMENTS c on t.table_name = c.table_name and t.column_name = c.column_name

  ) a

  left join (
      SELECT
        n.nspname AS schemaname, --schema名称
        c1.relname AS tablename, -- 表名
        c2.relname AS indexname, -- 索引名称
        s.conname AS conname,    -- 约束名称
        s.oid,
        pg_get_constraintdef(s.oid) AS constraintdef, -- 如果是约束，输出约束定义
        CASE WHEN s.conname IS NULL THEN pg_get_indexdef(x.indexrelid) END AS indexdef -- 如果不是约束，输出索引定义
    FROM pg_index x
    INNER JOIN pg_class c1 ON c1.oid = x.indrelid
    INNER JOIN pg_class c2 ON c2.oid = x.indexrelid
    INNER JOIN pg_namespace n ON n.oid = c1.relnamespace
    LEFT JOIN pg_constraint s ON s.conrelid = x.indrelid AND s.conindid = x.indexrelid and s.contype = 'p'
    WHERE (x.indisprimary = true OR x.indisunique = true)
    AND c1.relkind = 'r'
    AND x.indrelid >= 16384 AND x.indexrelid > 16384
    AND (c1.reloptions IS NULL OR c1.reloptions::text not like '%internal_mask%')  -- 排除内置对象
    ORDER BY schemaname, tablename, indexname

  ) b on a.table_name = b.tablename and b.constraintdef like '%' || a.column_name || '%'
 --where table_name = ?
 order by table_name, column_id
```

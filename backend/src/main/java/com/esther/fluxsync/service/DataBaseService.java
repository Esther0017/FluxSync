package com.esther.fluxsync.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DataBaseService {

    private final JdbcTemplate jdbcTemplate;

    public DataBaseService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * 执行通用查询并返回结果集。
     *
     * @param sql    要执行的 SQL 语句
     * @param mapper {@link RowMapper} 用于将结果集中的每一行映射为对象
     * @param params SQL 语句中的可变参数
     * @param <T>    返回的对象类型
     * @return 查询结果组成的 {@link List}，若无结果则返回空列表
     */
    public <T> List<T> query(String sql, RowMapper<T> mapper, Object... params) {
        return jdbcTemplate.query(sql, (rs, rowNum) -> mapper.map(rs), params);
    }

    /**
     * 执行查询并返回单条结果。
     * <p>
     * 内部调用 {@link #query(String, RowMapper, Object...)}，
     * 若结果为空则返回 {@link Optional#empty()}，
     * 否则返回第一条记录。
     * </p>
     *
     * @param sql    要执行的 SQL 语句
     * @param mapper {@link RowMapper} 用于将结果集中的每一行映射为对象
     * @param params SQL 语句中的可变参数
     * @param <T>    返回的对象类型
     * @return 单条查询结果的 {@link Optional}，可能为空
     */
    public <T> Optional<T> queryOne(String sql, RowMapper<T> mapper, Object... params) {
        List<T> list = query(sql, mapper, params);
        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }

    /**
     * 执行数据更新操作（INSERT/UPDATE/DELETE）。
     *
     * @param sql    要执行的 SQL 语句
     * @param params SQL 语句中的可变参数
     * @return 受影响的行数
     */
    public int update(String sql, Object... params) {
        return jdbcTemplate.update(sql, params);
    }

}

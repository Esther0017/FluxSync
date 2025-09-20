package com.esther.fluxsync.service;

import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DataBaseService {

    private final DataSource dataSource;

    public DataBaseService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * 通用查询
     *
     * @param sql sql语句
     * @param params 参数列表
     *
     * @return 对象列表
     *
     */
    public <T> List<T> query(String sql, Object[] params, RowMapper<T> mapper) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            setParameters(ps, params);
            try (ResultSet rs = ps.executeQuery()) {
                List<T> list = new ArrayList<>();
                while (rs.next()) {
                    list.add(mapper.map(rs));
                }
                return list;
            }
        }
    }

    // 查询单条
    public <T> Optional<T> queryOne(String sql, Object[] params, RowMapper<T> mapper) throws SQLException {
        List<T> list = query(sql, params, mapper);
        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }

    // 更新（INSERT/UPDATE/DELETE）
    public int update(String sql, Object[] params) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            setParameters(ps, params);
            return ps.executeUpdate();
        }
    }

    private void setParameters(PreparedStatement ps, Object[] params) throws SQLException {
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
        }
    }


}

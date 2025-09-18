package com.esther.fluxsync.service;

import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;

@Service
public class DataBaseService {

    private final DataSource dataSource;

    public DataBaseService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public ResultSet executeQuery(String query, Object[] params) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            setParameters(statement, params);

            return statement.executeQuery();
        }
    }

    // 更新操作（INSERT/UPDATE/DELETE）
    public int executeUpdate(String query, Object[] params) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            setParameters(statement, params);

            return statement.executeUpdate();
        }
    }

    private void setParameters(PreparedStatement statement, Object[] params) throws SQLException {
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }
        }
    }

}

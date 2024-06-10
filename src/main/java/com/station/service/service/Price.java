package com.station.service.service;

import com.station.service.db.ConnectionDb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Price {
    public static double getPriceById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        double price = 0.0;
        try {
            String sql = "SELECT price FROM ProductTemplate INNER JOIN Product ON ProductTemplate.template_id = Product.template_id WHERE product_id = ?";
            connection = ConnectionDb.createConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                price = resultSet.getDouble("price");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return price;
    }
}

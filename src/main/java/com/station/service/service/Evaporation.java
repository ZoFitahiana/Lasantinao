package com.station.service.service;

import com.station.service.db.ConnectionDb;
import com.station.service.db.entity.MovementStock;
import com.station.service.db.entity.Product;
import com.station.service.operation.ProductCrudOperation;
import org.springframework.stereotype.Component;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
@Component

public class Evaporation {
    private final ProductCrudOperation productCrudOperation;
    public Evaporation(ProductCrudOperation productCrudOperation) {
        this.productCrudOperation = productCrudOperation;
    }

    public static LocalDateTime getLastMovement(){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        LocalDateTime lastMovementDate = null;

        try {
            String sql = "SELECT MAX(date_movement) AS last_movement_date FROM MovementStock";
            connection = ConnectionDb.createConnection();
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                lastMovementDate = resultSet.getTimestamp("last_movement_date").toLocalDateTime();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving last movement date", e);
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
                throw new RuntimeException("Error closing database connection", e);
            }
        }

        return lastMovementDate;
    }
    public static String getNameProduct(int productId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String productName = null;
        try {
            String sql = "SELECT ProductTemplate.name FROM ProductTemplate INNER JOIN Product ON ProductTemplate.template_id = Product.template_id WHERE product_id = ?";
            connection = ConnectionDb.createConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, productId);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                productName = resultSet.getString("name");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving product name", e);
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
                throw new RuntimeException("Error closing database connection", e);
            }
        }

        return productName;
    }
    public static int calculateDaysDifference(LocalDateTime startDate, LocalDateTime endDate) {
        Duration duration = Duration.between(startDate, endDate);
        return (int) duration.toDays();
    }


    public double operationOfEvaporation(LocalDateTime last, LocalDateTime inDate,String productName){
        int daysEvaporation = calculateDaysDifference(last,inDate);
        double valueEvaporation = 0.0;
        if (productName.equals("Oil")){
            valueEvaporation = (daysEvaporation * 10 );
        } else if (productName.equals("Diesel") || productName.equals("Gasoline")) {
            valueEvaporation = (daysEvaporation * 10);
        }
        return  valueEvaporation;
    }
    public double evaporationByDay(MovementStock movementStock) {
        Product product = productCrudOperation.findById(movementStock.getProductId());
        LocalDateTime lastDate = getLastMovement();
        LocalDateTime applyEvaporationDate = movementStock.getDateMovement();
        String nameProduct = getNameProduct(product.getProductId());
        double evaporation = operationOfEvaporation(lastDate,applyEvaporationDate,nameProduct);
        double value = 0.0;
        if (product.getStockProduct() < evaporation || product.getStockProduct() < 0  ){
            value = 0 ;
        }else {
            value = (product.getStockProduct() - evaporation);
        }
        return value;
    }
}

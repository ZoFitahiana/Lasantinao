package com.station.service.service;

import com.station.service.db.ConnectionDb;
import com.station.service.db.entity.Product;
import com.station.service.operation.ProductCrudOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

@Component
public class Movement {
    private final ProductCrudOperation productCrudOperation;

    @Autowired
    public Movement(ProductCrudOperation productCrudOperation) {
        this.productCrudOperation = productCrudOperation;
    }
    //F3 -a
    public String getStatusMovementOne(LocalDateTime startDate, LocalDateTime endDate) {
        StringBuilder resultBuilder = new StringBuilder();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            String sql = "SELECT MovementStock.date_movement AS date_and_time, ProductTemplate.name, MovementStock.type_movement, MovementStock.quantity,MovementStock.quantity_type,MovementStock.product_id FROM MovementStock INNER JOIN ProductTemplate ON MovementStock.product_id = ProductTemplate.template_id WHERE MovementStock.date_movement BETWEEN ? AND ?";
            connection = ConnectionDb.createConnection();
            statement = connection.prepareStatement(sql);
            statement.setObject(1, startDate);
            statement.setObject(2, endDate);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                LocalDateTime date = resultSet.getTimestamp("date_and_time").toLocalDateTime();
                String name = resultSet.getString("ProductTemplate.name");
                String type = resultSet.getString("MovementStock.type_movement");
                int quantity = resultSet.getInt("MovementStock.quantity");
                String quantityType = resultSet.getString("MovementStock.quantity_type");
                int productId = resultSet.getInt("MovementStock.product_id");
                Product product = productCrudOperation.findById(productId);
                if (quantityType.equals("money")) {
                    double quantityInLiters = Conversion.conversionQuantityToLiter((double) quantity, product);
                    resultBuilder.append("| date : ").append(date).append(" | name : ").append(name).append(" | type : ").append(type).append(" | quantity : ").append(quantityInLiters).append(" liters |\n");
                } else {
                    resultBuilder.append("| date : ").append(date).append(" | name : ").append(name).append(" | type : ").append(type).append(" | quantity : ").append(quantity).append(" |\n");
                }
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
        return resultBuilder.toString();
    }

    //F3 -b
    public String getStatusMovementTwo(LocalDateTime startDate, LocalDateTime endDate) {
        StringBuilder resultBuilder = new StringBuilder();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            String sql = "SELECT * FROM StockSummary WHERE Date BETWEEN ? AND ?";
            connection = ConnectionDb.createConnection();
            statement = connection.prepareStatement(sql);
            statement.setObject(1, startDate);
            statement.setObject(2, endDate);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                LocalDateTime date = resultSet.getTimestamp("Date").toLocalDateTime();
                double addedGasoline = resultSet.getDouble("Qté ajoutée Essence");
                double addedDiesel = resultSet.getDouble("Qté ajoutée Gasoil");
                double addedOil = resultSet.getDouble("Qté ajoutée Pétrole");
                double soldGasoline = resultSet.getDouble("Qté vendue Essence");
                double soldDiesel = resultSet.getDouble("Qté vendue Gasoil");
                double soldOil = resultSet.getDouble("Qté vendue Pétrole");
                double remainingGasoline = resultSet.getDouble("Qté restante Essence");
                double remainingDiesel = resultSet.getDouble("Qté restante Gasoil");
                double remainingOil = resultSet.getDouble("Qté restante Pétrole");

                resultBuilder.append("| Date : ").append(date).append(" |\n");
                resultBuilder.append("| Qté ajoutée Essence : ").append(addedGasoline).append(" |\n");
                resultBuilder.append("| Qté ajoutée Gasoil : ").append(addedDiesel).append(" |\n");
                resultBuilder.append("| Qté ajoutée Pétrole : ").append(addedOil).append(" |\n");
                resultBuilder.append("| Qté vendue Essence : ").append(soldGasoline).append(" |\n");
                resultBuilder.append("| Qté vendue Gasoil : ").append(soldDiesel).append(" |\n");
                resultBuilder.append("| Qté vendue Pétrole : ").append(soldOil).append(" |\n");
                resultBuilder.append("| Qté restante Essence : ").append(remainingGasoline).append(" |\n");
                resultBuilder.append("| Qté restante Gasoil : ").append(remainingDiesel).append(" |\n");
                resultBuilder.append("| Qté restante Pétrole : ").append(remainingOil).append(" |\n");
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
        return resultBuilder.toString();
    }

    //Epics 02 vue global

    public String getStatusGlobalMovement(LocalDateTime startDate, LocalDateTime endDate) {
        StringBuilder resultBuilder = new StringBuilder();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            String sql = "SELECT * FROM StockSummary WHERE Date BETWEEN ? AND ?";
            connection = ConnectionDb.createConnection();
            statement = connection.prepareStatement(sql);
            statement.setObject(1, startDate);
            statement.setObject(2, endDate);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                LocalDateTime date = resultSet.getTimestamp("Date").toLocalDateTime();
                double addedGasoline = resultSet.getDouble("Qté ajoutée Essence");
                double addedDiesel = resultSet.getDouble("Qté ajoutée Gasoil");
                double addedOil = resultSet.getDouble("Qté ajoutée Pétrole");
                double soldGasoline = resultSet.getDouble("Qté vendue Essence");
                double soldDiesel = resultSet.getDouble("Qté vendue Gasoil");
                double soldOil = resultSet.getDouble("Qté vendue Pétrole");
                double remainingGasoline = resultSet.getDouble("Qté restante Essence");
                double remainingDiesel = resultSet.getDouble("Qté restante Gasoil");
                double remainingOil = resultSet.getDouble("Qté restante Pétrole");

                resultBuilder.append("| Date : ").append(date).append(" |\n");
                resultBuilder.append("| Qté ajoutée Essence : ").append(addedGasoline).append(" |\n");
                resultBuilder.append("| Qté ajoutée Gasoil : ").append(addedDiesel).append(" |\n");
                resultBuilder.append("| Qté ajoutée Pétrole : ").append(addedOil).append(" |\n");
                resultBuilder.append("| Qté vendue Essence : ").append(soldGasoline).append(" |\n");
                resultBuilder.append("| Qté vendue Gasoil : ").append(soldDiesel).append(" |\n");
                resultBuilder.append("| Qté vendue Pétrole : ").append(soldOil).append(" |\n");
                resultBuilder.append("| Qté restante Essence : ").append(remainingGasoline).append(" |\n");
                resultBuilder.append("| Qté restante Gasoil : ").append(remainingDiesel).append(" |\n");
                resultBuilder.append("| Qté restante Pétrole : ").append(remainingOil).append(" |\n");
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
        return resultBuilder.toString();
    }

}

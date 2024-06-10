package com.station.service.operation;

import com.station.service.db.ConnectionDb;
import com.station.service.db.entity.MovementStock;
import com.station.service.service.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;
@Repository

public class MovementStockCrudOperation implements  CrudOperation<MovementStock>{
    private final Stock stock;
    @Autowired
    public MovementStockCrudOperation(Stock stock) {
        this.stock = stock;
    }

    @Override
    public MovementStock findById(int id) {
        Connection connection = null ;
        PreparedStatement statement = null;
        ResultSet resultSet = null ;
        MovementStock movementStock = null ;
        try{
            String sql = "select * from MovementStock where movement_id = ? ";
            connection = ConnectionDb.createConnection();
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                int movementId = resultSet.getInt("movement_id");
                String typeMovement = resultSet.getString("type_movement");
                int quantity = resultSet.getInt("quantity");
                String quantityType = resultSet.getString("quantity_type");
                LocalDateTime dateOfMovement = resultSet.getTimestamp("date_movement").toLocalDateTime();
                int stationId = resultSet.getInt("station_id");
                int productId = resultSet.getInt("product_id");
                movementStock = new MovementStock(movementId,typeMovement,quantity,quantityType,dateOfMovement,stationId,productId);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                if (resultSet != null){
                    resultSet.close();
                }
                if (statement != null){
                    statement.close();
                }
                if (connection != null ){
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return movementStock;
    }

    @Override
    public List<MovementStock> findAll() {
        return null;
    }

    // F1
    @Override
    public MovementStock save(MovementStock toSave) {
        Connection connection = null;
        PreparedStatement statement = null ;
        try{
            String sql = "INSERT INTO MovementStock(movement_id,type_movement,quantity,quantity_type, date_movement, station_id, product_id) VALUES (?,?,?,?,?,?,?)";
            connection = ConnectionDb.createConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1,toSave.getMovementId());
            statement.setString(2,toSave.getTypeMovement());
            statement.setDouble(3,toSave.getQuantity());
            statement.setString(4,toSave.getQuantityType());
            statement.setObject(5,toSave.getDateMovement());
            statement.setInt(6,toSave.getStationId());
            statement.setInt(7,toSave.getProductId());
            statement.executeUpdate();
            stock.updateStockOfProduct(toSave);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                if (statement != null){
                    statement.close();
                 }
                if (connection != null ){
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return findById(toSave.getMovementId());
    }

    @Override
    public MovementStock update(MovementStock toUpdate) {
        return null;
    }

}

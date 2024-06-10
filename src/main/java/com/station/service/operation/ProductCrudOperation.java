package com.station.service.operation;

import com.station.service.db.ConnectionDb;
import com.station.service.db.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
@Repository

public class ProductCrudOperation implements  CrudOperation<Product>{
    @Override
    public Product findById(int id) {
        Connection connection = null ;
        PreparedStatement statement = null ;
        ResultSet resultSet = null ;
        Product product = null ;
        try{
            String sql = "select * from Product where product_id = ? ";
            connection = ConnectionDb.createConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1,id);
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                int productId = resultSet.getInt("product_id") ;
                int stationId = resultSet.getInt("station_id");
                int templateId = resultSet.getInt("template_id");
                double stockProduct = resultSet.getDouble("stock_product");
                product = new Product(productId,stationId,templateId,stockProduct);
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
        return product;
    }

    @Override
    public List<Product> findAll() {
        return null;
    }

    @Override
    public Product save(Product toSave) {
        return null;
    }

    @Override
    public Product update(Product toUpdate) {
        Connection connection = null ;
        PreparedStatement statement = null ;
        try{
            String sql = "Update  Product set station_id = ?, template_id = ? , stock_product = ?   where product_id = ?";
            connection = ConnectionDb.createConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1,toUpdate.getStationId());
            statement.setInt(2,toUpdate.getTemplateId());
            statement.setDouble(3,toUpdate.getStockProduct());
            statement.setInt(4,toUpdate.getProductId());
            statement.executeUpdate();
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0){
                throw new RuntimeException("Product with id : " + toUpdate.getProductId() + "not found !");
            }
        }
        catch (SQLException e) {
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
        return findById(toUpdate.getProductId());
    }
   }

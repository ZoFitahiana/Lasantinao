package com.station.service.service;

import com.station.service.db.ConnectionDb;
import com.station.service.db.entity.MovementStock;
import com.station.service.db.entity.Product;
import com.station.service.operation.ProductCrudOperation;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component
public class Stock {
    private final ProductCrudOperation productCrudOperation;
    private  final  Evaporation evaporation;

    public Stock(ProductCrudOperation productCrudOperation, Evaporation evaporation) {
        this.productCrudOperation = productCrudOperation;
        this.evaporation = evaporation;
    }
    //F1
    public void updateStockOfProduct(MovementStock movementStock) {
        double quantityValue = 0.0;
        Product product = productCrudOperation.findById(movementStock.getProductId());

        if (movementStock.getQuantityType().equals("money")) {
            double money = movementStock.getQuantity();
            quantityValue = Conversion.conversionQuantityToLiter(money, product);
        } else if (movementStock.getQuantityType().equals("liter")) {
            quantityValue = movementStock.getQuantity();
        } else {
           throw  new RuntimeException("Verify your type quantity");
        }

        Product newProductUpdated = getProductAfterMovement(movementStock, product, quantityValue);
        productCrudOperation.update(newProductUpdated);
        Connection connection = null ;
        PreparedStatement statement = null ;
        try{
           String sql = "SELECT save_history_stock(?,?,?,?);";
           connection = ConnectionDb.createConnection();
           statement= connection.prepareStatement(sql);
           statement.setInt(1,movementStock.getProductId());
           statement.setInt(2,movementStock.getMovementId());
           statement.setDouble(3,quantityValue);
           statement.setObject(4,movementStock.getDateMovement());
           statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    //F2
    private  Product getProductAfterMovement(MovementStock movementStock, Product product, double quantityValue) {
        double newStock =  evaporation.evaporationByDay(movementStock);
        if (movementStock.getTypeMovement().equals("Input")) {
            newStock += quantityValue;
        } else if (movementStock.getTypeMovement().equals("Output")) {
            if (quantityValue <= product.getStockProduct() && quantityValue <= 200 ){
                newStock -= quantityValue;
            }else{
                throw  new RuntimeException("Verify your stock product");
            }
        }
        return new Product(product.getProductId(), product.getStationId(), product.getTemplateId(), newStock);
    }
}

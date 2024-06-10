package com.station.service.service;

import com.station.service.db.entity.Product;

public class Conversion {
    public static  double conversionQuantityToLiter(Double quantityTypeMoney, Product product){
        double priceOfProduct = Price.getPriceById(product.getProductId());
      return Math.floor(quantityTypeMoney/priceOfProduct);
    }
}

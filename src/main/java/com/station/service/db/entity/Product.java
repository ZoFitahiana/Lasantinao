package com.station.service.db.entity;

import lombok.*;

@Getter
@AllArgsConstructor
@Setter
@ToString
@EqualsAndHashCode

public class Product {
    private int productId;
    private int stationId;
    private int templateId;
    private double stockProduct;
}

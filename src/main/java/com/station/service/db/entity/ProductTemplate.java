package com.station.service.db.entity;
import lombok.*;

@Getter
@AllArgsConstructor
@Setter
@ToString
@EqualsAndHashCode

public class ProductTemplate {
    private int templateId;
    private double price ;
    private  String name ;
}

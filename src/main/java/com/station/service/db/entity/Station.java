package com.station.service.db.entity;

import lombok.*;

import java.util.List;
@Getter
@AllArgsConstructor
@Setter
@ToString
@EqualsAndHashCode
public class Station {
   private  int stationId;
   private  String location ;
   private  String coordinates ;
   private  List<Product> productList;
}

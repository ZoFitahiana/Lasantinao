package com.station.service.db.entity;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Setter
@ToString
@EqualsAndHashCode

public class MovementStock {
    private int movementId;
    private String typeMovement;
    private double quantity;
    private String quantityType; //F2
    private LocalDateTime dateMovement;
    private int stationId;
    private int productId;
}

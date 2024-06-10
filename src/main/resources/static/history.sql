
Create Table StockHistory(
 history_id SERIAL  primary key ,
 product_id INT REFERENCES ProductTemplate(template_id),
 movement_id INT REFERENCES MovementStock(movement_id),
 remaining_quantities DOUBLE PRECISION,
 date_movement TIMESTAMP
);

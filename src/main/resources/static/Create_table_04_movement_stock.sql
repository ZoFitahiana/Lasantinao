CREATE TABLE MovementStock(
   movement_id INT PRIMARY KEY,
   type_movement TEXT CHECK (type_movement IN ('Input', 'Output')),
   quantity DOUBLE PRECISION,
   quantity_type TEXT CHECK (quantity_type IN ('liter', 'money')),
   date_movement TIMESTAMP,
   station_id INT REFERENCES Station(station_id),
   product_id INT REFERENCES Product(product_id)
);
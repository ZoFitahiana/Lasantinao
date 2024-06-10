CREATE TABLE Product(
   product_id INT PRIMARY KEY,
   station_id INT REFERENCES Station(station_id),
   template_id INT REFERENCES ProductTemplate(template_id),
   stock_product DECIMAL
);

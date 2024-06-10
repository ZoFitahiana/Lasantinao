-- Station
INSERT INTO Station(station_id, location, coordinates) VALUES
(1, 'Talatamaty ', 'Talatamaty station A'),
(2, 'Ivandry ', 'Ivandry station E'),
(3, 'Anosy ', 'Anosy station E');

--  ProductTemplate
INSERT INTO ProductTemplate(template_id, price, name) VALUES
(1,2130, 'Oil'),
(2,4900, 'Diesel'),
(3,5.900, 'Gasoline');


-- Product
INSERT INTO Product(product_id, station_id, template_id,stock_product) VALUES
(1, 1, 1,300.12),
(2, 2, 2,200.13),
(3, 3, 3,400.14);


--  MovementStock
INSERT INTO MovementStock(movement_id, type_movement, quantity, quantity_type, date_movement, station_id, product_id) VALUES
(1, 'Input', 100.00, 'liter', '2024-05-01', 1, 1),
(2, 'Output', 50.00, 'liter', '2024-05-02', 2, 2),
(3, 'Input', 230.00, 'money', '2024-05-03', 3, 3);

-- Stock initial
INSERT INTO StockHistory (product_id, movement_id, remaining_quantities, date_movement)
VALUES
(1, 1, 300.12, '2024-05-01'),
(2, 2, 200.13, '2024-05-02'),
(3, 3, 400.14, '2024-05-03');

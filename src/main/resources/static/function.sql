CREATE OR REPLACE FUNCTION save_history_stock(
    in_product_id INT,
    in_movement_id INT,
    in_remaining_quantities INT,
    in_date_movement TIMESTAMP
)
RETURNS INT AS $$
BEGIN
    INSERT INTO StockHistory (product_id, movement_id, remaining_quantities, date_movement)
    VALUES (in_product_id, in_movement_id, in_remaining_quantities, in_date_movement);

    RETURN (SELECT currval(pg_get_serial_sequence('StockHistory', 'history_id')));
END;
$$ LANGUAGE plpgsql;

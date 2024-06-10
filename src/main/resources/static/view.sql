CREATE OR REPLACE VIEW StockSummary AS
SELECT
    SH.date_movement AS "Date",
    COALESCE(SUM(CASE WHEN PT.name = 'Gasoline' AND MS.type_movement = 'Input' THEN SH.remaining_quantities ELSE 0 END), 0) AS "Qté ajoutée Essence",
    COALESCE(SUM(CASE WHEN PT.name = 'Diesel' AND MS.type_movement = 'Input' THEN SH.remaining_quantities ELSE 0 END), 0) AS "Qté ajoutée Gasoil",
    COALESCE(SUM(CASE WHEN PT.name = 'Oil' AND MS.type_movement = 'Input' THEN SH.remaining_quantities ELSE 0 END), 0) AS "Qté ajoutée Pétrole",
    COALESCE(SUM(CASE WHEN PT.name = 'Gasoline' AND MS.type_movement = 'Output' THEN SH.remaining_quantities ELSE 0 END), 0) AS "Qté vendue Essence",
    COALESCE(SUM(CASE WHEN PT.name = 'Diesel' AND MS.type_movement = 'Output' THEN SH.remaining_quantities ELSE 0 END), 0) AS "Qté vendue Gasoil",
    COALESCE(SUM(CASE WHEN PT.name = 'Oil' AND MS.type_movement = 'Output' THEN SH.remaining_quantities ELSE 0 END), 0) AS "Qté vendue Pétrole",
    COALESCE(SUM(CASE WHEN PT.name = 'Gasoline' THEN SH.remaining_quantities ELSE 0 END), 0) AS "Qté restante Essence",
    COALESCE(SUM(CASE WHEN PT.name = 'Diesel' THEN SH.remaining_quantities ELSE 0 END), 0) AS "Qté restante Gasoil",
    COALESCE(SUM(CASE WHEN PT.name = 'Oil' THEN SH.remaining_quantities ELSE 0 END), 0) AS "Qté restante Pétrole"
FROM
    StockHistory SH
LEFT JOIN
    MovementStock MS ON SH.movement_id = MS.movement_id
LEFT JOIN
    ProductTemplate PT ON SH.product_id = PT.template_id
WHERE
    SH.date_movement BETWEEN ? AND ?
GROUP BY
    SH.date_movement
ORDER BY
    SH.date_movement;

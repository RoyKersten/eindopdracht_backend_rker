INSERT INTO customer (first_name, last_name, phone_number, email)
VALUES
('Tom', 'de Graaf', '+31612345678', 'tom.degraaf@gmail.com'),
('Elly', 'van Doorn', '+31687654321', 'e.vandoorn@hmail.com'),
('Debby', 'Huismans', '+31654678515', 'debby.huismans@home.nl'),
('Karel', 'Viends', '+31685746114', 'karel.vriends@ziggo.nl');


INSERT INTO car (brand, model,year_of_construction, license_plate_number, customer_id_customer)
VALUES
('Volkswagen', 'Polo', '2021', '55-AWA-23','1');

INSERT INTO item (item_type, item_name, qty, price, brand, item_category)
VALUES
('part','zomerband 205/55/R16', '1', 55.00, 'Goodyear', 'banden'),
('part','winterband 205/55/R16', '1', 60.00, 'Goodyear', 'banden'),
('part','remschijven voor', '1', 129.99, 'Bosch', 'remmen'),
('part','remschijven achter', '1', 149.99, 'Bosch', 'remmen'),
('activity','keuring auto', '1', 45.00, '','keuring');


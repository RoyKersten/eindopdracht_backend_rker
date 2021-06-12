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


INSERT INTO service (service_type, service_date, service_status, issues_found_inspection, issues_to_repair,customer_id_customer, car_id_car)
VALUES
('inspection', '2021-06-08' , 'uitvoeren' ,'remmen voor vervangen, 4 nieuwe banden, olie verversen',null,1,1),
('repair', '2021-06-08' , 'uitvoeren' ,null, 'uitlaat vervangen',2,1);

INSERT INTO invoice (invoice_type, invoice_status, line_total, invoice_subtotal, vat_rate, vat_amount,invoice_total, customer_id_customer, service_id_service)
VALUES
('inspection_invoice', 'betaald' , 275.00 ,275.00, 0.21, 55, 330.00,'1', '1'),
('inspection_invoice', 'betaald' , 275.00 ,275.00, 0.21, 55, 330.00,'1', '1');

INSERT INTO service_line (service_line_number, item_id_item, item_name,qty, price, line_total,service_id_service, invoice_id_invoice)
VALUES
('10','1', 'zomerband 205/55/R16' ,'5', 55.00, 275.00,'1','1');
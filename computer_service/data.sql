-- Вставка данных в таблицу infrastructure.computers
INSERT INTO infrastructure.computers (inventory_number, graphic_adapter, processor, rom, ram) VALUES
('128g', 'GTX950', 'i5-4460', 128, 8),
('Блабла', 'NVIDIA GeForce RTX 3080', 'Intel Core i9-10900K', 512, 32);

-- Вставка данных в таблицу infrastructure.laptops
INSERT INTO infrastructure.laptops (inventory_number, graphic_adapter, processor, rom, ram, display) VALUES
('LT123', 'Intel UHD Graphics', 'Intel Core i5-8250U', 256, 16, '15.6" FHD'),
('LT124', 'NVIDIA GeForce MX250', 'Intel Core i7-8565U', 512, 32, '15.6" 4K UHD');

-- Вставка данных в таблицу infrastructure.peripherals
INSERT INTO infrastructure.peripherals (peripheral_name, peripheral_type, computer_id, laptop_id) VALUES
('Logitech Mouse', 'Mouse', 1, NULL),
('Dell Keyboard', 'Keyboard', 1, NULL),
('HP Printer', 'Printer', NULL, 1);

-- Вставка данных в таблицу infrastructure.software
INSERT INTO infrastructure.software (software_name, software_version, is_licensed, computer_id, laptop_id) VALUES
('Windows 10 Pro', '10.0', true, 1, NULL),
('Microsoft Office', '2019', true, 1, NULL),
('Adobe Photoshop', '2021', true, NULL, 1);

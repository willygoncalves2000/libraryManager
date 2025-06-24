INSERT INTO tb_client (name, email, password, login, phone) VALUES ('Willy', 'willygoncalvescampos@gmail.com', '1234', 'willy.goncalves', '37998244737');
INSERT INTO tb_client (name, email, password, login, phone) VALUES ('Emily', 'emilycamposfaria@gmail.com', '1234', 'emily.campos', '37998244737');
INSERT INTO tb_client (name, email, password, login, phone) VALUES ('Marcus', 'marcuspaulo@gmail.com', '1234', 'marcus.oliveira', '37998244737');

INSERT INTO tb_room (description, price, image_url) VALUES ('Quarto Simples', 99.90, 'https://exemplo.com/imagens/quarto1.jpg');
INSERT INTO tb_room (description, price, image_url) VALUES ('Quarto Luxo', 169.90, 'https://exemplo.com/imagens/quarto2.jpg');
INSERT INTO tb_room (description, price, image_url) VALUES ('Quarto Super Luxo', 239.90, 'https://exemplo.com/imagens/quarto3.jpg');

INSERT INTO tb_stay (client_id, room_id, check_in_date, check_out_date) VALUES (1, 2, '2025-06-23', '2025-06-24');
INSERT INTO tb_stay (client_id, room_id, check_in_date, check_out_date) VALUES (1, 1, '2025-07-01', '2025-07-02');
INSERT INTO tb_stay (client_id, room_id, check_in_date, check_out_date) VALUES (2, 1, '2025-06-23', '2025-06-24');




INSERT INTO tb_user (name, email, password, login, phone) VALUES ('Willy', 'willygoncalvescampos@gmail.com', '$2a$10$12Ddo/65ir0uYWxH8mNF8eOb9wjtfG0/7J5kr8eSZL951ZgR/JY1m', 'willy.goncalves', '37998244737');
INSERT INTO tb_user (name, email, password, login, phone) VALUES ('Emily', 'emilycamposfaria@gmail.com', '$2a$10$vezTwFrpfZG.u09XuZbnv.DzsuFj35hP/pYaCVMELRCGVRp6EuBr', 'emily.campos', '37998244737');
INSERT INTO tb_user (name, email, password, login, phone) VALUES ('Marcus', 'marcuspaulo@gmail.com', '$2a$10$jeQCmh16cR5OPdY/KiCgBeixCztfu7EeY810PGp.Kw3e5.7LYJz5C', 'marcus.oliveira', '37998244737');

INSERT INTO tb_room (description, price, image_url) VALUES ('Quarto Simples', 99.90, 'https://exemplo.com/imagens/quarto1.jpg');
INSERT INTO tb_room (description, price, image_url) VALUES ('Quarto Luxo', 169.90, 'https://exemplo.com/imagens/quarto2.jpg');
INSERT INTO tb_room (description, price, image_url) VALUES ('Quarto Super Luxo', 239.90, 'https://exemplo.com/imagens/quarto3.jpg');

INSERT INTO tb_stay (user_id, room_id, check_in_date, check_out_date) VALUES (1, 2, '2025-06-23', '2025-06-24');
INSERT INTO tb_stay (user_id, room_id, check_in_date, check_out_date) VALUES (1, 1, '2025-07-01', '2025-07-02');
INSERT INTO tb_stay (user_id, room_id, check_in_date, check_out_date) VALUES (2, 1, '2025-06-23', '2025-06-24');

INSERT INTO tb_role (authority) VALUES ('ROLE_ADMIN');
INSERT INTO tb_role (authority) VALUES ('ROLE_OPERATOR');

INSERT INTO tb_user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 2);



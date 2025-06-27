INSERT INTO tb_user (name, email, password, login, phone) VALUES ('Willy', 'willygoncalvescampos@gmail.com', '$2a$10$12Ddo/65ir0uYWxH8mNF8eOb9wjtfG0/7J5kr8eSZL951ZgR/JY1m', 'willy.goncalves', '37998244737');
INSERT INTO tb_user (name, email, password, login, phone) VALUES ('Emily', 'emilycamposfaria@gmail.com', '$2a$10$vezTwFrpfZG.u09XuZbnv.DzsuFj35hP/pYaCVMELRCGVRp6EuBr', 'emily.campos', '37998244737');
INSERT INTO tb_user (name, email, password, login, phone) VALUES ('Marcus', 'marcuspaulo@gmail.com', '$2a$10$jeQCmh16cR5OPdY/KiCgBeixCztfu7EeY810PGp.Kw3e5.7LYJz5C', 'marcus.oliveira', '37998244737');

INSERT INTO tb_room (name, description, price, image_url) VALUES ('Quarto Simples', 'Um quarto básico e barato',99.90, 'https://mab-art.com.br/wp-content/uploads/2019/09/como-decorar-quartos-de-hotel.jpg');
INSERT INTO tb_room (name, description, price, image_url) VALUES ('Quarto Luxo', 'Um luxoso quarto com quase tudo que há de melhor',169.90, 'https://cf.bstatic.com/xdata/images/hotel/max1024x768/465374907.jpg?k=636989f0fb144c8d84643414adfb679891e6717f7fced83bd5eade65b09ff125&o=&hp=1');
INSERT INTO tb_room (name, description, price, image_url) VALUES ('Quarto Super Luxo', 'O nosso melhor quarto, com TUDO que há de melhor', 239.90, 'https://forbes.com.br/wp-content/uploads/2020/01/Lifetsyle-Seven-South-13jan2020-Divulga%C3%A7%C3%A3o-1-1024x678.jpg');

INSERT INTO tb_stay (user_id, room_id, check_in_date, check_out_date) VALUES (1, 2, '2025-06-23', '2025-06-24');
INSERT INTO tb_stay (user_id, room_id, check_in_date, check_out_date) VALUES (1, 1, '2025-07-01', '2025-07-02');
INSERT INTO tb_stay (user_id, room_id, check_in_date, check_out_date) VALUES (2, 1, '2025-06-23', '2025-06-24');

INSERT INTO tb_role (authority) VALUES ('ROLE_ADMIN');
INSERT INTO tb_role (authority) VALUES ('ROLE_OPERATOR');

INSERT INTO tb_user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 2);



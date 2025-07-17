INSERT INTO tb_user (name, email, password, login, phone) VALUES ('Willy', 'willygoncalvescampos@gmail.com', '$2a$10$12Ddo/65ir0uYWxH8mNF8eOb9wjtfG0/7J5kr8eSZL951ZgR/JY1m', 'willy.goncalves', '37998244737');
INSERT INTO tb_user (name, email, password, login, phone) VALUES ('Emily', 'emilycamposfaria@gmail.com', '$2a$10$12Ddo/65ir0uYWxH8mNF8eOb9wjtfG0/7J5kr8eSZL951ZgR/JY1m', 'emily.campos', '37998244737');
INSERT INTO tb_user (name, email, password, login, phone) VALUES ('Marcus', 'marcuspaulo@gmail.com', '$2a$10$jeQCmh16cR5OPdY/KiCgBeixCztfu7EeY810PGp.Kw3e5.7LYJz5C', 'marcus.oliveira', '37998244737');


INSERT INTO tb_role (authority) VALUES ('ROLE_ADMIN');
INSERT INTO tb_role (authority) VALUES ('ROLE_CLIENT');
INSERT INTO tb_role (authority) VALUES ('ROLE_USER');

INSERT INTO tb_user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (1, 2);
INSERT INTO tb_user_role (user_id, role_id) VALUES (1, 3);

INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 2);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 3);

INSERT INTO tb_user_role (user_id, role_id) VALUES (3, 3);





-- Usuário CLIENTE
INSERT INTO tb_user (name, email, password, login, phone) VALUES ('João', 'joao@email.com', '$2a$10$jeQCmh16cR5OPdY/KiCgBeixCztfu7EeY810PGp.Kw3e5.7LYJz5C', 'joao', '99999');
-- senha: 1234
INSERT INTO tb_user_role (user_id, role_id) VALUES (4, 2);

INSERT INTO tb_books (title, author, isbn, total_copies, available_copies) VALUES ('1984', 'George Orwell', '9780451524935', 5, 5);
INSERT INTO tb_books (title, author, isbn, total_copies, available_copies) VALUES ('O Senhor dos Anéis', 'J.R.R. Tolkien', '9788533615540', 3, 3);
INSERT INTO tb_books (title, author, isbn, total_copies, available_copies) VALUES ('Dom Casmurro', 'Machado de Assis', '9788533613379', 4, 4);

-- João (CLIENTE) pegou o livro 1 (1984)
INSERT INTO tb_loans (user_id, book_id, loan_date, due_date, return_date) VALUES ( 2, 1, '2025-07-17', '2025-07-24', NULL);

-- João pegou o livro 3 (Dom Casmurro) e já devolveu
INSERT INTO tb_loans (user_id, book_id, loan_date, due_date, return_date) VALUES (2, 3, '2025-07-10', '2025-07-17', '2025-07-16');


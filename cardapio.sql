-- CREATE DATABASE restaurant_menu;
-- USE restaurant_menu;

CREATE TABLE category (
    `id` INT NOT NULL AUTO_INCREMENT,  
    `name` VARCHAR(64) NOT NULL,
    PRIMARY KEY  (`id`)
) ENGINE = InnoDB;

CREATE TABLE product (
    `id` INT NOT NULL AUTO_INCREMENT,  
    `name` VARCHAR(64) NOT NULL,
    `amount` int NOT NULL, 
    `description` VARCHAR(256) NOT NULL,
    `thumb_url` VARCHAR(256) NOT NULL,
    `category_id` int NOT NULL,
    PRIMARY KEY  (`id`),
    FOREIGN KEY (category_id) REFERENCES category (id)
) ENGINE = InnoDB;

INSERT INTO category (name) VALUES ("Lanches");
INSERT INTO category (name) VALUES ("Porções");
INSERT INTO category (name) VALUES ("Combinados");
INSERT INTO category (name) VALUES ("Sobremesas");
INSERT INTO category (name) VALUES ("Bebidas");

INSERT INTO product (name, amount, category_id,description, thumb_url) 
VALUES ("X Salada", 1800, 1, "200g de carne, mussarela, alface, tomate, picles, cebola roxa e maionese caseira", "https://www.seekpng.com/png/detail/408-4083760_png-hamburguer-po-de-hamburguer-congelado.png");

INSERT INTO product (name, amount, category_id,description, thumb_url) 
VALUES ("X Bacon", 2200, 1, "300g de carne, bacon, cheddar, alface, tomate, cebola roxa e maionese caseira", "https://www.kindpng.com/picc/m/427-4271828_transparent-hamburguer-png-hamburger-png-png-download.png");

INSERT INTO product (name, amount, category_id,description, thumb_url) 
VALUES ("Batata frita", 900, 2, "500g de batata.", "https://static.clubedaanamariabraga.com.br/wp-content/uploads/2019/02/batata-frita-sequinha-crocante.jpeg");

INSERT INTO product (name, amount, category_id,description, thumb_url) 
VALUES ("Combinado", 3000, 3, "X salada + 250g de Batata + Refri", "https://www.imagensempng.com.br/wp-content/uploads/2021/02/Hamburguer-Coca-Cola-Batata-Frita-Png.png");

INSERT INTO product (name, amount, category_id,description, thumb_url) 
VALUES ("Mousse", 600, 4, "Mousse de chocolate", "https://mrbey.com.br/wp-content/uploads/2021/02/Mousse-Lacta-Blue-500x338-01.jpg");

INSERT INTO product (name, amount, category_id, description, thumb_url) 
VALUES ("Coca Cola (lata)", 350, 5, "300ml", "https://www.imigrantesbebidas.com.br/bebida/images/products/full/1984-refrigerante-coca-cola-lata-350ml.jpg");
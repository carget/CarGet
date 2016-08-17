CREATE TABLE brands
(
    brand_id INT(11) unsigned PRIMARY KEY NOT NULL AUTO_INCREMENT,
    brand_name VARCHAR(25) NOT NULL,
    brand_full_name VARCHAR(60) NOT NULL
);
CREATE UNIQUE INDEX brands_brand_id_uindex ON brands (brand_id);
CREATE TABLE cars
(
    car_id INT(11) unsigned PRIMARY KEY NOT NULL AUTO_INCREMENT,
    model_id INT(11) unsigned NOT NULL,
    price_day DECIMAL(10) DEFAULT '777' NOT NULL,
    fuel_type INT(1) DEFAULT '0' NOT NULL,
    year INT(11) DEFAULT '2008' NOT NULL,
    CONSTRAINT cars_models_fk FOREIGN KEY (model_id) REFERENCES models (model_id) ON UPDATE CASCADE
);
CREATE UNIQUE INDEX cars_car_id_uindex ON cars (car_id);
CREATE INDEX cars_models_fk_idx ON cars (model_id);
CREATE TABLE models
(
    model_id INT(10) unsigned PRIMARY KEY NOT NULL AUTO_INCREMENT,
    brand_id INT(10) unsigned NOT NULL,
    class_name CHAR(1) DEFAULT 'B' NOT NULL,
    model_name VARCHAR(45) NOT NULL,
    doors_qty INT(10) unsigned DEFAULT '4' NOT NULL,
    automat TINYINT(4) DEFAULT '0' NOT NULL,
    power DOUBLE UNSIGNED DEFAULT '100' NOT NULL,
    conditioning TINYINT(4) DEFAULT '0' NOT NULL,
    img VARCHAR(255),
    CONSTRAINT models_brands_fk FOREIGN KEY (brand_id) REFERENCES brands (brand_id) ON UPDATE CASCADE
);
CREATE INDEX models_brands_fk_idx ON models (brand_id);
CREATE UNIQUE INDEX model_id_UNIQUE ON models (model_id);
CREATE TABLE orders
(
    order_id INT(11) unsigned PRIMARY KEY NOT NULL AUTO_INCREMENT,
    user_id INT(11) unsigned NOT NULL,
    car_id INT(11) unsigned NOT NULL,
    date_start DATE NOT NULL,
    date_end DATE NOT NULL,
    comment VARCHAR(60),
    rent DECIMAL(18,2) DEFAULT '0.00',
    fine DECIMAL(18,2) DEFAULT '0.00',
    status INT(1) NOT NULL,
    CONSTRAINT orders_users_fk FOREIGN KEY (user_id) REFERENCES users (user_id) ON UPDATE CASCADE,
    CONSTRAINT orders_cars_fk FOREIGN KEY (car_id) REFERENCES cars (car_id) ON UPDATE CASCADE
);
CREATE INDEX orders_users_fk ON orders (user_id);
CREATE INDEX order_car_fk_idx ON orders (car_id);
CREATE UNIQUE INDEX order_id_UNIQUE ON orders (order_id);
CREATE TABLE users
(
    user_id INT(11) unsigned PRIMARY KEY NOT NULL AUTO_INCREMENT,
    first_name VARCHAR(45) NOT NULL,
    last_name VARCHAR(45) NOT NULL,
    password VARCHAR(30) NOT NULL,
    passport VARCHAR(45),
    email VARCHAR(60) NOT NULL,
    is_admin TINYINT(4) DEFAULT '0' NOT NULL
);
CREATE UNIQUE INDEX users_email_uindex ON users (email);
CREATE UNIQUE INDEX users_id_uindex ON users (user_id);


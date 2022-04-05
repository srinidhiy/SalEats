DROP DATABASE IF EXISTS ProgrammingAssignment2;
CREATE DATABASE ProgrammingAssignment2;
USE ProgrammingAssignment2;

DROP TABLE IF EXISTS User;
DROP TABLE IF EXISTS Restaurant;
DROP TABLE IF EXISTS Restaurant_details;
DROP TABLE IF EXISTS Rating_details;
DROP TABLE IF EXISTS Category;
DROP TABLE IF EXISTS Bridge_table;

CREATE TABLE User (
	email VARCHAR(500) UNIQUE NOT NULL,
    name VARCHAR (500) UNIQUE NOT NULL,
    password VARCHAR(500) UNIQUE NOT NULL
);

CREATE TABLE Restaurant_details (
	details_id INT PRIMARY KEY NOT NULL,
    image_url VARCHAR(4000) NOT NULL,
    address VARCHAR(100) NOT NULL,
    phone_no VARCHAR(15) NOT NULL,
    estimated_price VARCHAR(10),
    yelp_url VARCHAR(4000) NOT NULL
);

CREATE TABLE Rating_details (
	rating_id INT PRIMARY KEY NOT NULL,
    review_count INT NOT NULL,
    rating VARCHAR(50) NOT NULL
);

CREATE TABLE Restaurant (
	restaurant_id VARCHAR(100) PRIMARY KEY NOT NULL,
    restaurant_name VARCHAR(500),
    details_id INT(50) NOT NULL,
    rating_id INT(50) NOT NULL,
    FOREIGN KEY (details_id) REFERENCES Restaurant_details(details_id),
    FOREIGN KEY (rating_id) REFERENCES Rating_details(rating_id)
);

CREATE TABLE Category (
	category_id INT PRIMARY KEY NOT NULL,
    category_name VARCHAR(500) NOT NULL,
    restaurant_id VARCHAR(100) NOT NULL,
    FOREIGN KEY (restaurant_id) REFERENCES Restaurant(restaurant_id)
);

CREATE TABLE Bridge_table (
	category_id INT NOT NULL AUTO_INCREMENT,
    restaurant_id VARCHAR(100) NOT NULL,
	FOREIGN KEY (category_id) REFERENCES Category(category_id),
    FOREIGN KEY (restaurant_id) REFERENCES Restaurant(restaurant_id)
);



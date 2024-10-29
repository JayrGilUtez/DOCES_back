create database doces_database;

use doces_database;

create table Role(
    `id` int primary key auto_increment,
    `name` varchar(30) not null UNIQUE
);

CREATE TABLE user (
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR (50) NOT NULL,
    `lastname` VARCHAR (50) NOT NULL,
    -- Esta es la matrícula ej.20223TN066
    `student_id` VARCHAR (50) NOT NULL UNIQUE,
    `email` VARCHAR (100) NOT NULL UNIQUE,
    `role_id` INT,
    `password` VARCHAR(255) NOT NULL,
    FOREIGN KEY (role_id) REFERENCES role(id)
);
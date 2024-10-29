create database doces_database;

use doces_database;

create table Role(
    `id` int primary key auto_increment,
    `name` varchar(30) not null UNIQUE
);

create table Person(
    `id` int primary key auto_increment,
    `name` varchar(50) not null,
    `last_name` varchar(50) not null,
    -- Esta es la matrícula ej.20223TN066
    `student_id` int not null UNIQUE
);

create table User(
    `id` int primary key auto_increment,
    `username` varchar(50) not null UNIQUE,
    `email` varchar(50) not null UNIQUE,
    `password` varchar(256) not null,
    `id_role` int not null,
    `id_person` int not null,
    foreign key(id_role) references Role(id),
    foreign key(id_person) references Person(id)
);
create database internship_dataone;
use internship_dataone;
create table orders(
  -- primary key
  id bigint primary key not null auto_increment,
  customer_name varchar(255) not null,
  item_ordered varchar(255) not null
);

INSERT INTO orders (customer_name, item_ordered) values ('Bobby', 'Sandwich');

INSERT INTO orders (customer_name, item_ordered) values ('Rohit', 'Burger');

INSERT INTO orders (customer_name, item_ordered) values ('Ashish', 'Fries');

INSERT INTO orders (customer_name, item_ordered) values ('Nikunj', 'Cola');
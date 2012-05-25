create table spending (
	spending_id int8 primary key,
	category varchar,
	subcategory varchar,
	name varchar,
	spending_date date,
	quantity int,
	unit_price numeric(8,2),
	total_price numeric(8,2));

create sequence spending_sequence;
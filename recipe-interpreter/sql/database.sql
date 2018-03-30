create table Recipe (
	recipe_id integer primary key,
	title varchar(32) not null,
	yield integer not null,
	serving_size integer not null,
	serving_type varchar(12) not null,
	instructions varchar(1200) not null
);

create table Ingredient (
	recipe_id integer not null,
	name varchar(16) not null,
	unit_of_measure varchar(12) not null,
	quantity integer not null,
	constraint ic_ingredient foreign key (recipe_id) references Recipe(recipe_id)
);
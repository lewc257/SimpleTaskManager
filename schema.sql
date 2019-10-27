drop table if exists user_account;
drop table if exists customer;
drop table if exists task;

create table user_account(
	user_id int auto_increment,
	username varchar(255) not null unique,
	password varchar(255) not null,
	created timestamp not null default current_timestamp,
	primary key(user_id)
);

create table user_info(
	user_info_id int auto_increment,
	user_id int not null unique,
	first_name varchar(255) not null,
	last_name varchar(255) not null,
	personal_email varchar(255) not null unique,
	created timestamp not null default current_timestamp,
	primary key(user_info_id),
	foreign key (user_id) references user_account(user_id)
);


create table task(
	task_id int auto_increment,
	user_id int not null,
	name varchar(255) not null,
	status boolean default 0,
	created timestamp not null default current_timestamp,
	primary key (task_id),
	foreign key (user_id) references user_account(user_id)
);
create table users (
    id serial primary key,
    name varchar(255)
);

create table meeting (
    id serial primary key,
    name varchar(255)
);

create table validate (
    id serial primary key,
    user_id int references users(id),
    meeting_id int references meeting(id),
    status varchar(255) default('Unread')
);

insert into users(name) values('Roma');
insert into users(name) values('Petr');
insert into users(name) values('Sasha');


insert into meeting(name) values ('Planner');
insert into meeting(name) values ('Corporate');
insert into meeting(name) values ('Journey');

insert into validate(user_id, meeting_id) values (1, 1);
insert into validate(user_id, meeting_id) values (1, 2);

insert into validate(user_id, meeting_id) values (2, 1);
insert into validate(user_id, meeting_id) values (2, 1);

insert into validate(user_id, meeting_id) values (3, 3);
insert into validate(user_id, meeting_id) values (3, 1);

update validate set  status = 'Yes' where id=1;
update validate set  status = 'No' where id=2;
update validate set  status = 'Yes' where id=3;
update validate set  status = 'No' where id=4;
update validate set  status = 'Yes' where id=5;


select count(id) as Applications, count(distinct user_id) as Users from validate;

select name from validate right outer join meeting m on m.id = validate.meeting_id where user_id is null;
create table users (
    id int primary key,
    name varchar(255)
);

create table meeting
(
    id      int primary key,
    name    varchar(255)
);

create table validate (
    id  int primary key,
    user_id int references users(id),
    meeting_id int references meeting(id)
);

insert into users values (1, 'Roma');
insert into users values (2, 'Sasha');
insert into users values (3, 'Vasya');

insert into meeting values (1,'Planner');
insert into meeting values (2,'Corporate');
insert into meeting values (3,'Journey');

insert into validate values (1,  1, 1);
insert into validate values (2,  1, 2);
insert into validate values (3,  2, 1);
insert into validate values (4,  3, 2);

select count(id) as Applications, count(distinct user_id) as Users from validate;

select name from validate right outer join meeting m on m.id = validate.meeting_id where user_id is null;
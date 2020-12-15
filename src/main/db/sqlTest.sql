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


select meeting.name, COUNT(validate.status) AS countOfPpl
from validate join meeting on validate.meeting_id = meeting.id
where validate.status = 'Yes'
group by meeting.name;

select withoutrequest  from (select meeting.name as withoutrequest
from meeting left outer join validate on meeting.id = validate.meeting_id
group by meeting.name) as first
left outer join (select meeting.name as withrequest
from meeting left outer join validate on meeting.id = validate.meeting_id
where validate.status = 'Yes' group by meeting.name) as second
on withoutrequest=withrequest where withrequest is null;

select meeting.name from meeting join validate v on meeting.id = v.meeting_id where status = 'No'
    except
select meeting.name from meeting join validate v on meeting.id = v.meeting_id where status = 'Yes';
CREATE TABLE company
(
    id integer NOT NULL,
    name character varying,
    CONSTRAINT company_pkey PRIMARY KEY (id)
);

CREATE TABLE person
(
    id integer NOT NULL,
    name character varying,
    company_id integer,
    CONSTRAINT person_pkey PRIMARY KEY (id)
);

insert into company values (1,'BMW');
insert into company values (2,'Nike');
insert into company values (3,'Apple');
insert into company values (4,'Microsoft');
insert into company values (5,'IBM');
insert into company values (6,'US Polo');

insert into person values (1,'Roma',1);
insert into person values (2,'Petr',2);
insert into person values (3,'Masha',3);
insert into person values (4,'Vasya',4);
insert into person values (5,'Katya',5);
insert into person values (6,'Sasha',5);

select p.name as person, c.name as company from person p left join company c
on c.id = p.company_id where p.company_id = 5;

select c.name, count(p.company_id) from company c left join person p
on c.id = p.company_id group by c.name order by count(p.company_id) DESC LIMIT 1;
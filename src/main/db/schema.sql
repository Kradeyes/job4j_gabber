CREATE database job_grabber;

create table post (
    id          serial primary key,
    name        varchar(255) not null,
    author      varchar(255) not null,
    description text         not null,
    link        text         not null UNIQUE ,
    created     date         not null
)
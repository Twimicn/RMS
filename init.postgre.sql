create table rms_user
(
    id          serial
        constraint rms_user_pk
            primary key,
    username    varchar(50) not null,
    password    varchar(50) not null,
    name        varchar(20),
    email       varchar(50),
    phone       varchar(20),
    role_id     int,
    create_time timestamp,
    update_time timestamp,
    state       int,
    token       varchar(40),
    expire      timestamp
);

comment on table rms_user is '用户表';

insert into rms_user (id, username, password, email, phone, role_id, state)
values (1, 'admin', '21232f297a57a5a743894a0e4a801fc3', 'admin@ginah.show', '13312345678', 1, 1);

create table rms_project
(
    id          serial
        constraint rms_project_pk
            primary key,
    name        varchar(50) not null,
    create_time timestamp,
    state       integer
);

create table rms_user_project
(
    id         serial
        constraint rms_user_project_pk
            primary key,
    user_id    integer,
    project_id integer,
    role       integer default 0
);

create table rms_resource
(
    id           serial
        constraint rms_resource_pk
            primary key,
    name         text,
    memo         text,
    storage      text,
    user_id      integer,
    user_name    text,
    project_id   integer,
    project_name text,
    create_time  timestamp,
    state        integer
);
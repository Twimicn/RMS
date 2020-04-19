create table rms_user
(
    id          integer
        constraint tm_user_pk
            primary key autoincrement,
    username    varchar(50) not null,
    password    varchar(50) not null,
    name        varchar(20),
    email       varchar(50),
    phone       varchar(20),
    role_id     integer,
    create_time datetime,
    update_time datetime,
    state       integer,
    token       varchar(40),
    expire      datetime
);

insert into rms_user (id, username, password, email, phone, role_id, state)
values (1, 'admin', '21232f297a57a5a743894a0e4a801fc3', 'admin@ginah.show', '13312345678', 1, 1);

create table rms_project
(
    id          integer
        constraint rms_project_pk
            primary key autoincrement,
    name        varchar(50) not null,
    create_time datetime,
    state       integer
);

create table rms_user_project
(
    id         integer not null
        constraint rms_user_project_pk
            primary key autoincrement,
    user_id    integer not null,
    project_id integer not null,
    role       integer default 0
);

create table if not exists users
(
    id         varchar(100) PRIMARY KEY NOT NULL,
    name       varchar(30)             NOT NULL,
    surname    varchar(30)             NOT NULL,
    phone      varchar(30)             NOT NULL,
    created_at timestamp               NOT NULL
);

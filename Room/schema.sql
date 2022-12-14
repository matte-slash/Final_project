USE matteo;

create table room (
       id  bigserial not null,
        address varchar(255) not null,
        name varchar(255),
        total_desk int4 not null,
        primary key (id)
);

create table _user_ (
       id  bigserial not null,
        first_name varchar(255),
        last_name varchar(255),
        role varchar(255),
        primary key (id)
);

create table slot_desk (
       id  bigserial not null,
        available boolean,
        date timestamp not null,
        desk_name varchar(255) not null,
        type int4 not null,
        room_id int8 not null,
        primary key (id)
)

create table booking (
       id  bigserial not null,
        date_of_booking timestamp,
        user_id int8 not null,
        primary key (id)
);


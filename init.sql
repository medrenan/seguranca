CREATE USER segurancaUser;
GRANT ALL PRIVILEGES ON DATABASE seguranca TO segurancaUser;

create sequence usr_user_seq;

create table usr_user (
    usr_id bigint check (usr_id > 0) not null default nextval ('usr_user_seq'),
    usr_name varchar(20) not null,
    usr_password varchar(100) not null,
    primary key (usr_id),
    constraint uni_user_name unique (usr_name)
);

create sequence aut_authorization_seq;

create table aut_authorization (
    aut_id bigint check (aut_id > 0) not null default nextval ('aut_authorization_seq'),
    aut_name varchar(20) not null,
    primary key (aut_id),
    constraint uni_aut_name unique (aut_name)
);

create table uau_user_authorization (
    usr_id bigint check (usr_id > 0) not null,
    aut_id bigint check (aut_id > 0) not null,
    primary key (usr_id, aut_id),
    foreign key (usr_id) references usr_user (usr_id) on delete restrict on update cascade,
    foreign key (usr_id) references aut_authorization (aut_id) on delete restrict on update cascade
);

create sequence ter_terms_of_service_version_seq;

create table ter_terms_of_service (
    ter_version bigint check (ter_version > 0) not null default nextval ('ter_terms_of_service_version_seq'),
    ter_description varchar not null,
    primary key (ter_version)
);

alter table usr_user
add column usr_ter_version bigint references ter_terms_of_service (ter_version) unique;

insert into usr_user (usr_name, usr_password)
values ('admin', '$2a$10$i3.Z8Yv1Fwl0I5SNjdCGkOTRGQjGvHjh/gMZhdc3e7LIovAklqM6C');
insert into aut_authorization (aut_name)
values ('ROLE_ADMIN'), ('ROLE_USER');
insert into uau_user_authorization values (1, 1);
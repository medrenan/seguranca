CREATE USER segurancaUser;
GRANT ALL PRIVILEGES ON DATABASE seguranca TO segurancaUser;

create table usr_user (
    usr_id BIGSERIAL,
    usr_name varchar(20) not null,
    usr_email varchar(100) not null,
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
    usr_id bigint check (usr_id > 0) not null references usr_user (usr_id) on update cascade on delete restrict,
    aut_id bigint check (aut_id > 0) not null references aut_authorization (aut_id) on update cascade on delete restrict,
    primary key (usr_id, aut_id)
);

create sequence ter_terms_of_service_version_seq;

create table alt_alternatives (
    alt_id BIGSERIAL primary key,
    alt_description varchar not null
);

create table ter_terms_of_service (
    ter_version bigint check (ter_version > 0) not null default nextval ('ter_terms_of_service_version_seq'),
    ter_alt_id bigint references alt_alternatives (alt_id),
    primary key (ter_version)
);

create table log_terms_of_service (
    log_ter_id bigserial primary key,
    log_ter_userid bigint not null,
    log_ter_user_name varchar,
    log_ter_user_email varchar,
    log_ter_date timestamp without time zone not null,
    log_ter_accepted boolean not null,
    log_ter_term_version bigint not null references ter_terms_of_service (ter_version)
);

create table ter_alt_term_alternatives (
    ter_version bigint not null references ter_terms_of_service (ter_version) on update cascade on delete restrict,
    alt_id bigint not null references alt_alternatives (alt_id) on update cascade on delete restrict,
    primary key (ter_version, alt_id)
);

create table ual_user_alternatives (
    usr_id bigint references usr_user (usr_id) on update cascade on delete restrict,
    alt_id bigint references alt_alternatives (alt_id) on update cascade on delete restrict,
    primary key (usr_id, alt_id)
);

alter table usr_user
    add column usr_ter_version bigint references ter_terms_of_service (ter_version);

alter table ter_terms_of_service
    add column ter_log_term_id bigint references log_terms_of_service (log_ter_id);

alter table log_terms_of_service
    add column log_ter_alt_id bigint references alt_alternatives (alt_id);

insert into usr_user (usr_name, usr_email, usr_password)
values ('admin', 'admin@admin.com', '$2a$10$i3.Z8Yv1Fwl0I5SNjdCGkOTRGQjGvHjh/gMZhdc3e7LIovAklqM6C'),
       ('Antonio', 'antonio12@gmail.com', '$2a$10$i3.Z8Yv1Fwl0I5SNjdCGkOTRGQjGvHjh/gMZhdc3e7LIovAklqM6C'),
       ('Josué', 'josueazevedo@hotmail.com', '$2a$10$i3.Z8Yv1Fwl0I5SNjdCGkOTRGQjGvHjh/gMZhdc3e7LIovAklqM6C');

insert into aut_authorization (aut_name)
values ('ROLE_ADMIN'), ('ROLE_USER');

insert into uau_user_authorization values (1, 1),
                                          (2, 2),
                                          (3, 2);

insert into alt_alternatives
values (1, 'Uso de informações privadas fornecidas pelo usuário'), (2, 'Permissão de contato');

insert into ter_terms_of_service
values (1);

insert into ter_alt_term_alternatives
values (1, 1), (1, 2);

insert into ual_user_alternatives
values (2, 1), (2, 2),
       (3, 1);

insert into log_terms_of_service
values (1, 2, 'Antonio', 'antonio12@gmail.com', '2022-12-14 02:35:00.328799', true, 1, 1),
       (2, 2, 'Antonio', 'antonio12@gmail.com', '2022-12-14 02:35:00.328799', true, 1, 2),
       (3, 3, 'Josué', null, '2022-12-14 02:37:32.31235', true, 1, 1),
       (4, 3, 'Josué', null, '2022-12-14 02:37:32.31235', true, 1, 2),
       (5, 3, 'Josué', null, '2022-12-14 02:38:02.41234', false, 1, 2);
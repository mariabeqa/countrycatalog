create extension if not exists "uuid-ossp";

create table if not exists "countries"
(
    id          UUID          unique not null default uuid_generate_v1() primary key,
    name        varchar(50)   unique not null,
    code        varchar(2)    unique not null,
    total_area  NUMERIC
);

alter table "countries" owner to postgres;

delete
from "countries";
insert into "countries"(name, code, total_area)
values ('Russia', 'RU', '17125191');
insert into "countries"(name, code, total_area)
values ('Canada', 'CA', '9984670');
insert into "countries"(name, code, total_area)
values ('China', 'CN', '9596960');
insert into "countries"(name, code, total_area)
values ('United States', 'US', '9525067');
insert into "countries"(name, code, total_area)
values ('Brazil', 'BR', '8510346');
insert into "countries"(name, code, total_area)
values ('Australia', 'AU', '7741220');
insert into "countries"(name, code, total_area)
values ('India', 'IN', '3287263');
insert into "countries"(name, code, total_area)
values ('Argentina', 'AR', '2780400');
insert into "countries"(name, code, total_area)
values ('Kazakhstan', 'KZ', '2724910');
insert into "countries"(name, code, total_area)
values ('Algeria', 'DZ', '2381741');

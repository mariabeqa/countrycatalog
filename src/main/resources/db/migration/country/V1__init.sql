create extension if not exists "uuid-ossp";

create table if not exists "countries"
(
    id            UUID unique        not null default uuid_generate_v1() primary key,
    name      varchar(50) unique not null,
    code varchar(50)              unique not null,
    value
    );

alter table "countries" owner to postgres;

delete
from "countries";
insert into "countries"(name, code, )
values ('RUB', 0.015);
insert into "countries"(currency, currency_rate)
values ('KZT', 0.0021);
insert into "countries"(currency, currency_rate)
values ('EUR', 1.08);
insert into "countries"(currency, currency_rate)
values ('USD', 1.0);
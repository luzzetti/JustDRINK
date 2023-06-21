-- liquibase formatted sql

-- changeset chris:audit-1
alter table if exists restaurants add column created_at timestamp(6) with time zone not null default now();
alter table if exists restaurants add column updated_at timestamp(6) with time zone default now();
alter table if exists restaurants_aud add column created_at timestamp(6) with time zone default now();
alter table if exists restaurants_aud add column updated_at timestamp(6) with time zone default now();

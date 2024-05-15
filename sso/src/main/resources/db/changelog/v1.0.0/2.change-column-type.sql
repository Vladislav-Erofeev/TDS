-- liquibase formatted sql

-- changeSet Vladislav:1
alter table person alter column phone type bigint;
-- rollback alter table person alter column phone type int;

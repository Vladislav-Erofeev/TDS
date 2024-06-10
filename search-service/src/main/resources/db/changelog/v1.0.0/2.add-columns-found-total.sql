-- liquibase formatted sql

-- changeSet Vladislav:2

alter table geocoded_file add column total int;
alter table geocoded_file add column found int;

-- rollback alter table geocoded_file drop column found;
-- rollback alter table geocoded_file drop column total;
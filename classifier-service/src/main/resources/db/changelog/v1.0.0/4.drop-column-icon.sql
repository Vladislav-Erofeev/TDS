-- liquibase formatted sql

-- changeSet Vladislav:4

alter table layer drop column icon_url;

-- rollback alter table layer add column icon_url varchar not nul;
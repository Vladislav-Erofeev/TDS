-- liquibase formatted sql

-- changeSet Vladislav:4

alter table public.attribute add column required bool default false;

-- rollback alter table public.attribute drop column required;
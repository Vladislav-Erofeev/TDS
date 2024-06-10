-- liquibase formatted sql

-- changeSet Vladislav:2

alter table geocoded_file add column csv_report varchar;

-- rollback alter table geocoded_file drop column csv_report;

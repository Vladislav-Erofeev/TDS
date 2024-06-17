-- liquibase formatted sql

-- changeSet Vladislav:3
alter table message add column message_type varchar not null  default 'USER_MESSAGE';
-- rollback alter table message drop column message_type;
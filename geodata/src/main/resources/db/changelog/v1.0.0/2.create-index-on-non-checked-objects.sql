-- liquibase formatted sql

-- changeSet Vladislav:2
create index item_not_checked_idx on item(checked) where checked != true;

-- rollback drop index item_not_checked_idx;
-- liquibase formatted sql

-- changeSet Vladislav:2
create index item_person_id_idx on item(person_id);
create index item_code_id_idx on item(code_id);

-- rollback drop index item_person_id_idx;
-- rollback drop index item_code_id_idx;
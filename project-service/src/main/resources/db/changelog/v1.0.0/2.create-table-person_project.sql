-- liquibase formatted sql

-- changeSet Vladislav:2
create table person_project(
    person_id int references person(id) not null,
    project_id int references project(project_id) on delete cascade not null,
    role varchar not null,
    PRIMARY KEY (person_id, project_id)
);

create index person_id_idx on person_project(person_id);
create index project_id_idx on person_project(project_id);

-- rollback drop index person_id_idx;
-- rollback drop index project_id_idx;
-- rollback drop table person_project;
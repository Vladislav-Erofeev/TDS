package com.example.projectservice.mappers;

import com.example.projectservice.domain.dtos.ProjectDto;
import com.example.projectservice.domain.entities.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {PropsMapper.class})
public interface ProjectMapper {
    ProjectMapper INSTANCE = Mappers.getMapper(ProjectMapper.class);

    @Mapping(source = "id", target = "id", qualifiedByName = "decodeId")
    Project toEntity(ProjectDto projectDto);

    @Mapping(source = "id", target = "id", qualifiedByName = "encodeId")
    ProjectDto toDto(Project project);
}

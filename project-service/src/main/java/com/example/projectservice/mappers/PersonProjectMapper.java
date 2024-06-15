package com.example.projectservice.mappers;

import com.example.projectservice.domain.dtos.PersonProjectDto;
import com.example.projectservice.domain.entities.PersonProject;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {PropsMapper.class})
public interface PersonProjectMapper {
    PersonProjectMapper INSTANCE = Mappers.getMapper(PersonProjectMapper.class);

    @Mapping(source = "personId", target = "personId", qualifiedByName = "encodeId")
    PersonProjectDto toDto(PersonProject project);
}

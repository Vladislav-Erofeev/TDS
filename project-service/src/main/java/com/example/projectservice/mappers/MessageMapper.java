package com.example.projectservice.mappers;

import com.example.projectservice.domain.dtos.MessageDto;
import com.example.projectservice.domain.entities.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {PropsMapper.class})
public interface MessageMapper {
    MessageMapper INSTANCE = Mappers.getMapper(MessageMapper.class);

    @Mapping(source = "personId", target = "personId", qualifiedByName = "decodeId")
    @Mapping(source = "projectId", target = "projectId", qualifiedByName = "decodeId")
    @Mapping(source = "id", target = "id", qualifiedByName = "decodeId")
    Message toEntity(MessageDto messageDto);

    @Mapping(source = "personId", target = "personId", qualifiedByName = "encodeId")
    @Mapping(source = "projectId", target = "projectId", qualifiedByName = "encodeId")
    @Mapping(source = "id", target = "id", qualifiedByName = "encodeId")
    MessageDto toDto(Message message);
}

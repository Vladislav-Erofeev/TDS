package com.example.projectservice.domain.dtos;

import com.example.projectservice.domain.entities.PersonProjectRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonProjectDto {
    private String personId;
    private PersonProjectRole role;
}

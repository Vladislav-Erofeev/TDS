package com.example.projectservice.domain.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ProjectDto {
    private String id;
    private String name;
    private String comment;
    private Date createdAt;
    private Date modifiedAt;
    private Integer personsCount;
}

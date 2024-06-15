package com.example.projectservice.domain.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class MessageDto {
    private String id;
    private String personId;
    private String projectId;
    private String content;
    private Date sendTime;
}

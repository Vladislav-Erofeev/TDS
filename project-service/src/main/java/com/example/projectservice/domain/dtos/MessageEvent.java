package com.example.projectservice.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MessageEvent {
    private MessageActionType type;
    private MessageDto message;
}

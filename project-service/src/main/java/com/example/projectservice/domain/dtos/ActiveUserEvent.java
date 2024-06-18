package com.example.projectservice.domain.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActiveUserEvent {
    private ActiveUserEventType type;
    private String personId;
    private Long x;
    private Long y;

    enum ActiveUserEventType {
        CONNECT,
        DISCONNECT,
        MOUSE_MOVE
    }
}

package com.example.searchservice.messages;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ItemMessage {
    private Item before;
    private Item after;
    private DbOperationsEnum op;
}

package com.example.searchservice.messages;

import com.example.searchservice.domain.entities.Item;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ItemMessage {
    private Item before;
    private Item after;
    private DbOperationsEnum op;
}

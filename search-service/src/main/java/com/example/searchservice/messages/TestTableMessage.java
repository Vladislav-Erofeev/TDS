package com.example.searchservice.messages;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class TestTableMessage {
    private TestTable before;
    private TestTable after;
    private String op;
}

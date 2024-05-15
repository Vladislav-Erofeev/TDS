package com.example.geodata.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
public class LineChartDto {
    private List<String> keys = new LinkedList<>();
    private List<Integer> values = new LinkedList<>();
}

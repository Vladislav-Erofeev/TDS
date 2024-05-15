package com.example.geodata.repositories;

import com.example.geodata.domain.dto.LineChartDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;

@Repository
@RequiredArgsConstructor
public class MetricRepository {
    private final JdbcTemplate jdbcTemplate;
    private final ThreadLocal<SimpleDateFormat> simpleDateFormat = ThreadLocal.withInitial(() ->
            new SimpleDateFormat("yyyy-MM-dd"));

    public LineChartDto getItemsCountByPersonIdGroupBuDay(Long personId) {
        LineChartDto lineChartDto = new LineChartDto();
        jdbcTemplate.query("""
                select count(*) as count, date_trunc('day', creation_date) as day from item 
                 where person_id = ? group by day order by day desc limit 100
                """, rs -> {
            lineChartDto.getKeys().add(simpleDateFormat.get().format(rs.getDate("day")));
            lineChartDto.getValues().add(rs.getInt("count"));
        }, personId);
        return lineChartDto;
    }
}

package com.example.pluang.pojos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class ProfitDayWise {
    private String Date;
    private com.example.pluang.enums.Strategy Strategy;
    private Double ProfitPerDay;
}

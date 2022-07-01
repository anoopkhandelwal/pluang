package com.example.pluang.pojos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@AllArgsConstructor
@Data
public class IntradayResponse {
    private List<ProfitDayWise> Breakdown;
    private Double TotalProfit;
}

package com.example.pluang.responses;
import com.example.pluang.enums.*;
import lombok.*;
@Data
@AllArgsConstructor
@Builder
public class IntraDayProfit {
    private Double ProfitPerUnit;
    private TargetType target;
    private Strategy Strategy;
}

package com.example.pluang.pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = false)
public class Price {

    private String Date;
    private Double Open;
    private Double High;
    private Double Low;
    private Double Close;
    private Integer Volume;
}

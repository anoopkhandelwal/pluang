package com.example.pluang.controllers;

import com.example.pluang.StrategyService;
import com.example.pluang.responses.IntraDayProfit;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v2/")
public class RangeBasedStrategy {

    @Autowired
    private StrategyService strategyService;

    @RequestMapping(value = "{from}/{to}", method = RequestMethod.GET)
    public IntraDayProfit getRangeBasedStrategy(@PathVariable(value = "from") String from,
                                              @PathVariable(value = "to") String to) throws JSONException, IOException {

        IntraDayProfit profit = strategyService.getProfitByRangeDate(from,to);
        return profit;
    }
}

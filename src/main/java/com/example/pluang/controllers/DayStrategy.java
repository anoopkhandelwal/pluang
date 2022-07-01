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
@RequestMapping("/api/v1/")
public class DayStrategy {

    @Autowired
    private StrategyService strategyService;

    @RequestMapping(value = "{date}", method = RequestMethod.GET)
    public IntraDayProfit getDayStrategy(@PathVariable(value = "date") String date) throws IOException, JSONException {

        IntraDayProfit profit = strategyService.getProfitByDate(date);
        return profit;
    }
}

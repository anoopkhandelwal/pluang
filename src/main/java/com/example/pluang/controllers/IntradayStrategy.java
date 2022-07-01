package com.example.pluang.controllers;

import com.example.pluang.StrategyService;
import com.example.pluang.pojos.IntradayResponse;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v3/")
public class IntradayStrategy {

    @Autowired
    private StrategyService strategyService;

    @RequestMapping(value = "{from}/{to}", method = RequestMethod.GET)
    public IntradayResponse getIntraDayStrategy(@PathVariable(value = "from") String from,
                                              @PathVariable(value = "to") String to) throws IOException, JSONException {

        IntradayResponse profitList = strategyService.getProfitListByIntraDay(from, to);
        return profitList;

    }
}

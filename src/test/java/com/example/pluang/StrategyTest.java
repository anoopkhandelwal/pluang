package com.example.pluang;

import com.example.pluang.enums.Strategy;
import com.example.pluang.enums.TargetType;
import com.example.pluang.pojos.IntradayResponse;
import com.example.pluang.pojos.Price;
import com.example.pluang.responses.IntraDayProfit;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class StrategyTest {

    @InjectMocks
    private StrategyService strategyService;

    Price price = Price.builder()
            .Date("12/30/21")
            .Open(179.47)
            .High(180.57)
            .Close(178.09)
            .Low(178.2)
            .Volume(59773008)
            .build();

    IntraDayProfit intraDayProfit = IntraDayProfit.builder()
            .ProfitPerUnit(1.1)
            .Strategy(Strategy.Long)
            .target(TargetType.High)
            .build();

    @Test
    public void getProfitTest() throws Exception {

        IntraDayProfit intraDayProfitResponse = strategyService.getProfit(price);
        Assert.assertNotNull(intraDayProfitResponse);
        Assert.assertEquals(intraDayProfitResponse.getProfitPerUnit(),intraDayProfit.getProfitPerUnit());
        Assert.assertEquals(intraDayProfitResponse.getStrategy(),intraDayProfit.getStrategy());
        Assert.assertEquals(intraDayProfitResponse.getTarget(),intraDayProfit.getTarget());
    }

    @Test
    public void getProfitByDateTest() throws Exception {
        String date="31-12-2021";
        Double profit = 1.145;
        IntraDayProfit dayProfitResponse = strategyService.getProfitByDate(date);
        Assert.assertNotNull(dayProfitResponse);
        Assert.assertEquals(dayProfitResponse.getTarget(), TargetType.High);
        Assert.assertEquals(dayProfitResponse.getStrategy(), Strategy.Long);
        Assert.assertEquals(dayProfitResponse.getProfitPerUnit(), profit);
    }

    @Test
    public void getProfitListByIntraDayTest() throws Exception {
        String dateFrom="23-12-2021";
        String dateTo="31-12-2021";
        Double profit = 5.2;
        IntradayResponse dayProfitResponse = strategyService.getProfitListByIntraDay(dateFrom, dateTo);
        Assert.assertNotNull(dayProfitResponse);
        Assert.assertEquals(dayProfitResponse.getTotalProfit(), profit);
        Assert.assertEquals(dayProfitResponse.getBreakdown().size(), 6);
    }

    @Test
    public void getProfitByRangeDateTest() throws Exception {
        String dateFrom="23-12-2021";
        String dateTo="31-12-2021";
        Double profit = 0.58;
        IntraDayProfit dayProfitResponse = strategyService.getProfitByRangeDate(dateFrom, dateTo);
        Assert.assertNotNull(dayProfitResponse);
        Assert.assertEquals(dayProfitResponse.getTarget(), TargetType.Low);
        Assert.assertEquals(dayProfitResponse.getStrategy(), Strategy.Short);
        Assert.assertEquals(dayProfitResponse.getProfitPerUnit(), profit);
    }
}

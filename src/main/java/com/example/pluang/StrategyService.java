package com.example.pluang;

import com.example.pluang.constants.ReadFile;
import com.example.pluang.enums.Candle;
import com.example.pluang.enums.Strategy;
import com.example.pluang.enums.TargetType;
import com.example.pluang.pojos.IntradayResponse;
import com.example.pluang.pojos.Price;
import com.example.pluang.pojos.ProfitDayWise;
import com.example.pluang.responses.IntraDayProfit;
import com.example.pluang.utils.Utils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Component
public class StrategyService {

    public IntraDayProfit getProfit(Price price){

        Candle candle = null;
        IntraDayProfit profit = null;
        DecimalFormat df = new DecimalFormat("#.###");
        if (price.getOpen() > price.getClose()) {
            candle = Candle.RED;
            return IntraDayProfit.builder()
                    .ProfitPerUnit(Double.valueOf(df.format(price.getHigh() - price.getOpen())))
                    .Strategy(Strategy.Long)
                    .target(TargetType.High)
                    .build();

        } else {
            candle = Candle.GREEN;
            return IntraDayProfit.builder()
                    .ProfitPerUnit(Double.valueOf(df.format(price.getOpen() - price.getLow())))
                    .Strategy(Strategy.Short)
                    .target(TargetType.Low)
                    .build();
        }
    }

    public IntraDayProfit getProfitByDate(String date) throws JSONException, IOException {
        Candle candle = null;
        IntraDayProfit profit = null;
        DecimalFormat df = new DecimalFormat("#.###");
        JSONArray array = ReadFile.getFileData();
        date = Utils.getDateConverter(date);
        for (int i = 0; i < array.length(); i++) {

            JSONObject obj = array.getJSONObject(i);
            if (obj.getString("Date").equals(date)) {

                Price price = Price.builder()
                        .Date(obj.getString("Date")).
                                Open(obj.getDouble("Open")).High(obj.getDouble("High"))
                        .Close(obj.getDouble("Close"))
                        .Low(obj.getDouble("Low"))
                        .Volume(obj.getInt("Volume"))
                        .build();

                return getProfit(price);
            }
        }
        return profit;
    }

    public IntraDayProfit getProfitByRangeDate(String from, String to) throws JSONException, IOException {
        JSONArray array = ReadFile.getFileData();
        from = Utils.getDateConverter(from);
        to = Utils.getDateConverter(to);
        int i;
        Price price = null;
        Integer fromIndex = null;
        Integer toIndex = null;
        for (i = 0; i < array.length(); i++) {
            JSONObject obj = array.getJSONObject(i);
            if (obj.getString("Date").equals(from)) {
                price = Price.builder()
                        .Date(obj.getString("Date")).
                                Open(obj.getDouble("Open")).High(obj.getDouble("High"))
                        .Close(obj.getDouble("Close"))
                        .Low(obj.getDouble("Low"))
                        .Volume(obj.getInt("Volume"))
                        .build();
                fromIndex =i;
                break;
            }
        }
        for (; i < array.length(); i++) {
            JSONObject obj = array.getJSONObject(i);
            if(obj.getDouble("High") > price.getHigh()){
                price.setHigh(obj.getDouble("High"));
            }
            if(obj.getDouble("Low") < price.getLow()){
                price.setLow(obj.getDouble("Low"));
            }
            if (obj.getString("Date").equals(to)) {
                price.setClose(obj.getDouble("Close"));
                toIndex=i;
                break;
            }
        }
        if(fromIndex!=null&&toIndex!=null){
        return getProfit(price);
        }else{
            // We can raise a custom exception of invalid date range provided
            System.out.println("Invalid date range provided");
            return null;
        }
    }

    public IntradayResponse getProfitListByIntraDay(String from, String to) throws JSONException, IOException {
        Candle candle = null;
        IntraDayProfit profit = null;
        JSONArray array = ReadFile.getFileData();
        from = Utils.getDateConverter(from);
        to = Utils.getDateConverter(to);
        int i;
        Price price = null;
        Double totalProfit= 0.0;
        List<ProfitDayWise> response = new ArrayList<>();
        for (i = 0; i < array.length(); i++) {
            JSONObject obj = array.getJSONObject(i);
            if (obj.getString("Date").equals(from)) {
                break;
            }
        }
        for (; i < array.length(); i++) {

            JSONObject obj = array.getJSONObject(i);

            price = Price.builder()
                    .Date(obj.getString("Date"))
                    .Open(obj.getDouble("Open"))
                    .High(obj.getDouble("High"))
                    .Close(obj.getDouble("Close"))
                    .Low(obj.getDouble("Low"))
                    .Volume(obj.getInt("Volume"))
                    .build();

            profit = getProfit(price);
            totalProfit+=profit.getProfitPerUnit();
            response.add(ProfitDayWise.builder().Date(obj.getString("Date"))
                    .ProfitPerDay(profit.getProfitPerUnit())
                    .Strategy(profit.getStrategy()).
                            build());
            if (obj.getString("Date").equals(to)) {
                break;
            }
        }
        DecimalFormat df = new DecimalFormat("#.###");
        return IntradayResponse.builder().Breakdown(response).TotalProfit(
                Double.valueOf(df.format(totalProfit))
        ).build();
    }
}

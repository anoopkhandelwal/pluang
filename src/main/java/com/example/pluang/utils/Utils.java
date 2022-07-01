package com.example.pluang.utils;

public class Utils {
    public static String getDateConverter(String date){
        String[] splitter = date.split("-");
        date = String.format("%s/%s/%s",splitter[1],splitter[0],splitter[2].substring(2));
        return date;
    }
}

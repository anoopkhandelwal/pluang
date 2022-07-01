package com.example.pluang.constants;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ReadFile {
    public static JSONArray getFileData() throws JSONException, IOException {
        String dirPath = FileSystems.getDefault().getPath("").toAbsolutePath().toString();
        String filePath = "src/main/java/com/example/pluang/constants/demo.json";
        String location = String.format("%s/%s",dirPath,filePath);
        String text = new String(Files.readAllBytes(Paths.get(location)), StandardCharsets.UTF_8);
        JSONArray array = new JSONArray(text);
        JSONArray reverseArray = new JSONArray();
        for (int i = array.length()-1; i>=0; i--) {
            reverseArray.put(array.get(i));
        }
        return reverseArray;
    }

}

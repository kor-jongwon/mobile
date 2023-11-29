package com.example.mobile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PlantsHttpRequest {
    public List<Plant> getPlants(String urlString) {
        HttpURLConnection urlConnection = null;
        StringBuilder result = new StringBuilder();
        List<Plant> plants = new ArrayList<>();

        try {
            URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            // JSON 파싱 및 Plant 객체 리스트 생성
            // JSON 파싱 라이브러리 사용 권장 (예: Gson, Jackson)
            // 예시에서는 간단한 파싱만 진행합니다.

            // plants = parseJsonToPlants(result.toString());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        return plants;
    }
}
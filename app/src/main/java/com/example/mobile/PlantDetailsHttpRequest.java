package com.example.mobile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class PlantDetailsHttpRequest {

    public Plant getPlantByName(String plantName) {
        HttpURLConnection urlConnection = null;
        StringBuilder result = new StringBuilder();

        try {
            URL url = new URL( api_url.GETPLANT + plantName);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            JSONObject response = new JSONObject(result.toString());
            JSONObject plantJson = response.getJSONObject("plant");

            // JSON 데이터를 Plant 객체로 변환
            Plant plant = new Plant();
            plant.setPlantName(plantJson.getString("plantName"));
            plant.setPlantDating(plantJson.getString("plantDating"));
            // 여기에 다른 필드들도 설정해야 합니다.

            return plant;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }
}
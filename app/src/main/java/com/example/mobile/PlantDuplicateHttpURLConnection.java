package com.example.mobile;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class PlantDuplicateHttpURLConnection {

    public String checkDuplicate(String urlString, Map<String, String> parameters) throws IOException {
        URL url = new URL(buildUrlWithParameters(urlString, parameters));
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        try {
            // HTTP 요청 방식 설정
            urlConnection.setRequestMethod("GET");

            // 응답 받기
            InputStream in = urlConnection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            return response.toString();
        } finally {
            urlConnection.disconnect();
        }
    }

    private String buildUrlWithParameters(String urlString, Map<String, String> parameters) {
        StringBuilder urlBuilder = new StringBuilder(urlString);
        if (!parameters.isEmpty()) {
            urlBuilder.append("?");
            for (Map.Entry<String, String> entry : parameters.entrySet()) {
                urlBuilder.append(entry.getKey())
                        .append("=")
                        .append(entry.getValue())
                        .append("&");
            }
            urlBuilder.setLength(urlBuilder.length() - 1); // 마지막 '&' 제거
        }
        return urlBuilder.toString();
    }
}

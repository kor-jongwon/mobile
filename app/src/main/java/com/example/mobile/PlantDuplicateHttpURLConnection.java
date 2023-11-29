package com.example.mobile;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class PlantDuplicateHttpURLConnection {

    /**
     * 식물 이름의 중복 여부를 확인하는 HTTP GET 요청을 수행합니다.
     *
     * @param urlString 요청을 보낼 서버의 URL
     * @param parameters 요청에 포함할 매개변수 (식물 이름 등)
     * @return 서버 응답 문자열
     * @throws IOException 네트워크 요청 중 발생하는 예외
     */
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

    /**
     * URL에 매개변수를 추가하여 전체 URL을 생성합니다.
     *
     * @param urlString 기본 URL
     * @param parameters 추가할 매개변수
     * @return 완성된 URL 문자열
     */
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

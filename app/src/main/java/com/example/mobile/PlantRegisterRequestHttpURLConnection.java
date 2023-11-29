package com.example.mobile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class PlantRegisterRequestHttpURLConnection {

    public String request(String _url, Map<String, String> _params, byte[] imageData, String fileName) throws IOException {
        // 멀티파트 폼 데이터의 경계 문자열
        String boundary = "----PlantAppBoundary" + System.currentTimeMillis();
        String LINE_FEED = "\r\n";

        URL url = new URL(_url);
        HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
        urlConn.setUseCaches(false);
        urlConn.setDoOutput(true); // POST를 사용하기 위해 설정
        urlConn.setDoInput(true);
        urlConn.setRequestMethod("POST");
        urlConn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

        OutputStream outputStream = urlConn.getOutputStream();
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream, "UTF-8"), true);

        // 텍스트 파라미터 추가
        for (Map.Entry<String, String> entry : _params.entrySet()) {
            writer.append("--").append(boundary).append(LINE_FEED);
            writer.append("Content-Disposition: form-data; name=\"").append(entry.getKey()).append("\"").append(LINE_FEED);
            writer.append("Content-Type: text/plain; charset=UTF-8").append(LINE_FEED);
            writer.append(LINE_FEED);
            writer.append(entry.getValue()).append(LINE_FEED);
            writer.flush();
        }

        // 이미지 파일 데이터 추가
        writer.append("--").append(boundary).append(LINE_FEED);
        writer.append("Content-Disposition: form-data; name=\"image\"; filename=\"").append(fileName).append("\"").append(LINE_FEED);
        writer.append("Content-Type: ").append("image/jpeg").append(LINE_FEED);
        writer.append("Content-Transfer-Encoding: binary").append(LINE_FEED);
        writer.append(LINE_FEED);
        writer.flush();

        outputStream.write(imageData);
        outputStream.flush();

        writer.append(LINE_FEED);
        writer.flush();

        // 요청 종료
        writer.append("--").append(boundary).append("--").append(LINE_FEED);
        writer.close();

        // 서버 응답 처리
        StringBuilder response = new StringBuilder();
        int responseCode = urlConn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
        } else {
            throw new IOException("Server returned non-OK status: " + responseCode);
        }

        urlConn.disconnect();
        return response.toString();
    }
}
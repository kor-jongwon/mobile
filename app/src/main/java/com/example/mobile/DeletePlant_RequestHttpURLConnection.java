package com.example.mobile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class DeletePlant_RequestHttpURLConnection {

    // 이 메서드는 URL과 파라미터를 받아서 서버에 DELETE 요청을 보내고 응답을 문자열로 반환합니다.
    public String requestDelete(String _url, Map<String, String> _params) {
        // HttpURLConnection 참조 변수.
        HttpURLConnection urlConn = null;

        // 결과를 저장할 변수.
        StringBuilder sb = new StringBuilder();

        try {
            URL url = new URL(_url);
            urlConn = (HttpURLConnection) url.openConnection();

            // [1-1]. urlConn 설정.
            urlConn.setConnectTimeout(15000); // 연결 타임아웃 설정
            urlConn.setReadTimeout(5000);     // 읽기 타임아웃 설정
            urlConn.setRequestMethod("DELETE"); // 요청 방식 설정
            urlConn.setDoOutput(true);        // OutputStream으로 DELETE 데이터를 넘겨주겠다는 옵션

            // [1-2]. 파라미터 전송
            OutputStream os = urlConn.getOutputStream();
            StringBuilder params = new StringBuilder();

            for (Map.Entry<String, String> param : _params.entrySet()) {
                if (params.length() != 0) params.append('&');
                params.append(param.getKey()).append('=').append(param.getValue());
            }

            os.write(params.toString().getBytes("UTF-8"));
            os.flush();
            os.close();

            // [1-3]. 연결 요청 확인
            if (urlConn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return null;
            }

            // [1-4]. 읽어온 결과물 리턴
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConn.getInputStream(), "UTF-8"));

            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConn != null) urlConn.disconnect();
        }

        return sb.toString();
    }
}
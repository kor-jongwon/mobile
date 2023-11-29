package com.example.mobile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class UpdatePlant_RequestHttpURLConnection {
    public String requestPost(String _url, Map<String, String> _params) {
        HttpURLConnection urlConn = null;
        StringBuilder sb = new StringBuilder();

        try {
            URL url = new URL(_url);
            urlConn = (HttpURLConnection) url.openConnection();

            urlConn.setConnectTimeout(15000);
            urlConn.setReadTimeout(5000);
            urlConn.setRequestMethod("POST"); // PUT 요청 대신 POST 사용
            urlConn.setDoInput(true);
            urlConn.setDoOutput(true);
            urlConn.setUseCaches(false);

            OutputStream os = urlConn.getOutputStream();
            StringBuilder params = new StringBuilder();

            for (Map.Entry<String, String> param : _params.entrySet()) {
                if (params.length() != 0) params.append('&');
                params.append(param.getKey()).append('=').append(param.getValue());
            }

            os.write(params.toString().getBytes("UTF-8"));
            os.flush();
            os.close();

            if (urlConn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return null;
            }

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
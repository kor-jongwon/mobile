package com.example.mobile;
import static com.example.mobile.SignUpActivity.id;
import android.content.ContentValues;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Id_Duplicates_RequestHttpURLConnection {

    public String request(String _url, ContentValues _params){

        // HttpURLConnection 참조 변수.
        HttpURLConnection urlConn = null;

        /**
         * 1. HttpURLConnection을 통해 web의 데이터를 가져온다.
         * */
        try{
            URL url = new URL(_url);
            urlConn = (HttpURLConnection) url.openConnection();
            // [1-1]. urlConn 설정.
            urlConn.setConnectTimeout(15000);
            urlConn.setReadTimeout(5000);
            urlConn.setDoInput(true);
            urlConn.setDoOutput(true);
            urlConn.setUseCaches(false);
            urlConn.setRequestMethod("POST"); // URL 요청에 대한 메소드 설정 : POST.
            urlConn.setRequestProperty("Accept-Charset", "UTF-8"); // Accept-Charset 설정.
            urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); // Content-Type 설정
            urlConn.setRequestProperty("apikey", ""); // ""안에 apikey를 입력

            // [1-2]. parameter 전달 및 데이터 읽어오기.
            String strParams = "id=" + id; // 변경: 파라미터 직접 설정
            OutputStream os = urlConn.getOutputStream();
            os.write(strParams.getBytes("UTF-8"));
            os.flush();
            os.close();

            // [1-3]. 연결 요청 확인.
            if (urlConn.getResponseCode() != HttpURLConnection.HTTP_OK)
                return null;

            // [1-4]. 읽어온 결과물 리턴.
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConn.getInputStream(), "UTF-8"));
            String line;
            String page = "";

            while ((line = reader.readLine()) != null){
                page += line;
            }
            return page;

        } catch (MalformedURLException e) { // for URL.
            e.printStackTrace();
        } catch (IOException e) { // for openConnection().
            e.printStackTrace();
        } finally {
            if (urlConn != null)
                urlConn.disconnect();
        }

        return null;
    }
}

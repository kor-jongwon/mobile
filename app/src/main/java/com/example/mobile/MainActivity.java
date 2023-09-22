package com.example.mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.content.ContentValues;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static boolean emailDuplicated;
    public EditText edit_email,edit_password;
    public Button btn_login,btn_register;
    public static String email,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_login = findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.btn_register);
        edit_email = findViewById(R.id.edit_email);
        edit_password = findViewById(R.id.edit_password);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = edit_email.getText().toString();
                password = edit_password.getText().toString();

                String url = "http://100.100.113.96:3000/login";
                NetworkTask networkTask = new NetworkTask(url, null);
                networkTask.execute();
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 회원가입 화면으로 이동하는 인텐트 생성
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent); // 인텐트 실행
            }
        });
    }

    public class NetworkTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;
        public NetworkTask(String url, ContentValues values) {

            this.url = url;
            this.values = values;
        }

        @Override
        protected String doInBackground(Void... params) {

            String result; // 요청 결과를 저장할 변수.
            LoginRequestHttpURLConnection loginRequestHttpURLConnection = new LoginRequestHttpURLConnection();
            result = loginRequestHttpURLConnection.request(url, values); // 해당 URL로 부터 결과물을 얻어온다.

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (s != null) {
                // 서버로부터 받은 결과를 Toast 메시지로 표시
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
            } else {
                // 서버 요청 실패 시 메시지를 표시하거나 다른 처리를 수행할 수 있음
                Toast.makeText(getApplicationContext(), "서버 요청 실패", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
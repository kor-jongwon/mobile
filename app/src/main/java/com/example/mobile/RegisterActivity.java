package com.example.mobile;

import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    public static boolean emailDuplicated;
    public EditText edit_email, edit_password, edit_name;
    public Button btn_duplicated, btn_sign_up;
    public static String email, password, name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        emailDuplicated = false;


        btn_duplicated = findViewById(R.id.buttonCheckDuplicate);
        btn_sign_up = findViewById(R.id.buttonSignUp);

        edit_name = findViewById(R.id.editTextUsername);
        edit_email = findViewById(R.id.editTextEmail);
        edit_password = findViewById(R.id.editTextPassword);
        btn_duplicated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 'email'과 'password'는 MainActivity에서 가져온 값으로 이미 설정되어 있음.
                email = edit_email.getText().toString(); // 'edit_name'에서 'name' 값을 가져옴
                String url = "http://100.100.113.96:3000/duplicated";

                // 'name' 값을 가지고 새로운 ContentValue를 생성하여 전송
                ContentValues params = new ContentValues();
                params.put("email", email);

                duplicated_NetworkTask dup_networkTask = new duplicated_NetworkTask(url, params);
                dup_networkTask.execute();
            }
        });

        btn_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (emailDuplicated) {
                    // 서버로부터 받은 결과를 Toast 메시지로 표시
                    email = edit_email.getText().toString();
                    password = edit_password.getText().toString();
                    name = edit_name.getText().toString();

                    // 'name' 값을 가지고 새로운 ContentValue를 생성하여 전송
                    ContentValues params = new ContentValues();
                    params.put("email", email);
                    params.put("password", password);
                    params.put("name", name);

                    String url = "http://100.100.113.96:3000/signup";
                    NetworkTask networkTask = new NetworkTask(url, null);
                    networkTask.execute();
                    Toast.makeText(getApplicationContext(), "회원가입 성공!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent); // 인텐트 실행
                } else {
                    // 서버 요청 실패 시 메시지를 표시하거나 다른 처리를 수행할 수 있음
                    Toast.makeText(getApplicationContext(), "이메일 중복 확인 해주세요!", Toast.LENGTH_SHORT).show();
                }

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
            SignUpRequestHttpURLConnection signupRequestHttpURLConnection = new SignUpRequestHttpURLConnection();
            result = signupRequestHttpURLConnection.request(url, values); // 해당 URL로 부터 결과물을 얻어온다.

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
    public class duplicated_NetworkTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;
        public duplicated_NetworkTask(String url, ContentValues values) {

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
                if (s.contains("가능한")) {
                    emailDuplicated = true;
                }
            } else {
                // 서버 요청 실패 시 메시지를 표시하거나 다른 처리를 수행할 수 있음
                Toast.makeText(getApplicationContext(), "서버 요청 실패", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
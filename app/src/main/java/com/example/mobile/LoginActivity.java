package com.example.mobile;

import static com.example.mobile.valid_data.isPasswordValid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.content.ContentValues;
import android.os.AsyncTask;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    public EditText edit_email,edit_password;
    public Button btn_login,btn_register, btn_find_id, btn_find_pw;
    public static String email,password;
    public TextView tv_error_email, tv_error_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_login = findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.btn_register);
        btn_find_id = findViewById(R.id.btn_find_id);
        btn_find_pw = findViewById(R.id.btn_find_pw);

        edit_email = findViewById(R.id.editTextEmail);
        edit_password = findViewById(R.id.editTextPassword);
        tv_error_email = findViewById(R.id.tv_error_email);
        tv_error_password = findViewById(R.id.tv_error_password);

        // 이메일 입력 변경 감지
        edit_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                // 이메일 형식 확인
                if (!Patterns.EMAIL_ADDRESS.matcher(s.toString()).matches()) {
                    tv_error_email.setTextColor(Color.parseColor("#FF0000"));
                    tv_error_email.setText("이메일 형식으로 입력해주세요.");
                    edit_email.setBackgroundResource(R.drawable.red_edittext);
                }

                // 올바른 이메일 형식인 경우
                if (Patterns.EMAIL_ADDRESS.matcher(s.toString()).matches()) {
                    tv_error_email.setText(" ");
                    edit_email.setBackgroundResource(R.drawable.green_edittext);
                }

                // 이메일이 비어있는 경우
                if (s.toString().isEmpty()) {
                    tv_error_email.setText("");
                    edit_email.setBackgroundResource(R.drawable.white_edittext);
                }
            }
        });

        // 비밀번호 입력 변경 감지
        edit_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                // 비밀번호 형식 확인
                if (!isPasswordValid(s.toString())) {
                    tv_error_password.setTextColor(Color.parseColor("#FF0000"));
                    tv_error_password.setText("비밀번호 형식으로 입력해주세요.");
                    edit_password.setBackgroundResource(R.drawable.red_edittext);
                }

                // 올바른 비밀번호 형식인 경우
                if (isPasswordValid(s.toString())) {
                    tv_error_password.setText(" ");
                    edit_password.setBackgroundResource(R.drawable.green_edittext);
                }

                // 비밀번호가 비어있는 경우
                if (s.toString().isEmpty()) {
                    tv_error_password.setText(" ");
                    edit_password.setBackgroundResource(R.drawable.white_edittext);
                }
            }
        });
        //Todo:
        //이메일, 비밀번호 입력시 붉은 색 테두리 및 입력 잘못된거 메시지 출력 및 로그인 버튼 비활성화 혹은 메시지 출력
        //버튼 클릭시 이벤트 추가
        //아이디 저장 기능 추가
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = edit_email.getText().toString();
                password = edit_password.getText().toString();

                if (email.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "이메일을 입력해주세요", Toast.LENGTH_SHORT).show();
                }

                else if (password.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                }

                else if (!valid_data.isPasswordValid(password)) {
                    Toast.makeText(getApplicationContext(), "비밀번호형식을 확인해주세요!", Toast.LENGTH_SHORT).show();
                }

                else if (!valid_data.isEmail(email)) {
                    Toast.makeText(getApplicationContext(), "아이디를 확인해주세요!", Toast.LENGTH_SHORT).show();
                }

                else if (!email.isEmpty() & !password.isEmpty() & valid_data.isEmail(email) & valid_data.isPasswordValid(password)) {
                    String url = api_url.LOGIN.getValue();
                    NetworkTask networkTask = new NetworkTask(url, null);
                    networkTask.execute();
                }
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 회원가입 화면으로 이동하는 인텐트 생성
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent); // 인텐트 실행
            }
        });
        btn_find_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 회원가입 화면으로 이동하는 인텐트 생성
                Intent intent = new Intent(LoginActivity.this, FindIdActivity.class);
                startActivity(intent); // 인텐트 실행
            }
        });
        btn_find_pw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 회원가입 화면으로 이동하는 인텐트 생성
                Intent intent = new Intent(LoginActivity.this, ResetPasswordActivity.class);
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
                try {
                    // JSON 문자열을 파싱하여 JSON 객체로 변환
                    JSONObject jsonObject = new JSONObject(s);

                    // JSON 객체에서 "message" 키의 값을 가져오기
                    String message = jsonObject.optString("message", "");

                    // "message" 값에 "성공하였습니다" 문자열이 포함되어 있는지 확인
                    if (message.contains("성공하였습니다")) {
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent); // 인텐트 실행
                    }

                    // 서버로부터 받은 결과를 Toast 메시지로 표시
                    Toast.makeText(getApplicationContext(), "로그인 성공!", Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                    // JSON 파싱 오류 처리
                }
            }

            else {
                // 서버 요청 실패 시 메시지를 표시하거나 다른 처리를 수행할 수 있음
                Toast.makeText(getApplicationContext(), "서버 요청 실패", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
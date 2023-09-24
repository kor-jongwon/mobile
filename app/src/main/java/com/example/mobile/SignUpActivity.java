package com.example.mobile;

// 필요한 import문을 추가하세요

import static com.example.mobile.valid_data.isPasswordValid;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class SignUpActivity extends AppCompatActivity {

    public static boolean emailDuplicated, check_name, check_password, check_password_valid;

    public EditText edit_email, edit_password, edit_name, edit_password_check;
    public Button btn_duplicated, btn_sign_up;
    public static String email, password, name;
    public TextView tv_error_email, tv_error_password, tv_error_password_check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // 초기화
        emailDuplicated = false;
        check_name = false;
        check_password = false;
        check_password_valid = false;

        // 뷰 초기화
        btn_duplicated = findViewById(R.id.buttonCheckDuplicate);
        btn_sign_up = findViewById(R.id.buttonSignUp);
        edit_name = findViewById(R.id.editTextUsername);
        edit_email = findViewById(R.id.editTextEmail);
        edit_password = findViewById(R.id.editTextPassword);
        edit_password_check = findViewById(R.id.editTextPassword_check);
        tv_error_email = findViewById(R.id.tv_error_email);
        tv_error_password = findViewById(R.id.tv_error_password);
        tv_error_password_check = findViewById(R.id.tv_error_password_check);

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
                emailDuplicated = false;
                btn_sign_up.setEnabled(false);

                // 이메일 형식 확인
                if (!Patterns.EMAIL_ADDRESS.matcher(s.toString()).matches()) {
                    tv_error_email.setTextColor(Color.parseColor("#FF0000"));
                    tv_error_email.setText("이메일 형식으로 입력해주세요.");
                    edit_email.setBackgroundResource(R.drawable.red_edittext);
                    btn_duplicated.setEnabled(false);
                }

                // 올바른 이메일 형식인 경우
                if (Patterns.EMAIL_ADDRESS.matcher(s.toString()).matches()) {
                    tv_error_email.setText(" ");
                    edit_email.setBackgroundResource(R.drawable.green_edittext);
                    btn_duplicated.setEnabled(true);

                    // 모든 조건 충족 시 회원가입 버튼 활성화
                    if (emailDuplicated && check_name && check_password && check_password_valid) {
                        btn_sign_up.setEnabled(true);
                    }
                }

                // 이메일이 비어있는 경우
                if (s.toString().isEmpty()) {
                    tv_error_email.setText("");
                    edit_email.setBackgroundResource(R.drawable.white_edittext);
                    btn_duplicated.setEnabled(false);
                }
            }
        });

        // 이름 입력 변경 감지
        edit_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                check_name = false;
                btn_sign_up.setEnabled(false);

                // 이름이 비어있지 않은 경우
                if (!s.toString().isEmpty()) {
                    check_name = true;
                    edit_name.setBackgroundResource(R.drawable.green_edittext);

                    // 모든 조건 충족 시 회원가입 버튼 활성화
                    if (emailDuplicated && check_name && check_password && check_password_valid) {
                        btn_sign_up.setEnabled(true);
                    }
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
                //Todo:
                // 비밀번호 자릿수가 12자리인지 비교 필요 지금은 비밀번호 안에 대문자,소문자, 숫자 특수문자만 있으면 조건 충족

                check_password = false;
                btn_sign_up.setEnabled(false);

                // 비밀번호 형식 확인
                if (!isPasswordValid(s.toString())) {
                    tv_error_password.setTextColor(Color.parseColor("#FF0000"));
                    //todo:
                    // 비밀번호 형식을 표시하기!
                    tv_error_password.setText("비밀번호 형식으로 입력해주세요.");
                    edit_password.setBackgroundResource(R.drawable.red_edittext);
                }

                // 올바른 비밀번호 형식인 경우
                if (isPasswordValid(s.toString())) {
                    check_password = true;
                    tv_error_password.setText(" ");
                    edit_password.setBackgroundResource(R.drawable.green_edittext);

                    // 모든 조건 충족 시 회원가입 버튼 활성화
                    if (emailDuplicated && check_name && check_password && check_password_valid) {
                        btn_sign_up.setEnabled(true);
                    }
                }

                // 비밀번호가 비어있는 경우
                if (s.toString().isEmpty()) {
                    tv_error_password.setText(" ");
                    edit_password.setBackgroundResource(R.drawable.white_edittext);
                }
            }
        });

        // 비밀번호 확인 입력 변경 감지
        edit_password_check.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                check_password_valid = false;
                btn_sign_up.setEnabled(false);

                // 비밀번호 형식 확인
                if (isPasswordValid(s.toString())) {
                    // 비밀번호와 일치하는지 확인
                    if (edit_password.getText().toString().equals(s.toString())) {
                        tv_error_password_check.setText(" ");
                        edit_password_check.setBackgroundResource(R.drawable.green_edittext);
                        check_password_valid = true;

                        // 모든 조건 충족 시 회원가입 버튼 활성화
                        if (emailDuplicated && check_name && check_password && check_password_valid) {
                            btn_sign_up.setEnabled(true);
                        }
                    } else {
                        tv_error_password_check.setText("비밀번호와 일치하지 않습니다.");
                        edit_password_check.setBackgroundResource(R.drawable.red_edittext);
                    }
                }

                // 비밀번호 형식이 잘못된 경우
                if (!isPasswordValid(s.toString())) {
                    tv_error_password_check.setText("비밀번호 형식으로 입력해주세요.");
                    edit_password_check.setBackgroundResource(R.drawable.red_edittext);
                }

                // 비밀번호 확인이 비어있는 경우
                if (s.toString().isEmpty()) {
                    tv_error_password_check.setText(" ");
                    edit_password_check.setBackgroundResource(R.drawable.white_edittext);
                }
            }
        });

        // ActionBar 설정 및 뒤로 가기 버튼 활성화
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // 이메일 중복 확인 버튼 클릭 리스너
        btn_duplicated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = edit_email.getText().toString();

                // 이메일이 비어있는 경우
                if (email.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "이메일을 입력해주세요", Toast.LENGTH_SHORT).show();
                }

                // 올바른 이메일 형식인 경우
                else if (valid_data.isEmail(email)) {
                    String url = api_url.IDDUPLICATE.getValue();
                    ContentValues params = new ContentValues();
                    params.put("email", email);
                    duplicated_NetworkTask dup_networkTask = new duplicated_NetworkTask(url, params);
                    dup_networkTask.execute();
                }

                // 올바르지 않은 이메일 형식인 경우
                else {
                    Toast.makeText(getApplicationContext(), "이메일 형식을 확인해주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 회원가입 버튼 클릭 리스너
        btn_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = edit_email.getText().toString();
                password = edit_password.getText().toString();
                name = edit_name.getText().toString();

                // 이메일이 비어있는 경우
                if (email.isEmpty()) {
                    emailDuplicated = false;
                    Toast.makeText(getApplicationContext(), "이메일 중복 확인 해주세요!", Toast.LENGTH_SHORT).show();
                }

                // 이메일 중복 확인이 되지 않은 경우
                else if (!email.isEmpty() && !emailDuplicated) {
                    Toast.makeText(getApplicationContext(), "이메일 중복 확인 해주세요!", Toast.LENGTH_SHORT).show();
                }

                // 비밀번호가 비어있는 경우
                else if (password.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "비밀번호를 입력해주세요!", Toast.LENGTH_SHORT).show();
                }

                // 이름이 비어있는 경우
                else if (name.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "이름을 입력해주세요!", Toast.LENGTH_SHORT).show();
                }

                // 비밀번호 형식이 올바르지 않은 경우
                else if (!isPasswordValid(password)) {
                    Toast.makeText(getApplicationContext(), "비밀번호 형식을 확인해주세요!", Toast.LENGTH_SHORT).show();
                }

                // 모든 조건을 만족하는 경우 회원가입 요청
                else if (!email.isEmpty() && !password.isEmpty() && !name.isEmpty() && emailDuplicated && isPasswordValid(password)) {
                    ContentValues params = new ContentValues();
                    params.put("email", email);
                    params.put("password", password);
                    params.put("name", name);

                    String url = api_url.SIGNUP.getValue();
                    NetworkTask networkTask = new NetworkTask(url, null);
                    networkTask.execute();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // 뒤로 가기 버튼을 클릭할 때 액티비티를 종료
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
            String result;
            SignUpRequestHttpURLConnection signupRequestHttpURLConnection = new SignUpRequestHttpURLConnection();
            result = signupRequestHttpURLConnection.request(url, values);
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
                    //Todo:
                    //node.js에서 json으로 {message : "회원가입 성공"}이 넘어오면 message 값 비교해서 LoginActivity으로 창전환

                    if (message.equals("회원가입 성공")) {
                        Toast.makeText(getApplicationContext(), "회원가입 성공!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    // JSON 파싱 오류 처리
                }
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
            String result;
            Id_Duplicates_RequestHttpURLConnection loginRequestHttpURLConnection = new Id_Duplicates_RequestHttpURLConnection();
            result = loginRequestHttpURLConnection.request(url, values);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (s != null) {

                try {
                    // JSON 문자열을 파싱하여 JSON 객체로 변환
                    JSONObject jsonObject = new JSONObject(s);
                    //Todo:
                    // Json에 값이 equals로 안맞아짐...해결필요

                    // JSON 객체에서 "message" 키의 값을 가져오기
                    String message = jsonObject.optString("message", "");

                    if (message.equals("사용 가능한 이메일입니다.")) {
                            emailDuplicated = true;
                    }
                    else{
                        Toast.makeText(getApplicationContext(),  message, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    // JSON 파싱 오류 처리
                }
            } else {
                // 서버 요청 실패 시 메시지를 표시하거나 다른 처리를 수행할 수 있음
                Toast.makeText(getApplicationContext(), "서버 요청 실패", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

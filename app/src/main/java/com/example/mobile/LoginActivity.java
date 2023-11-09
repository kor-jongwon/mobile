package com.example.mobile;

import static com.example.mobile.valid_data.isPasswordValid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.content.ContentValues;
import android.os.AsyncTask;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    public static boolean check_id, check_password;
    public EditText edit_id,edit_password;
    public Button btn_login;
    public static String id,password;
    public TextView tv_error_id, tv_error_password ;
    public CheckBox saveIdCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        check_id = false;
        check_password = false;

        btn_login = findViewById(R.id.btn_login);


        saveIdCheckBox = findViewById(R.id.saveIdCheckBox);
        edit_id = findViewById(R.id.editTextid);
        edit_password = findViewById(R.id.editTextPassword);
        tv_error_id = findViewById(R.id.tv_error_id);
        tv_error_password = findViewById(R.id.tv_error_password);

        btn_login.setEnabled(false);

        // 이메일 입력 변경 감지
        edit_id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                tv_error_id.setVisibility(View.GONE);
                // 이메일이 비어있는 경우
                if (!s.toString().isEmpty()) {
                    tv_error_id.setText("");
                    edit_id.setBackgroundResource(R.drawable.green_edittext);
                    check_id =true;
                    if (check_id & check_password){
                        btn_login.setEnabled(true);
                    }
                }
                // 이메일이 비어있는 경우
                if (s.toString().isEmpty()) {
                    check_id = false;
                    tv_error_id.setText("");
                    edit_id.setBackgroundResource(R.drawable.white_edittext);
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
                tv_error_password.setVisibility(View.GONE);
                // 비밀번호 형식 확인
                if (!isPasswordValid(s.toString())) {
                    tv_error_password.setVisibility(View.VISIBLE);
                    btn_login.setEnabled(false);
                    check_password=false;
                    tv_error_password.setTextColor(Color.parseColor("#FF0000"));
                    tv_error_password.setText("비밀번호 형식으로 입력해주세요.");
                    edit_password.setBackgroundResource(R.drawable.red_edittext);
                }

                // 올바른 비밀번호 형식인 경우
                if (isPasswordValid(s.toString())) {
                    tv_error_password.setText(" ");
                    edit_password.setBackgroundResource(R.drawable.green_edittext);
                    check_password=true;
                    if (check_password & check_id){
                        btn_login.setEnabled(true);
                    }
                }

                // 비밀번호가 비어있는 경우
                if (s.toString().isEmpty()) {
                    tv_error_password.setVisibility(View.GONE);
                    check_password = false;
                    btn_login.setEnabled(false);
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
                id = edit_id.getText().toString();
                password = edit_password.getText().toString();


                String url = api_url.LOGIN.getValue();
                NetworkTask networkTask = new NetworkTask(url, null);
                networkTask.execute();

            }
        });



        saveIdCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            // 아이디를 저장하는 함수
            private void saveUsername(String id) {
                SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("id", id);
                editor.apply();
            }

            // 저장된 아이디를 가져오는 함수
            private String getSavedUsername() {
                SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                return preferences.getString("id", "");
            }

            // 체크박스 상태에 따라 아이디 저장 또는 삭제
            private void handleUsernameSave(boolean isChecked) {
                if (isChecked) {
                    String id = edit_id.getText().toString();
                    if (id.isEmpty()){
                        tv_error_id.setVisibility(View.VISIBLE);
                        edit_id.setBackgroundResource(R.drawable.red_edittext);
                        tv_error_id.setText("아이디를 입력하세요.");
                        saveIdCheckBox.setChecked(false);
                    }
                    else {
                        // 체크박스가 체크된 경우 아이디 저장
                        saveUsername(edit_id.getText().toString());
                    }

                } else {
                    // 체크박스가 체크 해제된 경우 저장된 아이디 삭제
                    saveUsername("");
                }
            }
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                handleUsernameSave(isChecked);
            }
        });


    }
    public void launchForgotPasswordActivity(View view) {
        Intent intent = new Intent(this, FindIdActivity.class);
        startActivity(intent);
    }

    public void launchsigupActivity(View view) {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
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
                        if (message.contains("로그인에 성공하였습니다. 그러나 식물 데이터가 없습니다.")) {
                            Intent intent = new Intent(LoginActivity.this, Plant_RegisterList.class);
                            startActivity(intent); // 인텐트 실행
                        }
                        else{
                            Intent intent = new Intent(LoginActivity.this, Plant_RegisterList.class);
                            startActivity(intent); // 인텐트 실행
                        }
                    }
                    else{
                        edit_id.setBackgroundResource(R.drawable.red_edittext);
                        edit_password.setBackgroundResource(R.drawable.red_edittext);
                        tv_error_id.setVisibility(View.VISIBLE);
                        tv_error_password.setVisibility(View.VISIBLE);
                        tv_error_id.setText("아이디를 확인해주세요");
                        tv_error_password.setText("비밀번호를 확인해주세요");

                    }

                    // 서버로부터 받은 결과를 Toast 메시지로 표시

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
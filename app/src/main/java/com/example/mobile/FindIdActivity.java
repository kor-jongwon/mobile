package com.example.mobile;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONException;
import org.json.JSONObject;

public class FindIdActivity extends AppCompatActivity {
    public static boolean check_email, check_name, check_ver;
    public static String  email, name, verification;
    private EditText editTextName, editTextEmail, editTextVer;
    private TextView tv_error_email, textViewverifi, textViewTimeLimit, find_id_textview, find_pw_textview;
    private Button buttonFindId, btn_find_id, btn_reset_password;
    private CountDownTimer countDownTimer;
    private long timeLeftInMillis = 5 * 60 * 1000; // 5분 (밀리초 단위)


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_id);

        // XML 레이아웃에서 위젯들을 찾아서 변수에 연결합니다.
        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextVer = findViewById(R.id.editText_VerificationCode);

        textViewTimeLimit = findViewById(R.id.textViewTimeLimit);
        textViewverifi = findViewById(R.id.textView_VerificationCode);


        tv_error_email = findViewById(R.id.tv_error_email);

        btn_find_id = findViewById(R.id.btn_findId);
        btn_reset_password = findViewById(R.id.btn_resetPassword2323);
        buttonFindId = findViewById(R.id.btn_find_id);

        check_email = false;
        check_name = false;

        buttonFindId.setEnabled(false);
        updateCountdownText();



        // 이름 입력 변경 감지
        editTextName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                check_name = false;
                buttonFindId.setEnabled(false);

                // 이름이 비어있는 경우
                if (s.toString().isEmpty()) {
                    buttonFindId.setEnabled(false);
                    check_name = false;
                    editTextName.setBackgroundResource(R.drawable.white_edittext);
                }
                if (!s.toString().isEmpty()) {
                    check_name = true;
                    editTextName.setBackgroundResource(R.drawable.green_edittext);

                    if (check_name & check_email) {
                        buttonFindId.setText("인증번호 받기");
                        buttonFindId.setEnabled(true);
                    }
                }
            }
        });

        // 이메일 입력 변경 감지
        editTextEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                check_email = false;
                buttonFindId.setEnabled(false);

                // 이메일 형식 확인
                if (!Patterns.EMAIL_ADDRESS.matcher(s.toString()).matches()) {
                    tv_error_email.setTextColor(Color.parseColor("#FF0000"));
                    tv_error_email.setText("이메일 형식으로 입력해주세요.");
                    editTextEmail.setBackgroundResource(R.drawable.red_edittext);
                    buttonFindId.setEnabled(false);
                    check_email = false;
                }

                // 올바른 이메일 형식인 경우
                if (Patterns.EMAIL_ADDRESS.matcher(s.toString()).matches()) {
                    tv_error_email.setText(" ");
                    editTextEmail.setBackgroundResource(R.drawable.green_edittext);
                    check_email = true;

                    // 모든 조건 충족 시 회원가입 버튼 활성화
                    if (check_email & check_name) {
                        buttonFindId.setText("인증번호 받기");
                        buttonFindId.setEnabled(true);
                    }
                }

                // 이메일이 비어있는 경우
                if (s.toString().isEmpty()) {
                    tv_error_email.setText("");
                    editTextEmail.setBackgroundResource(R.drawable.white_edittext);
                    check_email = false;
                    buttonFindId.setEnabled(false);
                }
            }
        });


        // ActionBar 설정 및 뒤로 가기 버튼 활성화
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로 가기 버튼 활성화
        }

        btn_find_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "아이디 찾기 페이지 입니다.", Toast.LENGTH_SHORT).show();
            }
        });


        // 아이디 찾기 버튼의 클릭 이벤트를 설정합니다.
        buttonFindId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 email = editTextEmail.getText().toString();

                if (!check_ver) {
                    ViewGroup.MarginLayoutParams views = (ViewGroup.MarginLayoutParams) buttonFindId.getLayoutParams();
                    views.topMargin = 50;
                    //    if (editTextV)
                    editTextVer.setVisibility(View.VISIBLE);
                    textViewTimeLimit.setVisibility(View.VISIBLE);
                    textViewverifi.setVisibility(View.VISIBLE);

                    name  = editTextName.getText().toString();
                    email = editTextEmail.getText().toString();

                    ContentValues params = new ContentValues();

                    params.put("email", email);
                    params.put("name",name);


                    // NetworkTask 실행
                    String url = api_url.FINDID.getValue();

// params 설정
                    NetworkTask networkTask = new NetworkTask(url, params, new NetworkTask.OnTaskCompleted() {
                        @Override
                        public void onTaskCompleted(String result) {
                            if (result != null) {
                                // 작업이 성공적으로 완료된 경우 result에 결과가 전달됩니다.
                                // 이곳에서 결과를 처리하세요.
                                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                verification = result;
                            } else {
                                // 작업이 실패한 경우 또는 결과가 없는 경우 처리하세요.
                                Toast.makeText(getApplicationContext(), "인증실패 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    networkTask.execute();

                    buttonFindId.setText("인증번호 확인");
                    textViewTimeLimit.setText("남은시간 5:00");
                    startCountdown();
                    buttonFindId.setEnabled(false);
                    check_ver = true;
                }
                else if (check_ver){
                    Toast.makeText(getApplicationContext(), verification, Toast.LENGTH_SHORT).show();
                    if (editTextVer.toString() == verification) {
                        // 사용자가 입력한 이름과 이메일을 가져옵니다.
                        //intent id or id가 없는경우
                        Toast.makeText(getApplicationContext(), "인증번호가 확인되었습니다", Toast.LENGTH_SHORT).show();
                    }
                    else if (editTextVer.toString() != verification) {
                        if (!editTextVer.toString().isEmpty()) {
                            Toast.makeText(getApplicationContext(), "인증번호를 다시 확인해주세요2", Toast.LENGTH_SHORT).show();
                        }
                        else if (editTextVer.toString().isEmpty()){
                            Toast.makeText(getApplicationContext(), "인증번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
            }
        });
        // 이메일 입력 변경 감지
        editTextVer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()) {
                    buttonFindId.setEnabled(false);
                }
                if (!s.toString().isEmpty()) {
                    buttonFindId.setEnabled(true);
                }

            }
        });
    }
    public void resetPasswordButtonClick(View view) {
        // 클릭 이벤트 처리
        Intent intent = new Intent(this, ResetPasswordActivity.class);
        startActivity(intent);
    }
    private void startCountdown() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountdownText();
            }

            @Override
            public void onFinish() {
                // 카운트다운 종료 후 원하는 동작을 수행하세요.
                textViewTimeLimit.setText("시간 종료");
                buttonFindId.setEnabled(false);
            }
        }.start();
    }
    private void updateCountdownText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format("%02d:%02d", minutes, seconds);
        textViewTimeLimit.setText("남은 시간: " + timeLeftFormatted);
    }
    @Override
    public boolean onSupportNavigateUp() {
        // 뒤로 가기 버튼을 클릭했을 때의 동작을 정의합니다.
        Intent intent = new Intent(FindIdActivity.this, LoginActivity.class);
        startActivity(intent);
        return true;
    }
    public static class NetworkTask extends AsyncTask<Void, Void, String> {
        private String url;
        private ContentValues values;
        private OnTaskCompleted listener; // 작업 완료 리스너

        // 생성자에 리스너 추가
        public NetworkTask(String url, ContentValues values, OnTaskCompleted listener) {
            this.url = url;
            this.values = values;
            this.listener = listener;
        }

        @Override
        protected String doInBackground(Void... params) {
            String result;
            EmailRequestHttpURLConnection emailRequestHttpURLConnection = new EmailRequestHttpURLConnection();
            result = emailRequestHttpURLConnection.request(url, values);
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
                    String verificationCode = jsonObject.optString("verification", "");

                    if (!message.isEmpty() && !verificationCode.isEmpty()) {
                        verification = verificationCode;
                        // 작업 완료 리스너 호출
                        if (listener != null) {
                            listener.onTaskCompleted(verification);
                        }
                    } else {
                        // 작업 완료 리스너 호출
                        if (listener != null) {
                            listener.onTaskCompleted(null);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    // JSON 파싱 오류 처리
                }
            } else {
                // 서버 요청 실패 시 메시지를 표시하거나 다른 처리를 수행할 수 있음
                // 작업 완료 리스너 호출
                if (listener != null) {
                    listener.onTaskCompleted(null);
                }
            }
        }

        // 작업 완료 리스너 인터페이스 정의
        public interface OnTaskCompleted {
            void onTaskCompleted(String result);
        }
    }

}

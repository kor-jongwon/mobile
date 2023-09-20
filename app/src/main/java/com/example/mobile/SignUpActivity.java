package com.example.mobile;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SignUpActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        usernameEditText = findViewById(R.id.editTextUsername);
        emailEditText = findViewById(R.id.editTextEmail);
        passwordEditText = findViewById(R.id.editTextPassword);
        signUpButton = findViewById(R.id.buttonSignUp);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "모든 필드를 입력하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // AsyncTask 실행
                new SignupTask().execute(username, email, password);
            }
        });
    }

    // AsyncTask 클래스 정의
    private class SignupTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            String username = params[0];
            String email = params[1];
            String password = params[2];

            // 네트워크 작업을 여기서 수행합니다.
            return performSignup(username, email, password);
        }

        private Boolean performSignup(String username, String email, String password) {
            try {
                // 회원가입 정보를 JSON으로 만듭니다.
                JSONObject jsonBody = new JSONObject();
                jsonBody.put("username", username);
                jsonBody.put("email", email);
                jsonBody.put("password", password);

                // REST API를 호출하여 회원가입 요청을 보냅니다.
                OkHttpClient client = new OkHttpClient();
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                RequestBody requestBody = RequestBody.create(JSON, jsonBody.toString());

                Request request = new Request.Builder()
                        .url("http://localhost:3000/api/signup") // 실제 API 엔드포인트 URL로 변경
                        .post(requestBody)
                        .build();

                Response response = client.newCall(request).execute();
                String responseBody = response.body().string();

                if (response.isSuccessful()) {
                    // 회원가입 성공 처리
                    return true;
                } else {
                    // 회원가입 실패 처리
                    JSONObject errorJson = new JSONObject(responseBody);
                    String errorMessage = errorJson.getString("error"); // 에러 메시지 필드 이름에 따라 변경
                    showToast("회원가입 실패: " + errorMessage);
                    return false;
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
                showToast("네트워크 오류 또는 서버 오류");
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            super.onPostExecute(success);

            if (success) {
                // 회원가입 성공 처리
                showToast("회원가입 성공!");
                finish();
            } else {
                // 회원가입 실패 처리
                showToast("회원가입 실패");
            }
        }

        private void showToast(String message) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(SignUpActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}

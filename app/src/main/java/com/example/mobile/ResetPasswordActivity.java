package com.example.mobile;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText editTextName, editTextUsername, editTextEmail;
    private Button buttonResetPassword;

    //Todo:
    //SignUpActivity.java 참고
    //붉은색 테두리 붉은 글씨 추가 필요
    //불일치 및 조건 불총족시 버튼 비활성화
    //뒤로가기 버튼(완료)
    //이메일 인증번호 (3:00분시간 타이머 설정)
    //안증번호 생성기
    //이메일 인증번호 전송
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        // XML 레이아웃에서 위젯들을 찾아서 변수에 연결합니다.
        editTextName = findViewById(R.id.editTextName);
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextEmail = findViewById(R.id.editTextEmail);
        buttonResetPassword = findViewById(R.id.buttonResetPassword);

        // ActionBar 설정 및 뒤로 가기 버튼 활성화
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로 가기 버튼 활성화
        }

        // 비밀번호 재설정 버튼의 클릭 이벤트를 설정합니다.
        buttonResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 사용자가 입력한 이름, 아이디, 이메일을 가져옵니다.
                String name = editTextName.getText().toString();
                String username = editTextUsername.getText().toString();
                String email = editTextEmail.getText().toString();

                // 여기에서 비밀번호 재설정 로직을 구현하면 됩니다.
                // 예를 들어, 입력된 정보를 서버로 전송하여 비밀번호를 재설정하는 등의 작업을 수행할 수 있습니다.

                // 비밀번호 재설정 결과에 따라 적절한 처리를 수행합니다.
                // 예를 들어, 비밀번호가 성공적으로 재설정되었을 경우 결과를 화면에 표시하거나 다음 화면으로 이동할 수 있습니다.
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        // 뒤로 가기 버튼을 클릭했을 때의 동작을 정의합니다.
        onBackPressed();
        return true;
    }
}

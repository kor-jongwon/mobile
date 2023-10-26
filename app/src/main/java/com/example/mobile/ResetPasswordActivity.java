package com.example.mobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText editTextId, editTextName, editTextEmail;
    private Button buttonResetPassword, btn_find_id, btn_reset_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        // XML 레이아웃에서 위젯들을 찾아서 변수에 연결합니다.
        editTextId = findViewById(R.id.editTextid);
        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        btn_find_id = findViewById(R.id.btn_find_id);
        btn_reset_password = findViewById(R.id.btn_resetPassword);


        // ActionBar 설정 및 뒤로 가기 버튼 활성화
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로 가기 버튼 활성화
        }

        // 비밀번호 재설정 버튼의 클릭 이벤트를 설정합니다.
      /*  buttonResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 사용자가 입력한 아이디, 이름, 이메일을 가져옵니다.
                String id = editTextId.getText().toString();
                String name = editTextName.getText().toString();
                String email = editTextEmail.getText().toString();

                // 여기에서 비밀번호 재설정 로직을 구현하면 됩니다.
                // 예를 들어, 입력된 정보를 서버로 전송하여 비밀번호를 재설정하는 등의 작업을 수행할 수 있습니다.

                // 비밀번호 재설정 결과에 따라 적절한 처리를 수행합니다.
                // 예를 들어, 비밀번호가 성공적으로 재설정되었을 경우 결과를 화면에 표시하거나 다음 화면으로 이동할 수 있습니다.
            }


        });
     */
        btn_find_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "비밀번호 찾기 페이지 입니다.", Toast.LENGTH_SHORT).show();
            }
        });

        btn_reset_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResetPasswordActivity.this, FindIdActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        // 뒤로 가기 버튼을 클릭했을 때의 동작을 정의합니다.
        Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
        startActivity(intent);
        return true;
    }
}

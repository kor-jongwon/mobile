package com.example.mobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class FindIdActivity extends AppCompatActivity {

    private EditText editTextName, editTextEmail;
    private Button buttonFindId;

    //Todo:
    //붉은색 테두리 붉은 글씨 추가 필요
    //불일치 및 조건 불총족시 버튼 비활성화
    //뒤로가기 버튼(완료)
    //조건에 맞으면 아이디를 어떻게 보여줄지...
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_id);

        // XML 레이아웃에서 위젯들을 찾아서 변수에 연결합니다.
        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        buttonFindId = findViewById(R.id.buttonFindId);

        // ActionBar 설정 및 뒤로 가기 버튼 활성화
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로 가기 버튼 활성화
        }

        // 아이디 찾기 버튼의 클릭 이벤트를 설정합니다.
        buttonFindId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 사용자가 입력한 이름과 이메일을 가져옵니다.
                String name = editTextName.getText().toString();
                String email = editTextEmail.getText().toString();

                // 여기에서 아이디 찾기 로직을 구현하면 됩니다.
                // 예를 들어, 입력된 이름과 이메일을 서버로 전송하여 아이디를 찾는 등의 작업을 수행할 수 있습니다.

                // 아이디를 찾은 결과에 따라 적절한 처리를 수행합니다.
                // 예를 들어, 아이디를 찾았을 경우 결과를 화면에 표시하거나 다음 화면으로 이동할 수 있습니다.
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

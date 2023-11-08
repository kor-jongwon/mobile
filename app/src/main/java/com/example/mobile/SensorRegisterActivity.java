package com.example.mobile;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SensorRegisterActivity extends AppCompatActivity {
    private EditText editTextName;
    private EditText editTextId;
    private Button btn_auth, btn_fin;

    private boolean special_empty = false;
    private boolean caseSensitive = false;
    private boolean empty = false;

    public static boolean check_sensorId, check_sensorName;

    // 센서 ID 대소문자 구분 검사
    private boolean isIdCaseSensitive (String id){
        // 대소문자 구분을 원하는 경우:
        if (id.equals(id.toUpperCase())){
            return false; // 들어온 id와 id를 모두 대문자로 바꿔서 비교했을 때 같으면 문제 없으므로 false
        } else {
            return true;  // 문제가 있으므로 true -> 텍스트 색이 레드로 보여줌
        }
    }

    //공백검사
    private boolean isStringEmpty (String text){
        if (text == null) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sensor_control_1); // 여기에서 레이아웃을 설정해야 함

        editTextName = findViewById(R.id.editTextName); // 이름 입력 EditText를 레이아웃의 ID와 연결
        editTextId = findViewById(R.id.editTextId); // 센서 ID 입력 EditText를 레이아웃의 ID와 연결
        btn_auth=(Button) findViewById(R.id.btn_find_id3);
        btn_fin=(Button) findViewById(R.id.btn_finish);

        // 이름 입력 리스너 설정
        editTextName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 텍스트가 변경되기 전에 호출되는 부분
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 텍스트가 변경될 때 호출되는 부분
            }

            @Override
            public void afterTextChanged(Editable s) {

                String inputText = s.toString();
                if (inputText.isEmpty()) {
                    // 입력이 비어 있는 경우
                    editTextName.setBackgroundResource(R.drawable.white_edittext);// 흰색 배경
                    empty=false;
                } else {
                    // 입력이 비어 있지 않은 경우
                    editTextName.setBackgroundResource(R.drawable.green_edittext); // 녹색 배경
                    empty=true;
                }

            }
        });

        // 센서 ID 입력 리스너 설정
        editTextId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 텍스트가 변경되기 전에 호출되는 부분
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 텍스트가 변경될 때 호출되는 부분
            }
            // 텍스트가 변경된 후 호출되는 부분
            @Override
            public void afterTextChanged(Editable s) {
                String inputText = s.toString();
                boolean first = true;

                //특수문자 검사
                String REGEXP_PATTERN_NUMBER = "[ !@#$%^&*(),.?\\\":{}|<>]";

                Pattern pattern = Pattern.compile(REGEXP_PATTERN_NUMBER);
                Matcher matcher = pattern.matcher(inputText);

                int specialCharacterCount = 0; // 특수문자 개수를 저장하는 변수
                while (matcher.find()) {
                    specialCharacterCount++;
                }

                //특수문자1개 이상+공백
                if (specialCharacterCount >= 1 || inputText.isEmpty()) {
                    editTextId.setBackgroundResource(R.drawable.red_edittext);
                    special_empty = false;
                } else {
                    editTextId.setBackgroundResource(R.drawable.white_edittext);
                    special_empty = true;
                }

                // 대소문자 구분
                if (isIdCaseSensitive(inputText)) {editTextId.setBackgroundResource(R.drawable.red_edittext);
                    caseSensitive = false;
                }
                if (!isIdCaseSensitive(inputText) && specialCharacterCount == 0) {
                    editTextId.setBackgroundResource(R.drawable.white_edittext);
                    caseSensitive = true;
                }

            }

        });

        //인증하기
        // 인증하기 버튼 클릭 리스너
        btn_auth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 데이터 유효성 검사 수행 (특수문자, 대소문자 등의 검사)

                if (special_empty && caseSensitive) {
                    // 데이터 유효성 검사에 이상이 없다면 인증을 처리하는 로직을 여기에 추가

                    // 인증이 완료되면 아래와 같이 테두리를 초록색으로 변경
                    editTextId.setBackgroundResource(R.drawable.green_edittext);

                    // 인증이 완료되면 Toast 메시지 표시
                    Toast.makeText(getApplicationContext(), "인증이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "필수 항목을 확인해 주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //완료
        btn_fin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (special_empty && caseSensitive && empty) {
                    Toast.makeText(getApplicationContext(), "완료되었습니다.", Toast.LENGTH_SHORT).show();
                    // Intent를 사용하여 sensor_control_list.xml로 화면 전환
                    Intent intent = new Intent(SensorRegisterActivity.this, SensorRegisterList.class); // SensorControlListActivity는 실제로 사용하려는 대상 화면 클래스입니다.
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "필수 항목을 확인해 주세요.", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
}

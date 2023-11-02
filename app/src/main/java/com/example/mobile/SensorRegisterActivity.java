package com.example.mobile;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SensorRegisterActivity extends AppCompatActivity {
    public static boolean check_rgId, check_rgname;
    private EditText editTextName;
    private EditText editTextId;

    public static boolean check_sensorId, check_sensorName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sensor_control_1); // 여기에서 레이아웃을 설정해야 함

        editTextName = findViewById(R.id.editTextName); // 이름 입력 EditText를 레이아웃의 ID와 연결
        editTextId = findViewById(R.id.editTextId); // 센서 ID 입력 EditText를 레이아웃의 ID와 연결

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
                // 텍스트가 변경된 후 호출되는 부분
                String inputText = s.toString();
                if (inputText.isEmpty()) {
                    // 입력이 비어 있는 경우
                    editTextName.setBackgroundResource(R.drawable.red_edittext); // 붉은색 배경
                } else {
                    // 입력이 비어 있지 않은 경우
                    editTextName.setBackgroundResource(R.drawable.white_edittext); // 흰색 배경
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

            @Override
            public void afterTextChanged(Editable s) {
                // 텍스트가 변경된 후 호출되는 부분
                String inputText = s.toString();
                boolean first = true;

                //특수문자 검사
                String REGEXP_PATTERN_NUMBER = "[ !@#$%^&*(),.?\\\":{}|<>]";

                editTextId.setBackgroundResource(R.drawable.white_edittext);

                if (!inputText.isEmpty()) {
                    // 입력이 비어 있는 경우
                    // 흰색 배경
                    editTextId.setBackgroundResource(R.drawable.white_edittext);
                    first = false;
                }

                Pattern pattern = Pattern.compile(REGEXP_PATTERN_NUMBER);
                Matcher matcher = pattern.matcher(inputText);

                while (matcher.find()) {
                    editTextId.setBackgroundResource(R.drawable.red_edittext);
                }
            }
        });

                // 중복 검사
                if (isIdDuplicate(inputText)) {
                    editTextId.setBackgroundResource(R.drawable.red_edittext);
                    return;
                }

                // 공백 검사
                if (inputText.isEmpty()) {
                    editTextId.setBackgroundResource(R.drawable.red_edittext);
                    return;
                }

                // 대소문자 구분
                if (isIdCaseSensitive(inputText)) {
                    editTextId.setBackgroundResource(R.drawable.red_edittext);
                } else {
                    editTextId.setBackgroundResource(R.drawable.white_edittext);
                }

                /*if (!first) {
                    editTextId.setBackgroundResource(R.drawable.red_edittext); // 붉은색 배경
                }*/

                private boolean isIdDuplicate (String id){

                }




    }
}

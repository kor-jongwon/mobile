package com.example.mobile;

import android.content.ContentValues;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class SensorRegisterActivity extends AppCompatActivity {
    public   EditText editTextName,editTextId;
    public TextView  tv_error_id, tv_error_name;
    public Button btn_duplicated, btn_register;

    public static String id;

    public static boolean idDuplicated;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_register); // 여기에서 레이아웃을 설정해야 함

        editTextName = findViewById(R.id.sensor_register_editText_name); // 이름 입력 EditText를 레이아웃의 ID와 연결
        editTextId = findViewById(R.id.sensor_register_editText_id); // 센서 ID 입력 EditText를 레이아웃의 ID와 연결

        tv_error_id = findViewById(R.id.sensor_register_tv_error_Id);
        tv_error_name = findViewById(R.id.sensor_register_tv_error_name);

        btn_register = findViewById(R.id.btn_register);
        btn_duplicated = findViewById(R.id.sensor_register_buttonCheckDuplicate);

        // 이름 입력 리스너 설정

        btn_duplicated.setEnabled(false);
        btn_register.setEnabled(false);
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
                    tv_error_name.setVisibility(View.VISIBLE);
                    tv_error_name.setTextColor(Color.parseColor("#FF0000"));
                    tv_error_name.setText("센서 이름을 입력해주세요.");
                    editTextName.setBackgroundResource(R.drawable.red_edittext); // 붉은색 배경
                } else {
                    tv_error_name.setVisibility(View.GONE);
                    editTextName.setBackgroundResource(R.drawable.green_edittext); // 흰색 배경
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
                btn_duplicated.setEnabled(false);
                // 텍스트가 변경된 후 호출되는 부분
                String inputText = s.toString();
                if (inputText.isEmpty()) {
                    btn_duplicated.setEnabled(false);
                    tv_error_id.setVisibility(View.VISIBLE);
                    tv_error_id.setTextColor(Color.parseColor("#FF0000"));
                    tv_error_id.setText("센서 아이디를 입력해주세요.");
                    editTextId.setBackgroundResource(R.drawable.red_edittext); // 붉은색 배경
                } else {
                    btn_duplicated.setEnabled(true);

                    btn_duplicated.setEnabled(true);
                    tv_error_id.setVisibility(View.GONE);
                    editTextId.setBackgroundResource(R.drawable.green_edittext);
                }
            }
        });
        btn_duplicated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = editTextId.getText().toString();

                ContentValues params = new ContentValues();
                params.put("id", id);

                String url = api_url.SENSORDUPLICATE.getValue();
                duplicated_NetworkTask networkTask = new duplicated_NetworkTask(url, null);
                networkTask.execute();
            }
        });
    }
    public class duplicated_NetworkTask extends AsyncTask<Void, Void, String> {

        private final String url;
        private final ContentValues values;

        public duplicated_NetworkTask(String url, ContentValues values) {
            this.url = url;
            this.values = values;
        }

        @Override
        protected String doInBackground(Void... params) {
            String result;
            Sensor_Duplicates_RequestHttpURLConnection sensorRequestHttpURLConnection = new Sensor_Duplicates_RequestHttpURLConnection();
            result = sensorRequestHttpURLConnection.request(url, values);
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

                    if (message.equals("아이디가 제공되지 않았습니다.")) {
                        tv_error_id.setVisibility(View.VISIBLE);
                        tv_error_id.setText("네트워크를 다시 확인해주세요.");
                        editTextId.setBackgroundResource(R.drawable.red_edittext);

                    }
                    if (message.equals("없는 아이디 입니다.")) {
                        tv_error_id.setVisibility(View.VISIBLE);
                        tv_error_id.setText("센서 아이디를 다시 입력해주세요.");
                        editTextId.setBackgroundResource(R.drawable.red_edittext);

                    }
                    if (message.equals("등록되어 있는 아이디 입니다.")) {
                        tv_error_id.setVisibility(View.VISIBLE);
                        idDuplicated=true;
                        tv_error_id.setTextColor(getResources().getColor(android.R.color.holo_blue_dark));
                        tv_error_id.setText("사용가능한 아이디 입니다.");

                        btn_duplicated.setEnabled(false);
                        editTextId.setBackgroundResource(R.drawable.green_edittext);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    // JSON 파싱 오류 처리
                }
            } else {
                tv_error_id.setVisibility(View.VISIBLE);
                tv_error_id.setText("네트워크를 다시 확인해주세요.");
                editTextId.setBackgroundResource(R.drawable.red_edittext);
            }
        }
    }
}

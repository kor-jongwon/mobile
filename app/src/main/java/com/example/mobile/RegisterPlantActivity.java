package com.example.mobile;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;

public class RegisterPlantActivity extends Activity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private EditText editTextPlantName;
    private TextView textViewDate;
    private Button btnSave;
    private ImageView imageView;
    private boolean isImageSelected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_plant);

        imageView = findViewById(R.id.imageView);
        editTextPlantName = findViewById(R.id.plant_name);
        textViewDate = findViewById(R.id.plant_date_text_view);
        Button btnSelectDate = findViewById(R.id.date_picker_btn);
        btnSave = findViewById(R.id.button);

        btnSave.setEnabled(false); //버튼 비활성화 상태

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        editTextPlantName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateInputs(); // 텍스트가 변경될 때 확인
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });

        btnSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        // "완료" 버튼 클릭 리스너를 설정합니다.
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePlantData(); // 사용자 데이터 저장 메소드를 호출합니다.
            }
        });
    }

    private void showDatePickerDialog() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String selectedDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                        textViewDate.setText(selectedDate);
                        validateInputs(); // 날짜가 설정된 후 검증
                    }
                }, year, month, day);
        datePickerDialog.show();
    }

    public void openGallery() {
        // Toast 메시지 추가
        Toast.makeText(this, "이미지를 선택하세요.", Toast.LENGTH_SHORT).show();

        // 갤러리를 여는 인텐트 생성
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        // 결과를 반환받기 위해 액티비티 시작
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                imageView.setImageBitmap(bitmap);
                isImageSelected = true;
                validateInputs();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Failed to load image", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void validateInputs() {
        // 모든 입력 데이터가 유효한지 확인합니다.
        // 그렇지 않으면 비활성화합니다.
        btnSave.setEnabled(isImageSelected && !TextUtils.isEmpty(editTextPlantName.getText().toString().trim()) && !TextUtils.isEmpty(textViewDate.getText()));
        // 모든 조건이 충족되면 버튼을 활성화합니다.
    }

    private void savePlantData() {
        ContentValues values = new ContentValues();
        String plantName = editTextPlantName.getText().toString();
        String plantDate = textViewDate.getText().toString();
        //todo 사용자 id 저장위치 확인후 변경
        values.put("userId", "사용자 id");
        values.put("plantName", plantName);
        values.put("plantingDate", plantDate);


        String url = api_url.REGISTPLANT.getValue();
        NetworkTask networkTask = new NetworkTask(url , values);
        networkTask.execute();

        Toast.makeText(this, "식물 이름: " + plantName + "\n심은 날짜: " + plantDate, Toast.LENGTH_SHORT).show();
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
            PlantRegisterRequestHttpURLConnection requestHttpURLConnection = new PlantRegisterRequestHttpURLConnection();
            try {
                return requestHttpURLConnection.request(url, values); // 해당 URL로 부터 결과물을 얻어온다.
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            // 결과 처리
            if (s != null) {
                try {
                    // JSON 문자열을 파싱하여 JSON 객체로 변환
                    JSONObject jsonObject = new JSONObject(s);

                    // JSON 객체에서 "message" 키의 값을 가져오기
                    String message = jsonObject.optString("message", "");

                    // "message" 값에 "성공하였습니다" 문자열이 포함되어 있는지 확인
                    if (message.contains("식물정보가 성공적으로 추가되었습니다.")) {
                        Intent intent = new Intent(RegisterPlantActivity.this, Plant_RegisterList.class);
                        startActivity(intent); // 인텐트 실행
                    }
                    else{
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
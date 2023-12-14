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

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterPlantActivity extends Activity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private EditText editTextPlantName;
    private TextView textViewDate;
    private Button btnSave, duplicateButton, btnSelectDate;
    private ImageView imageView;
    private boolean isImageSelected = false;
    private boolean isDuplicateName = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_plant);

        imageView = findViewById(R.id.imageView);
        editTextPlantName = findViewById(R.id.plant_name);
        textViewDate = findViewById(R.id.plant_date_text_view);
        btnSelectDate = findViewById(R.id.date_picker_btn);
        duplicateButton = findViewById(R.id.duplicateButton);
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

        duplicateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String plantName = editTextPlantName.getText().toString();
                checkPlantNameDuplicate(plantName);
            }
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
        //todo is ImageSelected 개선
        btnSave.setEnabled(isDuplicateName &&!TextUtils.isEmpty(editTextPlantName.getText().toString().trim()) && !TextUtils.isEmpty(textViewDate.getText()));
        // 모든 조건이 충족되면 버튼을 활성화합니다.
    }

    private void savePlantData() {
        String plantName = editTextPlantName.getText().toString();
        String plantDate = textViewDate.getText().toString();

        // Map으로 데이터를 저장합니다.
        Map<String, String> values = new HashMap<>();
        values.put("plantName", plantName);
        values.put("plantingDate", plantDate);

        // 이미지 데이터 준비
        imageView.setDrawingCacheEnabled(true);
        Bitmap bitmap = imageView.getDrawingCache();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        byte[] imageData = bos.toByteArray();
        imageView.setDrawingCacheEnabled(false);

        String url = api_url.REGISTPLANT.getValue(); // 서버 URL을 설정해야 합니다.
        NetworkTask networkTask = new NetworkTask(url, values, imageData, "plant_image.jpg");
        networkTask.execute();
    }


    public class NetworkTask extends AsyncTask<Void, Void, String> {

        private String url;
        private Map<String, String> values;
        private byte[] imageData;
        private String imageName;

        // 생성자에서 이미지 데이터와 파일 이름을 추가로 받습니다.
        public NetworkTask(String url, Map<String, String> values, byte[] imageData, String imageName) {
            this.url = url;
            this.values = values;
            this.imageData = imageData;
            this.imageName = imageName;
        }

        @Override
        protected String doInBackground(Void... params) {
            PlantRegisterRequestHttpURLConnection requestHttpURLConnection = new PlantRegisterRequestHttpURLConnection();
            try {
                // 수정된 request 메서드를 호출합니다. 이 메서드는 이미지와 텍스트 데이터를 모두 서버로 전송합니다.
                return requestHttpURLConnection.request(url, values, imageData, imageName);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            // 결과 처리
            if (s != null) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    String message = jsonObject.optString("message", "");

                    if (message.contains("식물 정보가 성공적으로 추가되었습니다.")) {
                        // 다음 액티비티로 이동
                        Intent intent = new Intent(RegisterPlantActivity.this, Plant_RegisterList.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else {
                        // 오류 메시지 처리
                        Toast.makeText(getApplicationContext(), "등록 실패: " + message, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "JSON 파싱 오류", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "서버 요청 실패", Toast.LENGTH_SHORT).show();
            }
        }
    }
    //식물 중복 체크
    private void checkPlantNameDuplicate(final String plantName) {
        OkHttpClient client = new OkHttpClient();

        // 식물 이름 중복 검사를 위한 서버 URL
        String url = api_url.PLANTDUPLICATE.getValue();

        // POST 요청에 필요한 FormBody를 생성
        RequestBody formBody = new FormBody.Builder()
                .add("plantName", plantName)
                .build();

        // 요청을 생성
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        // 요청을 비동기적으로 실행
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // 네트워크 오류 처리
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }

                final String result = response.body().string();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonResponse = new JSONObject(result);
                            String message = jsonResponse.optString("message", "");

                            // API 응답에 따라 isDuplicateName 상태 설정
                            if (message.contains("사용 가능한 식물 이름입니다.")) {
                                isDuplicateName = true;
                            } else if (message.contains("동일한 이름의 식물이 이미 등록되어 있습니다.")) {
                                isDuplicateName = false;
                                Toast.makeText(RegisterPlantActivity.this, "중복된 이름입니다.", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            // JSON 파싱 오류 처리
                        }
                    }
                });
            }
        });
    }


}
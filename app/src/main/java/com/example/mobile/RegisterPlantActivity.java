package com.example.mobile;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
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

public class RegisterPlantActivity extends Activity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private EditText editTextPlantName;
    private TextView textViewDate;
    private Button btnSave;
    private ImageView imageView;
    private boolean isImageSelected = false;
    private boolean isNameAvailable = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_plant);

        imageView = findViewById(R.id.imageView);
        editTextPlantName = findViewById(R.id.plant_name);
        textViewDate = findViewById(R.id.plant_date_text_view);
        Button btnSelectDate = findViewById(R.id.date_picker_btn);
        Button duplicateButton = findViewById(R.id.duplicateButton);
        btnSave = findViewById(R.id.button);

        btnSave.setEnabled(false); // 초기 버튼 비활성화 상태

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
                validateInputs();
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

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePlantData();
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
                        validateInputs();
                    }
                }, year, month, day);
        datePickerDialog.show();
    }

    public void openGallery() {
        Toast.makeText(this, "이미지를 선택하세요.", Toast.LENGTH_SHORT).show();
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
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
                Toast.makeText(this, "이미지 로드 실패", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void validateInputs() {
        btnSave.setEnabled(isImageSelected && isNameAvailable && !TextUtils.isEmpty(editTextPlantName.getText().toString().trim()) && !TextUtils.isEmpty(textViewDate.getText()));
    }

    private void savePlantData() {
        String plantName = editTextPlantName.getText().toString();
        String plantDate = textViewDate.getText().toString();

        // imageView에서 Bitmap 가져오기
        Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();

        // Bitmap을 byte 배열로 변환
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        byte[] imageData = bos.toByteArray();

        String url = api_url.REGISTPLANT.getValue();

        Map<String, String> data = new HashMap<>();
        data.put("plantName", plantName);
        data.put("plantingDate", plantDate);

        NetworkTask networkTask = new NetworkTask(url, data, imageData, "plant_image.jpg");
        networkTask.execute();
    }


    public class NetworkTask extends AsyncTask<Void, Void, String> {

        private String url;
        private Map<String, String> params;
        private byte[] imageData;
        private String imageName;

        public NetworkTask(String url, Map<String, String> params, byte[] imageData, String imageName) {
            this.url = url;
            this.params = params;
            this.imageData = imageData;
            this.imageName = imageName;
        }

        @Override
        protected String doInBackground(Void... voids) {
            PlantRegisterRequestHttpURLConnection requestHttpURLConnection = new PlantRegisterRequestHttpURLConnection();
            try {
                return requestHttpURLConnection.request(url, params, imageData, imageName);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result != null && !result.isEmpty()) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String message = jsonObject.optString("message", "");

                    if (message.contains("식물 정보가 성공적으로 추가되었습니다.")) {
                        Intent intent = new Intent(RegisterPlantActivity.this, Plant_RegisterList.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else {
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
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                PlantDuplicateHttpURLConnection requestHttpURLConnection = new PlantDuplicateHttpURLConnection();
                try {
                    // 이곳에 필요한 URL, values, imageData, imageName 값을 설정해야 합니다.
                    // 예를 들어, 식물 이름 중복 검사에 필요한 정보를 설정합니다.
                    String url = api_url.PLANTDUPLICATE.getValue(); // 실제 서버 URL로 교체
                    Map<String, String> values = new HashMap<>();
                    values.put("plantName", plantName); // plantName은 식물 이름 변수

                    // 이미지 데이터가 필요하지 않은 경우, null 또는 빈 데이터를 전달할 수 있습니다.
                    byte[] imageData = null; // 혹은 필요한 이미지 데이터
                    String imageName = ""; // 혹은 필요한 이미지 이름

                    return requestHttpURLConnection.checkDuplicate(url, values);
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String result) {
                if (result != null) {
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
                } else {
                    // 응답이 null인 경우, 서버 연결 실패 처리
                }
            }
        }.execute();
    }


}
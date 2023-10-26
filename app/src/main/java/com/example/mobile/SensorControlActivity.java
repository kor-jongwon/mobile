package com.example.mobile;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
public class SensorControlActivity extends AppCompatActivity{

    // 센서 ID를 입력받는 EditText를 찾아옴
    //EditText editTextID = findViewById(R.id.editTextID);

    @Override
    public boolean onSupportNavigateUp() {
        // 뒤로 가기 버튼을 클릭했을 때의 동작을 정의합니다.
        Intent intent = new Intent(SensorControlActivity.this,  HomeActivity.class);
        startActivity(intent);
        return true;
    }

}

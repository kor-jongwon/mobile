package com.example.mobile;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        // 스플래시 화면을 일정 시간 동안 표시한 후 메인 액티비티로 전환합니다.
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish(); // 스플래시 액티비티를 종료합니다.
            }
        }, 2000); // 2초 동안 스플래시 화면을 표시한 후 전환합니다. (3000 밀리초 = 3초)
    }
}

package com.example.mobile;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpCongratulationsActivity extends AppCompatActivity {

    private TextView textViewCountdown;
    private Button buttonReturnToLogin;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_congratulations);

        textViewCountdown = findViewById(R.id.textViewCountdown);
        buttonReturnToLogin = findViewById(R.id.buttonReturnToLogin);

        // 로그인 화면으로 자동 이동하는 타이머 설정
        countDownTimer = new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // 매 초마다 실행되며, 남은 시간에 따라 숫자를 업데이트
                int seconds = (int) (millisUntilFinished / 1000);
                textViewCountdown.setText(String.valueOf(seconds));
            }

            @Override
            public void onFinish() {
                // 타이머가 완료되면 로그인 화면으로 이동
                Intent intent = new Intent(SignUpCongratulationsActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }.start();

        // "로그인 화면으로 돌아가기" 버튼 클릭 이벤트
        buttonReturnToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 버튼 클릭 시 로그인 화면으로 이동하도록 타이머 취소
                countDownTimer.cancel();
                Intent intent = new Intent(SignUpCongratulationsActivity.this, LoginActivity.class);
                startActivity(intent);
                finish(); // 현재 화면 종료
            }
        });
    }
}

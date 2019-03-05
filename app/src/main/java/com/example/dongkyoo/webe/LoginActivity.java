package com.example.dongkyoo.webe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.dongkyoo.webe.main.MainActivity;
import com.google.android.material.textfield.TextInputEditText;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText idEditText, pwEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        idEditText = findViewById(R.id.activity_login_id_editText);
        pwEditText = findViewById(R.id.activity_login_pw_editText);
    }

    // 로그인 버튼 클릭
    public void onClickLogin(View view) {
        String id = idEditText.getText().toString();
        String pw = pwEditText.getText().toString();

        // 아이디 입력 안하면 에러 메세지 노출
        if (id.equals("")) {
            idEditText.setError(getResources().getString(R.string.require_id));
            return;
        }

        // 비밀번호 입력 안하면 에러 메세지 노출
        if (pw.equals("")) {
            pwEditText.setError(getResources().getString(R.string.require_pw));
            return;
        }


        // TODO: 로그인 처리

        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}

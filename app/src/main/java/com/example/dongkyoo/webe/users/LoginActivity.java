package com.example.dongkyoo.webe.users;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.dongkyoo.webe.R;
import com.example.dongkyoo.webe.main.MainActivity;
import com.example.dongkyoo.webe.network.Network;
import com.example.dongkyoo.webe.vos.User;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private TextView signUpButton;
    private TextInputEditText idEditText, pwEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        idEditText = findViewById(R.id.activity_login_id_editText);
        pwEditText = findViewById(R.id.activity_login_pw_editText);
        signUpButton = findViewById(R.id.activity_login_sign_up);

        setEvent();
    }

    private void setEvent() {
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    // 로그인 버튼 클릭
    public void onClickLogin(View view) throws Exception {
        String id = idEditText.getText().toString();
        String password = pwEditText.getText().toString();

        // 아이디 입력 안하면 에러 메세지 노출
        if (id.equals("")) {
            idEditText.setError(getResources().getString(R.string.require_id));
            return;
        }

        // 비밀번호 입력 안하면 에러 메세지 노출
        if (password.equals("")) {
            pwEditText.setError(getResources().getString(R.string.require_pw));
            return;
        }

        Call<User> res = Network.getInstance().retrofitService.login(id, password);
        res.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 200) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("user", new Gson().toJson(response.body()));
                    LoginActivity.this.startActivity(intent);
                    LoginActivity.this.finish();
                } else {
                    // TODO: Spinner 처리
                    // TODO: 로그인 에러 메세지 띄워주기
                    Log.e(LoginActivity.this.getClass().getName(), "로그인 실패");
                    Log.e("로그인 실패", response.message());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // TODO: Spinner 처리
                // TODO: 로그인 에러 메세지 띄워주기
                Log.e(LoginActivity.this.getClass().getName(), "서버 연결 실패");
                t.printStackTrace(System.err);
            }
        });
    }
}

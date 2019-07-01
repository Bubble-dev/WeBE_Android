package com.example.dongkyoo.webe.users;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.dongkyoo.webe.R;
import com.example.dongkyoo.webe.network.Network;
import com.example.dongkyoo.webe.vos.User;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextInputEditText idEditText, nameEditText, passwordEditText, passwordConfirmEditText, emailEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        setView();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void setView() {
        toolbar = findViewById(R.id.activity_sign_up_toolbar);
        idEditText = findViewById(R.id.activity_sign_up_id_editText);
        nameEditText = findViewById(R.id.activity_sign_up_name_editText);
        passwordEditText = findViewById(R.id.activity_sign_up_password_editText);
        passwordConfirmEditText = findViewById(R.id.activity_sign_up_password_confirm_editText);
        emailEditText = findViewById(R.id.activity_sign_up_email_editText);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sign_up, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_sign_up_save) {
            String id = idEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            String passwordConfirm = passwordConfirmEditText.getText().toString();
            String email = emailEditText.getText().toString();
            String name = nameEditText.getText().toString();

            if (!checkValidInput(id, password, passwordConfirm, email, name))
                return false;

            Call<User> call = Network.getInstance().retrofitService.signUp(new User(id, password, email, name));
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.code() == 200) {
                        Log.i(SignUpActivity.this.getClass().getName(), response.body().getId() + " 회원가입 성공");
                        SignUpActivity.this.finish();
                    } else {
                        Log.e(SignUpActivity.this.getClass().getName(), "회원가입 실패\n" + response.message());
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Log.e(SignUpActivity.this.getClass().getName(), "서버 연결 실패", t);
                }
            });
        } else if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    private boolean checkValidInput(String id, String password, String passwordConfirm, String email, String name) {
        boolean isValid = true;
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";

        if (TextUtils.isEmpty(id)) {
            isValid = false;
            idEditText.setError(getResources().getString(R.string.require_id));
        }

        if (TextUtils.isEmpty(password)) {
            isValid = false;
            passwordEditText.setError(getResources().getString(R.string.require_pw));
        }

        if (TextUtils.isEmpty(passwordConfirm)) {
            isValid = false;
            passwordConfirmEditText.setError(getResources().getString(R.string.require_pw));
        }

        if (TextUtils.isEmpty(email)) {
            isValid = false;
            emailEditText.setError(getResources().getString(R.string.require_email));
        } else if (!email.matches(regex)) {
            isValid = false;
            emailEditText.setError(getResources().getString(R.string.require_valid_email));
        }

        if (TextUtils.isEmpty(name)) {
            isValid = false;
            nameEditText.setError(getResources().getString(R.string.require_name));
        }

        if (!TextUtils.isEmpty(password) && !TextUtils.isEmpty(passwordConfirm) && !password.equals(passwordConfirm)) {
            isValid = false;
            passwordConfirmEditText.setError(getResources().getString(R.string.require_same_password_and_confirm));
        }
        return isValid;
    }
}

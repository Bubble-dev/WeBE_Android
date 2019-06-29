package com.example.dongkyoo.webe;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;

import androidx.test.core.app.ApplicationProvider;

import com.example.dongkyoo.webe.main.MainActivity;
import com.google.android.material.textfield.TextInputEditText;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import static org.mockito.Mockito.*;

import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
public class LoginActivityTest {

    private LoginActivity activity;

    @Before
    public void setup() {
        activity = Robolectric.buildActivity(LoginActivity.class)
                .create()
                .resume()
                .get();
    }

    @Test
    public void clickLoginWithoutId() {
        EditText idEditText = activity.findViewById(R.id.activity_login_id_editText);
        Button loginBtn = activity.findViewById(R.id.activity_login_login_button);

        idEditText.setText("");

        loginBtn.performClick();

        Assert.assertEquals(activity.getResources().getString(R.string.require_id), idEditText.getError());
    }

    @Test
    public void clickLoginWithoutPassword() {
        EditText idEditText = activity.findViewById(R.id.activity_login_id_editText);
        EditText pwEditText = activity.findViewById(R.id.activity_login_pw_editText);
        Button loginBtn = activity.findViewById(R.id.activity_login_login_button);

        idEditText.setText("testId");
        pwEditText.setText("");

        loginBtn.performClick();

        Assert.assertEquals(activity.getResources().getString(R.string.require_pw), pwEditText.getError());
    }

    @Test
    public void clickLoginSuccessfully() {
        EditText idEditText = activity.findViewById(R.id.activity_login_id_editText);
        EditText pwEditText = activity.findViewById(R.id.activity_login_pw_editText);
        Button loginBtn = activity.findViewById(R.id.activity_login_login_button);

        idEditText.setText("testId");
        pwEditText.setText("testPassword123!@#");

        loginBtn.performClick();

        Intent expectedIntent = new Intent(activity, MainActivity.class);
        Intent actualIntent = Shadows.shadowOf((Application) ApplicationProvider.getApplicationContext()).getNextStartedActivity();
        Assert.assertEquals(expectedIntent.getComponent(), actualIntent.getComponent());
    }
}

package com.example.dongkyoo.webe;

import android.app.Application;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;

import androidx.test.core.app.ApplicationProvider;

import com.example.dongkyoo.webe.main.MainActivity;
import com.example.dongkyoo.webe.network.Network;
import com.example.dongkyoo.webe.users.LoginActivity;
import com.example.dongkyoo.webe.vos.User;

import org.awaitility.Awaitility;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.shadows.ShadowLooper;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

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
    public void loginSuccessfully() throws Exception {
        EditText idEditText = activity.findViewById(R.id.activity_login_id_editText);
        final EditText pwEditText = activity.findViewById(R.id.activity_login_pw_editText);
        Button loginBtn = activity.findViewById(R.id.activity_login_login_button);

        final String id = "testId";
        final String password = "testPassword123!@#";

        idEditText.setText(id);
        pwEditText.setText(password);

        MockWebServer server = new MockWebServer();
        server.enqueue(new MockResponse().setResponseCode(200).setBody(new User(id, password).toString()));
        server.start(Network.PORT);
        server.url("/");

        loginBtn.performClick();
        Awaitility.await().atMost(5, TimeUnit.SECONDS).until(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                ShadowLooper.runUiThreadTasks();
                Intent actualIntent = Shadows.shadowOf((Application) ApplicationProvider.getApplicationContext()).getNextStartedActivity();

                if (actualIntent != null) {
                    Intent expectedIntent = new Intent(activity, MainActivity.class);
                    Assert.assertEquals(expectedIntent.getComponent(), actualIntent.getComponent());
                    return true;
                }

                return false;
            }
        });
    }
}

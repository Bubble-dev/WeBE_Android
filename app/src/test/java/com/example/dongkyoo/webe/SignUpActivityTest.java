package com.example.dongkyoo.webe;

import android.app.Application;
import android.content.Intent;
import android.view.MenuItem;

import androidx.test.core.app.ApplicationProvider;

import com.example.dongkyoo.webe.network.Network;
import com.example.dongkyoo.webe.users.SignUpActivity;
import com.example.dongkyoo.webe.vos.User;
import com.google.android.material.textfield.TextInputEditText;

import org.awaitility.Awaitility;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.shadow.api.Shadow;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowLooper;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

@RunWith(RobolectricTestRunner.class)
public class SignUpActivityTest {

    private SignUpActivity activity;

    private TextInputEditText idEditText, nameEditText, passwordEditText, passwordConfirmEditText, emailEditText;

    @Before
    public void setup() {
        activity = Robolectric.buildActivity(SignUpActivity.class)
                .create()
                .resume()
                .get();

        idEditText = activity.findViewById(R.id.activity_sign_up_id_editText);
        nameEditText = activity.findViewById(R.id.activity_sign_up_name_editText);
        passwordEditText = activity.findViewById(R.id.activity_sign_up_password_editText);
        passwordConfirmEditText = activity.findViewById(R.id.activity_sign_up_password_confirm_editText);
        emailEditText = activity.findViewById(R.id.activity_sign_up_email_editText);
    }

    /**
     * 필수 입력란에 입력 안하고 저장 버튼 누른 경우
     * 결과 : 입력안한 EditText 에 에러 메세지 띄워줌
     */
    @Test
    public void clickSaveWithoutRequiredInput() {
        idEditText.setText("");
        nameEditText.setText("");
        passwordEditText.setText("");
        passwordConfirmEditText.setText("");
        emailEditText.setText("");

        ShadowActivity shadowActivity = Shadows.shadowOf(activity);
        shadowActivity.clickMenuItem(R.id.menu_sign_up_save);

        String idErrorMessage = idEditText.getError().toString();
        String nameErrorMessage = nameEditText.getError().toString();
        String passwordErrorMessage = passwordEditText.getError().toString();
        String passwordConfirmErrorMessage = passwordConfirmEditText.getError().toString();
        String emailErrorMessage = emailEditText.getError().toString();

        Assert.assertEquals(idErrorMessage, activity.getResources().getString(R.string.require_id));
        Assert.assertEquals(nameErrorMessage, activity.getResources().getString(R.string.require_name));
        Assert.assertEquals(passwordErrorMessage, activity.getResources().getString(R.string.require_pw));
        Assert.assertEquals(passwordConfirmErrorMessage, activity.getResources().getString(R.string.require_pw));
        Assert.assertEquals(emailErrorMessage, activity.getResources().getString(R.string.require_email));
    }

    /**
     * password와 confirm이 다른 비밀번호를 가지고 있을 때 발생하는 에러 체크
     * 결과 : confirm에 에러 메세지 노출
     */
    @Test
    public void clickSaveDifferentPasswordWithConfirm() {
        idEditText.setText("testId");
        nameEditText.setText("testName");
        passwordEditText.setText("testPassword1");
        passwordConfirmEditText.setText("testPassword2");
        emailEditText.setText("myEmail@naver.com");

        ShadowActivity shadowActivity = Shadows.shadowOf(activity);
        shadowActivity.clickMenuItem(R.id.menu_sign_up_save);

        String passwordConfirmErrorMessage = passwordConfirmEditText.getError().toString();

        Assert.assertEquals(passwordConfirmErrorMessage, activity.getResources().getString(R.string.require_same_password_and_confirm));
    }

    /**
     * 이메일 형식에 맞지 않는 경우
     * 결과: 이메일에 에러 메세지 노출
     */
    @Test
    public void clickSaveInvalidEmail() {
        idEditText.setText("testId");
        nameEditText.setText("testName");
        passwordEditText.setText("testPassword1");
        passwordConfirmEditText.setText("testPassword1");
        emailEditText.setText("myEmailnaver.com");

        ShadowActivity shadowActivity = Shadows.shadowOf(activity);
        shadowActivity.clickMenuItem(R.id.menu_sign_up_save);

        String emailErrorMessage = emailEditText.getError().toString();

        Assert.assertEquals(emailErrorMessage, activity.getResources().getString(R.string.require_valid_email));
    }

    /**
     * 회원가입 성공적으로 이루어짐
     * 결과: SignUpActivity 가 finish 되며 LoginActivity 가 화면에 다시 띄워짐
     * @throws Exception
     */
    @Test
    public void clickSaveSuccessfully() throws Exception {
        final String id = "testId";
        final String name = "testName";
        final String password = "testPassword1";
        final String email = "myEmail@naver.com";

        idEditText.setText(id);
        nameEditText.setText(name);
        passwordEditText.setText(password);
        passwordConfirmEditText.setText(password);
        emailEditText.setText(email);

        MockWebServer server = new MockWebServer();
        server.enqueue(new MockResponse().setResponseCode(200).setBody(new User(id, password, email, name).toString()));

        server.start(Network.PORT);
        server.url("/");

        ShadowActivity shadowActivity = Shadows.shadowOf(activity);
        shadowActivity.clickMenuItem(R.id.menu_sign_up_save);

        Awaitility.await().atMost(3, TimeUnit.SECONDS).until(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                ShadowLooper.runUiThreadTasks();
                if (activity.isFinishing()) {
                    Assert.assertTrue(true);
                    return true;
                }
                return false;
            }
        });

        server.shutdown();
    }
}

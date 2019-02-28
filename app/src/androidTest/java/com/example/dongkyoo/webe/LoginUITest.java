package com.example.dongkyoo.webe;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginUITest {

    private String id;
    private String pw;

    @Rule
    public ActivityTestRule activityRule = new ActivityTestRule(LoginActivity.class);


    // 아이디 미입력
    @Test
    public void emptyId() {
        onView(withId(R.id.activity_login_id_editText))
                .perform(ViewActions.clearText());

        onView(withId(R.id.activity_login_login_button))
                .perform(click());

        onView(withId(R.id.activity_login_id_editText))
                .check(matches(ViewMatchers.hasErrorText(TestUtil.getStringResource(R.string.require_id))));
    }

    // 비밀번호 미입력
    @Test
    public void emptyPw() {
        onView(withId(R.id.activity_login_pw_editText))
                .perform(ViewActions.clearText());

        onView(withId(R.id.activity_login_login_button))
                .perform(click());

        onView(withId(R.id.activity_login_pw_editText))
                .check(matches(ViewMatchers.hasErrorText(TestUtil.getStringResource(R.string.require_pw))));
    }

    // 비밀번호 8글자 미만 입력
//    @Test
//    public void tooShortPw() {
//        Random random = new Random();
//        random.setSeed(System.currentTimeMillis());
//
//        // 1이상 7이하의 랜덤 숫자r 생성
//        int r = 0;
//        while (r == 0) r = random.nextInt(7);
//
//        // r 만큼의 길이를 가진 String을 패스워드란에 입력
//        onView(withId(R.id.activity_login_pw_editText))
//                .perform(ViewActions.typeText(TestUtil.getRandomString(r)));
//
//        onView(withId(R.id.activity_login_login_button))
//                .perform(click());
//
//        onView(withId(R.id.activity_login_pw_editText))
//                .check(matches())
//    }
}

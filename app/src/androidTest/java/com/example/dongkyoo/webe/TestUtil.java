package com.example.dongkyoo.webe;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.platform.app.InstrumentationRegistry;

import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static androidx.test.espresso.matcher.RootMatchers.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static org.hamcrest.Matchers.*;

public class TestUtil {

    public static String getStringResource(int resId) {
        return InstrumentationRegistry.getInstrumentation().getTargetContext().getResources()
                .getString(resId);
    }

    public static String getRandomString(int length) {
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());

        List<String> charList = new ArrayList<>();
        for (int i = 'a'; i <= 'z'; i++) {
            charList.add(String.valueOf((char) i));
        }
        for (int i = 'A'; i <= 'Z'; i++) {
            charList.add(String.valueOf((char) i));
        }
        for (int i = '0'; i <= '9'; i++) {
            charList.add(String.valueOf((char) i));
        }

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int r = random.nextInt(charList.size());
            sb.append(charList.get(r));
        }

        return sb.toString();
    }

    public static ViewInteraction isDisplayingToast(Activity activity, String toastMsg) {
        return onView(withText(toastMsg)).inRoot(withDecorView(not(activity.getWindow().getDecorView()))).check(matches(isDisplayed()));
    }
}

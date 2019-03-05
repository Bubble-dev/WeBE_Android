package com.example.dongkyoo.webe;

import android.view.View;
import android.widget.ImageView;

import org.hamcrest.Matcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.platform.app.InstrumentationRegistry;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.actionWithAssertions;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

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

    public static ViewInteraction checkIsDisplayingSnackbar(String content) {
        return onView(withId(com.google.android.material.R.id.snackbar_text))
                .check(matches(withText(content)));
    }

    public static ViewAction setImage(int imageResId) {
        return actionWithAssertions(new ImageViewAction(imageResId));
    }

    public static class ImageViewAction implements ViewAction {

        private int imageId;

        public ImageViewAction(int resId) {
            this.imageId = resId;
        }

        @Override
        public Matcher<View> getConstraints() {
            return allOf(isDisplayed(), isAssignableFrom(ImageView.class));
        }

        @Override
        public String getDescription() {
            return "이미지 뷰에 이미지를 지정하는 액션";
        }

        @Override
        public void perform(UiController uiController, View view) {
            if (view instanceof ImageView) {
                ImageView imageView = (ImageView) view;
                imageView.setImageResource(imageId);
            }
        }
    }
}

package com.example.dongkyoo.webe;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
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
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;

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

    public static ViewAction setImageAction(int id) {
        return new SetImageAction(id);
    }

    public static ViewAction setImageAction(Bitmap bitmap) {
        return new SetImageAction(bitmap);
    }

    public static class SetImageAction implements ViewAction {
        private Bitmap image;

        public SetImageAction(int id) {
            this.image = BitmapFactory.decodeResource(InstrumentationRegistry.getInstrumentation().getTargetContext().getResources(), id);
        }

        public SetImageAction(Bitmap bitmap) {
            this.image = bitmap;
        }

        @Override
        public Matcher<View> getConstraints() {
            return allOf(isDisplayed(),
                    isAssignableFrom(ImageView.class),
                    isAssignableFrom(ImageButton.class)
            );
        }

        @Override
        public String getDescription() {
            return "ImageView나 ImageButton에 이미지를 적용";
        }

        @Override
        public void perform(UiController uiController, View view) {
            Log.i("hi", String.valueOf(view instanceof ImageView));
            Log.i("hi2", String.valueOf(view instanceof Button));

            if (view instanceof ImageView) {
                ImageView imageView = (ImageView) view;
                imageView.setImageBitmap(image);
            }
        }
    }
}

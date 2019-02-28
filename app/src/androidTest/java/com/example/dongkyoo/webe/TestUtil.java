package com.example.dongkyoo.webe;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import androidx.test.platform.app.InstrumentationRegistry;

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
}

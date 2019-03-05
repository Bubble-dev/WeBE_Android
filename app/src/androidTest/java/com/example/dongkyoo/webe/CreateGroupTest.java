package com.example.dongkyoo.webe;

import com.example.dongkyoo.webe.createGroup.CreateGroupViewModel;
import com.example.dongkyoo.webe.main.MainActivity;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class CreateGroupTest {

    private String name;
    private CreateGroupViewModel viewModel;

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule(MainActivity.class);

    @Before
    public void setupFragment() throws Throwable {
        activityTestRule.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activityTestRule.getActivity().onClickAddGroupFab();
            }
        });
        viewModel = new CreateGroupViewModel();
    }

    // 이름 미입력
    @Test
    public void emptyName() {
        onView(withId(R.id.fragment_create_group_name))
                .perform(clearText());

        onView(withId(R.id.menu_create_group_save))
                .perform(click());

        onView(withId(R.id.fragment_create_group_name))
                .check(matches(
                        hasErrorText(TestUtil.getStringResource(R.string.require_group_name)))
                );
    }

    // 중복된 이름
    @Test
    public void duplicatedName() {
        onView(withId(R.id.fragment_create_group_name))
                .perform(typeText(
                        "duplicate"
                ));

        TestUtil.checkIsDisplayingSnackbar(TestUtil.getStringResource(R.string.duplicated_group_name));
    }

    // 그룹명만 입력하고 저장
    @Test
    public void saveWithNameOnly() {
        onView(withId(R.id.fragment_create_group_name))
                .perform(replaceText(TestUtil.getRandomString(10)));

        onView(withId(R.id.menu_create_group_save))
                .perform(click());

        TestUtil.checkIsDisplayingSnackbar(TestUtil.getStringResource(R.string.create_group_successfully));
    }

    // 그룹명, 그룹 설명 입력하고 저장
    @Test
    public void saveWithNameDescription() {
        onView(withId(R.id.fragment_create_group_name))
                .perform(replaceText(TestUtil.getRandomString(10)));

        onView(withId(R.id.fragment_create_group_description))
                .perform(replaceText(TestUtil.getRandomString(500)));

        onView(withId(R.id.menu_create_group_save))
                .perform(click());

        TestUtil.checkIsDisplayingSnackbar(TestUtil.getStringResource(R.string.create_group_successfully));
    }

    // 그룹명, 그룹 설명, 대표사진 입력하고 저장
    @Test
    public void saveWithNameDescriptionPhoto() {
        onView(withId(R.id.fragment_create_group_name))
                .perform(replaceText(TestUtil.getRandomString(10)));

        onView(withId(R.id.fragment_create_group_description))
                .perform(replaceText(TestUtil.getRandomString(500)));

        onView(withId(R.id.fragment_create_group_titleImage))
                .perform(TestUtil.setImage(R.drawable.ic_launcher_foreground));

        onView(withId(R.id.menu_create_group_save))
                .perform(click());

        TestUtil.checkIsDisplayingSnackbar(TestUtil.getStringResource(R.string.create_group_successfully));
    }

    // 뒤로가기 키
    @Test
    public void backKeyPressed() {
        onView(isRoot()).perform(ViewActions.pressBack());
        onView(withId(R.id.activity_main_fragmentContainer)).check(matches(Matchers.not(isDisplayed())));
    }
}

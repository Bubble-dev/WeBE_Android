package com.example.dongkyoo.webe;

import com.example.dongkyoo.webe.createGroup.CreateGroupViewModel;
import com.example.dongkyoo.webe.main.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class CreateGroupTest {

    private String name;
    private CreateGroupViewModel viewModel;

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule(MainActivity.class);

    @Before
    public void setupFragment() {
        activityTestRule.getActivity()
                .getSupportFragmentManager()
                .beginTransaction();

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
                .perform(replaceText(
                        viewModel.getDuplicateName()
                ));

        TestUtil.isDisplayingToast(activityTestRule.getActivity(), TestUtil.getStringResource(R.string.duplicated_group_name));
    }

    // 그룹명만 입력하고 저장
    @Test
    public void saveSuccessfully() {
        onView(withId(R.id.fragment_create_group_name))
                .perform(replaceText(TestUtil.getRandomString(10)));

        onView(withId(R.id.menu_create_group_save))
                .perform(click());
    }
}

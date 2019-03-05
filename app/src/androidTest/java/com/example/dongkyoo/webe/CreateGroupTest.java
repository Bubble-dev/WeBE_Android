package com.example.dongkyoo.webe;

import com.example.dongkyoo.webe.createGroup.CreateGroupFragment;
import com.example.dongkyoo.webe.createGroup.CreateGroupViewModel;
import com.example.dongkyoo.webe.main.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class CreateGroupTest {

    private String name;
    private CreateGroupViewModel viewModel;

    @Rule
    public ActivityTestRule activityTestRule = new ActivityTestRule(MainActivity.class);

    @Before
    public void setupFragment() {
        FragmentScenario.launchInContainer(CreateGroupFragment.class, null, R.style.Theme_AppCompat, null);
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getTargetContext());
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
                        viewModel.getDuplicateName()
                ));

        TestUtil.isDisplayingToast(activityTestRule.getActivity(), TestUtil.getStringResource(R.string.duplicated_group_name));
    }

    // 그룹명만 입력하고 저장
    @Test
    public void saveWithNameOnly() {
        onView(withId(R.id.fragment_create_group_name))
                .perform(replaceText(TestUtil.getRandomString(10)));

        onView(withId(R.id.menu_create_group_save))
                .perform(click());

        TestUtil.isDisplayingToast(activityTestRule.getActivity(), TestUtil.getStringResource(R.string.create_group_successfully));
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

        TestUtil.isDisplayingToast(activityTestRule.getActivity(), TestUtil.getStringResource(R.string.create_group_successfully));
    }

    // 그룹명, 그룹 설명, 대표사진 입력하고 저장
    @Test
    public void saveWithNameDescriptionPhoto() {
        onView(withId(R.id.fragment_create_group_name))
                .perform(replaceText(TestUtil.getRandomString(10)));

        onView(withId(R.id.fragment_create_group_description))
                .perform(replaceText(TestUtil.getRandomString(500)));

        onView(withId(R.id.fragment_create_group_titleImage))
                .perform(TestUtil.setImageAction(R.drawable.ic_launcher_foreground));

        onView(withId(R.id.menu_create_group_save))
                .perform(click());

        TestUtil.isDisplayingToast(activityTestRule.getActivity(), TestUtil.getStringResource(R.string.create_group_successfully));
    }
}

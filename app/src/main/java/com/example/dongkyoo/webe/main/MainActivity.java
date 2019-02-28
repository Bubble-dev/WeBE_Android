package com.example.dongkyoo.webe.main;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.example.dongkyoo.webe.R;
import com.example.dongkyoo.webe.createGroup.CreateGroupFragment;
import com.example.dongkyoo.webe.calendar.CalendarFragment;
import com.example.dongkyoo.webe.group.GroupFragment;
import com.example.dongkyoo.webe.setting.SettingFragment;
import com.example.dongkyoo.webe.vos.Group;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GroupFragment.OnGroupFragmentHandler, CreateGroupFragment.OnCreateGroupFragmentHandler {

    private enum ScreenState {
        MAIN, CREATE_GROUP
    }

    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private FrameLayout fragmentContainer;
    private ScreenState screenState = ScreenState.MAIN;
    private CalendarFragment calendarFragment;
    private GroupFragment groupFragment;
    private SettingFragment settingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.activity_main_toolbar);
        tabLayout = findViewById(R.id.activity_main_tabLayout);
        fragmentContainer = findViewById(R.id.activity_main_fragmentContainer);

        setSupportActionBar(toolbar);

        setViewPager();
        setTabLayout();
    }

    private void setTabLayout() {
        tabLayout.getTabAt(0).setIcon(R.drawable.round_calendar_today_white_24);
        tabLayout.getTabAt(1).setIcon(R.drawable.round_group_white_24);
        tabLayout.getTabAt(2).setIcon(R.drawable.round_settings_white_24);
    }

    private void setViewPager() {
        calendarFragment = CalendarFragment.newInstance();
        groupFragment = GroupFragment.newInstance();
        settingFragment = SettingFragment.newInstance();

        viewPager = findViewById(R.id.activity_main_viewPager);
        viewPager.setAdapter(new MainViewPagerAdapter(getSupportFragmentManager(), calendarFragment, groupFragment, settingFragment));

        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem search = menu.findItem(R.id.main_menu_search);
        SearchView searchView = (SearchView) search.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_menu_search:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClickAddGroupFab() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.activity_main_fragmentContainer, CreateGroupFragment.newInstance())
                .commitNow();

        fragmentContainer.setVisibility(View.VISIBLE);
        tabLayout.setVisibility(View.GONE);
        viewPager.setVisibility(View.GONE);
        screenState = ScreenState.CREATE_GROUP;
    }

    @Override
    public void onCreateNewGroup(Group group) {
        calendarFragment.addGroup(group);
        groupFragment.addGroup(group);
    }
}

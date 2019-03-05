package com.example.dongkyoo.webe.main;


import com.example.dongkyoo.webe.calendar.CalendarFragment;
import com.example.dongkyoo.webe.group.GroupFragment;
import com.example.dongkyoo.webe.setting.SettingFragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class MainViewPagerAdapter extends FragmentStatePagerAdapter {

    private CalendarFragment calendarFragment;
    private GroupFragment groupFragment;
    private SettingFragment settingFragment;

    public MainViewPagerAdapter(FragmentManager fm, CalendarFragment calendarFragment, GroupFragment groupFragment, SettingFragment settingFragment) {
        super(fm);

        this.calendarFragment = calendarFragment;
        this.groupFragment = groupFragment;
        this.settingFragment = settingFragment;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return calendarFragment;

            case 1:
                return groupFragment;

            case 2:
                return settingFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}

package com.example.dongkyoo.webe.calendar;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;

import com.example.dongkyoo.webe.vos.Group;
import com.example.dongkyoo.webe.vos.Schedule;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class CalendarViewModel extends ViewModel {

    private MutableLiveData<List<Group>> enrolledGroupList;

    public CalendarViewModel() {
        Group group = new Group("토트넘");

        Schedule s = new Schedule();
        s.setContent("토트넘 vs 맨유");
        s.setGroup(new Group("토트넘"));
        s.setDate(new Date(System.currentTimeMillis()));
        List<Schedule> sList = new ArrayList<>();
        sList.add(s);
        group.setScheduleList(sList);

        List<Group> gs = new ArrayList<>();
        gs.add(group);
        enrolledGroupList = new MutableLiveData<>();
        enrolledGroupList.setValue(gs);
    }

    public void addGroup(Group group) {
        List<Group> list = enrolledGroupList.getValue();
        list.add(group);
        enrolledGroupList.setValue(list);
    }

    public LiveData<List<Group>> getEnrolledGroupList(String userId) {
        return enrolledGroupList;
    }

    public List<Schedule> getRecentScheduleList() {
        List<Schedule> scheduleList = new ArrayList<>();

        for (Group g : enrolledGroupList.getValue()) {
            for (Schedule s : g.getScheduleList()) {
                if (scheduleList.size() == 0)
                    scheduleList.add(s);
                else {
                    scheduleList.add(binarySearch(scheduleList, s, 0, scheduleList.size() - 1), s);
                    if (scheduleList.size() == 5)
                        return scheduleList;
                }
            }
        }
        return scheduleList;
    }

    public int binarySearch(List<Schedule> list, Schedule s, int begin, int end) {
        int half = (int) ((begin + end) / 2 + 0.5);
        long halfTime = list.get(half).getDate().getTime();
        long targetTime = s.getDate().getTime();

        if (begin == end)
            return begin;

        if (targetTime == halfTime)
            return half;
        if (targetTime <= halfTime)
            return binarySearch(list, s, begin, half - 1);
        else if (targetTime > halfTime)
            return binarySearch(list, s, half + 1, end);
        return 0;
    }
}

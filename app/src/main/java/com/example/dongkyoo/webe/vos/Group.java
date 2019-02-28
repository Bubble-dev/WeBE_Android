package com.example.dongkyoo.webe.vos;

import java.util.ArrayList;
import java.util.List;

public class Group {

    private int id;
    private String titleImageUrl;
    private String name;
    private List<Schedule> scheduleList;
    private List<User> subscriberList;
    private List<User> managerList;

    public Group(Group group) {
        this.id = group.id;
        this.titleImageUrl = group.titleImageUrl;
        this.name = group.name;
        this.subscriberList = new ArrayList<>();
        if (group.getSubscriberList() != null)
            this.subscriberList.addAll(group.getSubscriberList());

        this.managerList = new ArrayList<>();
        if (group.getManagerList() != null)
            this.managerList.addAll(group.getManagerList());

        this.scheduleList = new ArrayList<>();
        if (group.getScheduleList() != null)
            this.scheduleList.addAll(group.getScheduleList());
    }

    public Group(String name) {
        this.name = name;
        this.subscriberList = new ArrayList<>();
        this.managerList = new ArrayList<>();
        this.scheduleList = new ArrayList<>();
    }

    public String getTitleImageUrl() {
        return titleImageUrl;
    }

    public void setTitleImageUrl(String titleImageUrl) {
        this.titleImageUrl = titleImageUrl;
    }

    public List<User> getManagerList() {
        List<User> list = new ArrayList<>();
        list.addAll(managerList);
        return list;
    }

    public void setManagerList(List<User> managerList) {
        this.managerList = managerList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean hasSubscriber(String userId) {
        for (User subscriber: subscriberList) {
            if (subscriber.getId().equals(userId))
                return true;
        }
        return false;
    }

    public List<User> getSubscriberList() {
        List<User> list = new ArrayList<>();
        list.addAll(subscriberList);
        return list;
    }

    public void setSubscriberList(List<User> subscriberList) {
        this.subscriberList = subscriberList;
    }

    public List<Schedule> getScheduleList() {
        List<Schedule> list = new ArrayList<>();
        list.addAll(scheduleList);
        return list;
    }

    public void setScheduleList(List<Schedule> scheduleList) {
        this.scheduleList = scheduleList;
    }
}

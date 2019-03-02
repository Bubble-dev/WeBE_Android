package com.example.dongkyoo.webe.createGroup;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.dongkyoo.webe.vos.Group;

public class CreateGroupViewModel extends ViewModel {

    private MutableLiveData<Group> group;

    public Group saveNewGroup() {
        return new Group("new Group!");
    }

    public LiveData<Group> getGroup() {
        return new MutableLiveData<>();
    }

    public boolean isDuplicatedName(String name) {
        // TODO: 서버 처리 필수!
        if (name.equals(getDuplicateName()))
            return true;
        return false;
    }

    public String getDuplicateName() {
        return "duplicate";
    }
}

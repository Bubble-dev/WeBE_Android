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
}

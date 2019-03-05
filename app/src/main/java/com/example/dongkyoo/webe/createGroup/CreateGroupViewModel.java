package com.example.dongkyoo.webe.createGroup;


import com.example.dongkyoo.webe.vos.Group;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CreateGroupViewModel extends ViewModel {

    private MutableLiveData<Group> group;

    public Group saveNewGroup() {
        return new Group("new Group!");
    }

    public LiveData<Group> getGroup() {
        return new MutableLiveData<>();
    }
}

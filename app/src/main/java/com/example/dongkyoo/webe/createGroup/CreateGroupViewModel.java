package com.example.dongkyoo.webe.createGroup;


import androidx.lifecycle.ViewModel;

import com.example.dongkyoo.webe.network.Network;
import com.example.dongkyoo.webe.vos.Group;

import retrofit2.Call;
import retrofit2.Callback;

public class CreateGroupViewModel extends ViewModel {

    public void createNewGroup(Callback<Group> callback) {
        Call<Group> res = Network.getInstance().retrofitService.requestCreateNewGroup("testName");
        res.enqueue(callback);
    }
}

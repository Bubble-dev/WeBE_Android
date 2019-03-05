package com.example.dongkyoo.webe.group;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dongkyoo.webe.R;
import com.example.dongkyoo.webe.vos.Group;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class GroupFragment extends Fragment {

    private List<Group> groupList;

    private RecyclerView groupRecyclerView;
    private GroupViewModel mViewModel;
    private FloatingActionButton addGroupFab;
    private OnGroupFragmentHandler groupFragmentHandler;
    private GroupAdapter adapter;

    public static GroupFragment newInstance() {
        return new GroupFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (getActivity() != null && getActivity() instanceof OnGroupFragmentHandler) {
            this.groupFragmentHandler = (OnGroupFragmentHandler) getActivity();
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        groupList = new ArrayList<>();
        groupList.add(new Group("hi"));

        View view = inflater.inflate(R.layout.fragment_group, container, false);
        groupRecyclerView = view.findViewById(R.id.fragment_group_recyclerView);
        groupRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new GroupAdapter(groupList);
        groupRecyclerView.setAdapter(adapter);

        addGroupFab = view.findViewById(R.id.fragment_group_addGroupFab);
        addGroupFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                groupFragmentHandler.onClickAddGroupFab();
            }
        });

        return view;
    }

    public void addGroup(Group group) {
        groupList.add(group);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(GroupViewModel.class);
    }


    public interface OnGroupFragmentHandler {
        void onClickAddGroupFab();
    }
}

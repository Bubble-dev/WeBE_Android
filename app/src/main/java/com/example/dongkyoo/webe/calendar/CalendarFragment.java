package com.example.dongkyoo.webe.calendar;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dongkyoo.webe.R;
import com.example.dongkyoo.webe.vos.Group;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CalendarFragment extends Fragment {

    private CalendarViewModel viewModel;
    private RecyclerView recentScheduleRecyclerView;
    private OnCalendarFragmentListener listener;

    public static CalendarFragment newInstance() {
        return new CalendarFragment();
    }

    public interface OnCalendarFragmentListener {
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (getActivity() != null && getActivity() instanceof OnCalendarFragmentListener) {
            this.listener = (OnCalendarFragmentListener) getActivity();
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        recentScheduleRecyclerView = view.findViewById(R.id.fragment_calendar_recentSchedule_recyclerView);
        recentScheduleRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(CalendarViewModel.class);
        recentScheduleRecyclerView.setAdapter(new RecentScheduleRecyclerViewAdapter(viewModel.getRecentScheduleList()));
    }

    public void addGroup(Group group) {
        viewModel.addGroup(group);
    }
}

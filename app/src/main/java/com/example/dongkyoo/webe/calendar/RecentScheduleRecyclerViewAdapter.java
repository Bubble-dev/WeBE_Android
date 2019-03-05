package com.example.dongkyoo.webe.calendar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dongkyoo.webe.R;
import com.example.dongkyoo.webe.vos.Schedule;

import java.text.SimpleDateFormat;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class RecentScheduleRecyclerViewAdapter extends RecyclerView.Adapter<RecentScheduleRecyclerViewAdapter.RecentScheduleViewHolder> {

    private List<Schedule> scheduleList;

    public RecentScheduleRecyclerViewAdapter(List<Schedule> scheduleList) {
        this.scheduleList = scheduleList;
    }

    public static class RecentScheduleViewHolder extends RecyclerView.ViewHolder {

        public CircleImageView imageView;
        public TextView groupTextView, contentTextView, dateTextView;

        public RecentScheduleViewHolder(@NonNull View itemView) {
            super(itemView);

            this.imageView = itemView.findViewById(R.id.recent_schedule_item_image);
            this.groupTextView = itemView.findViewById(R.id.recent_schedule_item_group);
            this.contentTextView = itemView.findViewById(R.id.recent_schedule_item_content);
            this.dateTextView = itemView.findViewById(R.id.recent_schedule_item_date);
        }
    }

    @Override
    public int getItemCount() {
        if (scheduleList == null)
            return 0;

        return scheduleList.size();
    }

    @NonNull
    @Override
    public RecentScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recent_schedule, parent, false);

        RecentScheduleViewHolder viewHolder = new RecentScheduleViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecentScheduleViewHolder viewHolder, int position) {
        Schedule schedule = scheduleList.get(position);
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");

        viewHolder.groupTextView.setText(schedule.getGroup().getName());
        viewHolder.contentTextView.setText(schedule.getContent());
        viewHolder.dateTextView.setText(format.format(schedule.getDate()));
    }
}

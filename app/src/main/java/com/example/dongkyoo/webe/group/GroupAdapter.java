package com.example.dongkyoo.webe.group;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dongkyoo.webe.R;
import com.example.dongkyoo.webe.vos.Group;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.ViewHolder> {

    private List<Group> groupList;

    public GroupAdapter(List<Group> groupList) {
        this.groupList = groupList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public CircleImageView titleImageView;
        public ImageView subscribeImageView;
        public TextView nameTextView, numOfSubscriberTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            titleImageView = itemView.findViewById(R.id.item_group_groupTitleImage);
            subscribeImageView = itemView.findViewById(R.id.item_group_subscribeIcon);
            nameTextView = itemView.findViewById(R.id.item_group_groupName);
            numOfSubscriberTextView = itemView.findViewById(R.id.item_group_numOfSubscriber);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_group, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Group group = groupList.get(position);

        viewHolder.nameTextView.setText(group.getName());
        viewHolder.subscribeImageView.setImageResource(group.hasSubscriber("myId") ? R.drawable.round_favorite_black_24 : R.drawable.round_favorite_border_black_24);
        viewHolder.numOfSubscriberTextView.setText(String.valueOf(group.getSubscriberList().size()));
    }

    @Override
    public int getItemCount() {
        if (groupList == null)
            return 0;

        return groupList.size();
    }
}

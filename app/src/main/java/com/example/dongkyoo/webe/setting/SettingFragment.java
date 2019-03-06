package com.example.dongkyoo.webe.setting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.dongkyoo.webe.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

public class SettingFragment extends Fragment {

    private SettingViewModel mViewModel;
    private LinearLayout noticeLayout, versionLayout, notificationLayout, inquireLayout;

    public static SettingFragment newInstance() {
        return new SettingFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        noticeLayout = view.findViewById(R.id.fragment_setting_notice);
        versionLayout = view.findViewById(R.id.fragment_setting_version);
        notificationLayout = view.findViewById(R.id.fragment_setting_notification);
        inquireLayout = view.findViewById(R.id.fragment_setting_inquire);

        noticeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 공지사항
            }
        });
        versionLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 최신 버전 체크
            }
        });
        notificationLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 알림 관련 설정
            }
        });
        inquireLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 문의
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SettingViewModel.class);
    }

}

package com.example.dongkyoo.webe.report;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.dongkyoo.webe.R;

public class ReportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, ReportFragment.newInstance())
                    .commitNow();
        }

        Toolbar toolbar = findViewById(R.id.activity_report_toolbar);
        setSupportActionBar(toolbar);
    }
}

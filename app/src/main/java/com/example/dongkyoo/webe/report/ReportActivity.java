package com.example.dongkyoo.webe.report;

import android.os.Bundle;

import com.example.dongkyoo.webe.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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

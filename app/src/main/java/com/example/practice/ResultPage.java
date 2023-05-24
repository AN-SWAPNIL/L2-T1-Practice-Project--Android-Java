package com.example.practice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class ResultPage extends AppCompatActivity {
    TabLayout tab;
    ViewPager view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        tab = findViewById(R.id.tab_layout);
        view = findViewById(R.id.view_pager);

        ViewPagerAdapt adapter = new ViewPagerAdapt(getSupportFragmentManager());
        view.setAdapter(adapter);
        tab.setupWithViewPager(view);
    }

}
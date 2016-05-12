package com.example.mm.telephonebook.activity;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import com.example.mm.telephonebook.adapter.MainAdapter;
import com.example.mm.telephonebook.R;

/**
 * Name:      MainActivity
 * Author:    Lambo
 * Function:  主界面
 */
public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);


        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        MainAdapter myAdapter = new MainAdapter(getSupportFragmentManager());
        viewPager.setAdapter(myAdapter);
        tabLayout.setTabTextColors(Color.rgb(97, 202, 223), Color.rgb(34, 110, 126));
        tabLayout.setupWithViewPager(viewPager);
    }
}

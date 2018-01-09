package com.example.android.newsfeed;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

/**
 * Main app Activity, which contains ViewPager populated by Fragments. Each of them contains list
 * of latest news on different subjects obtained from The Guardian API,
 * depending on tags used to fetch the news.
 */

public class NewsFeedActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsfeed);


        // VIEWPAGER
        // Find the view pager that will allow the user to swipe between fragments
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        // Create an adapter that knows which fragment should be shown on each page
        CategoryFragmentPagerAdapter adapter = new CategoryFragmentPagerAdapter(this, getSupportFragmentManager());
        // Set the adapter onto the view pager
        viewPager.setAdapter(adapter);

        // TABLAYOUT
        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        // Set off screen page limit
        viewPager.setOffscreenPageLimit(CategoryFragmentPagerAdapter.NUM_ITEMS - 1);

    }
}

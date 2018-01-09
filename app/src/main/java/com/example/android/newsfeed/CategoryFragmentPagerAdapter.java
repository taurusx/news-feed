package com.example.android.newsfeed;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * {@link CategoryFragmentPagerAdapter} is a {@link FragmentPagerAdapter} that can provide the layout for
 * each list item based on a data source which is a list of {@link NewsObject} objects.
 */
public class CategoryFragmentPagerAdapter extends FragmentPagerAdapter {

    /**
     * Context of the app
     */
    private Context mContext;

    /**
     * Number of views in viewPager
     */
    public static final int NUM_ITEMS = 4;

    /**
     * Create a new {@link CategoryFragmentPagerAdapter} object.
     *
     * @param context is the context of the app
     * @param fm      is the fragment manager that will keep each fragment's state in the adapter
     *                across swipes.
     */
    public CategoryFragmentPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    /**
     * Return the {@link Fragment} that should be displayed for the given page number.
     */
    @Override
    public Fragment getItem(int position) {
        // Set Fragments under each Tab
        if (position == 0) {
            return new TechnologyFragment();
        } else if (position == 1) {
            return new WorldFragment();
        } else if (position == 2) {
            return new ScienceFragment();
        } else {
            return new BusinessFragment();
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Set titles of Tabs
        if (position == 0) {
            return mContext.getString(R.string.category_technology);
        } else if (position == 1) {
            return mContext.getString(R.string.category_world);
        } else if (position == 2) {
            return mContext.getString(R.string.category_science);
        } else {
            return mContext.getString(R.string.category_business);
        }
    }

    /**
     * Return the total number of pages.
     */
    @Override
    public int getCount() {
        return NUM_ITEMS;
    }
}
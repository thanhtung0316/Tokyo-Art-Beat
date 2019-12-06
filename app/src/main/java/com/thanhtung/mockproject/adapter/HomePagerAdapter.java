package com.thanhtung.mockproject.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.thanhtung.mockproject.ui.home.news.NewsFragment;
import com.thanhtung.mockproject.ui.home.popular.PopularFragment;

public class HomePagerAdapter extends FragmentStatePagerAdapter {
    private String[] listTab = {"News", "Popular"};
    private NewsFragment newsFragment;
    private PopularFragment popularFragment;

    public HomePagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        newsFragment = new NewsFragment();
        popularFragment = new PopularFragment();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position==0){
            return newsFragment;
        } else if (position==1){
            return popularFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return listTab.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return listTab[position];
    }
}

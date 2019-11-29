package com.thanhtung.mockproject.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.thanhtung.mockproject.ui.browse.BrowseCurrentFragment;
import com.thanhtung.mockproject.ui.browse.BrowsePastFragment;

public class SearchPagerAdapter extends FragmentStatePagerAdapter {

    private String[] listTab;
    private Fragment  fmCurrent;
    private Fragment fmPast;

    public SearchPagerAdapter(@NonNull FragmentManager fm, int behavior, String[] listTab, Fragment fmCurrent, Fragment fmPast) {
        super(fm, behavior);
        this.listTab = listTab;
        this.fmCurrent = fmCurrent;
        this.fmPast = fmPast;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return fmCurrent;
        } else if (position == 1) {
            return fmPast;
        }
        return null;
    }

    @Override
    public int getCount() {
        if (listTab!=null){
            return listTab.length;
        } else return 0;

    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return listTab[position];
    }
}

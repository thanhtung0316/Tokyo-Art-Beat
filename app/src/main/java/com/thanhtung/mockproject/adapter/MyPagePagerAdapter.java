package com.thanhtung.mockproject.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.thanhtung.mockproject.ui.mypage.status.mygoing.MyGoingFragment;
import com.thanhtung.mockproject.ui.mypage.status.mywent.MyWentFragment;

public class MyPagePagerAdapter extends FragmentStatePagerAdapter {
    private String[] listTab = {"Going", "Went"};
    private MyGoingFragment goingFragment;
    private MyWentFragment myWentFragment;

    public MyPagePagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        goingFragment = new MyGoingFragment();
        myWentFragment = new MyWentFragment();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position==0){
            return goingFragment;
        } else if (position==1){
            return myWentFragment;
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

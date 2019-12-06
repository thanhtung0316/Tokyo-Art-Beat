package com.thanhtung.mockproject.ui.browse;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.thanhtung.mockproject.R;
import com.thanhtung.mockproject.adapter.SearchPagerAdapter;
import com.thanhtung.mockproject.databinding.FragmentViewPagerBinding;
import com.thanhtung.mockproject.ui.browse.search.current.BrowseCurrentFragment;
import com.thanhtung.mockproject.ui.browse.search.past.BrowsePastFragment;

public class BrowseViewPagerFragment extends Fragment{
    private BrowseCurrentFragment fmCurrent = new BrowseCurrentFragment();
    private BrowsePastFragment fmPast = new BrowsePastFragment();
    private FragmentViewPagerBinding binding;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_view_pager,container,false);
        binding.vpBrowseSearch.setAdapter(new SearchPagerAdapter(getChildFragmentManager(),0
                , new String[]{"Current & Upcoming", "Past"},fmCurrent,fmPast));
        binding.tabBrowseSearch.setupWithViewPager(binding.vpBrowseSearch);
        return binding.getRoot();
    }

    public BrowseCurrentFragment getFmCurrent() {
        return fmCurrent;
    }

    public void setFmCurrent(BrowseCurrentFragment fmCurrent) {
        this.fmCurrent = fmCurrent;
    }

    public BrowsePastFragment getFmPast() {
        return fmPast;
    }

    public void setFmPast(BrowsePastFragment fmPast) {
        this.fmPast = fmPast;
    }

}

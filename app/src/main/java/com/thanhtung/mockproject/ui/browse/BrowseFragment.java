package com.thanhtung.mockproject.ui.browse;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.thanhtung.mockproject.R;
import com.thanhtung.mockproject.databinding.FragmentBrowseBinding;

public class BrowseFragment extends Fragment {
    private FragmentBrowseBinding binding;
    private FragmentManager manager;
    private BrowseSearchFragment fmSearch = new BrowseSearchFragment();
    private BrowseCategoryFragment fmCategory = new BrowseCategoryFragment();
    private BrowseListEventByCategoryFragment fmDetail = new BrowseListEventByCategoryFragment();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_browse,container,false);
        initView();
        showFragment(fmCategory,R.anim.slide_in_right,R.anim.slide_out_left);
        return binding.getRoot();
    }

    private void initView() {
        manager = getChildFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.ly_browse_frame,fmCategory,null);
        transaction.add(R.id.ly_browse_frame,fmSearch,null);
        transaction.add(R.id.ly_browse_frame,fmDetail,null);
        transaction.commit();
    }

    public void showFragment(Fragment fmShow, int in, int out) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.setCustomAnimations(in,out);
        transaction.hide(fmSearch);
        transaction.hide(fmCategory);
        transaction.hide(fmDetail);
        transaction.show(fmShow);
        transaction.commit();
    }

    public BrowseSearchFragment getFmSearch() {
        return fmSearch;
    }

    public BrowseCategoryFragment getFmCategory() {
        return fmCategory;
    }

    public BrowseListEventByCategoryFragment getFmDetail() {
        return fmDetail;
    }
}

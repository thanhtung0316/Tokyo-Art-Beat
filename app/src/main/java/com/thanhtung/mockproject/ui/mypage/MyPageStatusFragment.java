package com.thanhtung.mockproject.ui.mypage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.thanhtung.mockproject.R;
import com.thanhtung.mockproject.adapter.MyPagePagerAdapter;
import com.thanhtung.mockproject.databinding.FragmentMypageStatusBinding;

public class MyPageStatusFragment extends Fragment {
    private FragmentMypageStatusBinding binding;
    private MyPagePagerAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_mypage_status,container,false);
        adapter =new MyPagePagerAdapter(getChildFragmentManager(),0);
        binding.tlTabLayout.setupWithViewPager(binding.vpViewPager);
        binding.vpViewPager.setAdapter(adapter);
        return binding.getRoot();

    }
}

package com.thanhtung.mockproject.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.thanhtung.mockproject.R;
import com.thanhtung.mockproject.databinding.FragmentHomeBinding;
import com.thanhtung.mockproject.adapter.HomePagerAdapter;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        binding.vpViewPager.setAdapter(new HomePagerAdapter(getChildFragmentManager(), 0));
        binding.tlTabLayout.setupWithViewPager(binding.vpViewPager);
        return binding.getRoot();
    }

}
package com.thanhtung.mockproject.ui.browse;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.thanhtung.mockproject.R;
import com.thanhtung.mockproject.databinding.FragmentNothingToShowBinding;

public class BrowseNothingFragment extends Fragment {
    private FragmentNothingToShowBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_nothing_to_show,container,false);
        return binding.getRoot();
    }
}

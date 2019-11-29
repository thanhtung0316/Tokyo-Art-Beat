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
import com.thanhtung.mockproject.adapter.EventAdapter;
import com.thanhtung.mockproject.dao.AppEventInSearchdatabase;
import com.thanhtung.mockproject.databinding.FragmentEventInCategoryBinding;
import com.thanhtung.mockproject.model.Event;

import java.util.ArrayList;
import java.util.List;

public class BrowseCurrentFragment extends Fragment {
    private EventAdapter adapter;
    private List<Event> datas;
    private FragmentEventInCategoryBinding binding;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_event_in_category, container, false);
        adapter = new EventAdapter(getContext());
        List<Event> currentEvent = new ArrayList<>();
        datas = AppEventInSearchdatabase.getInstance(getContext(), "search-event-database").getEventDao().getAll();
        for (Event event : datas) {
            if (!BrowseSearchFragment.checkDates(event.getScheduleEndDate())) {
                currentEvent.add(event);
            }
        }
        if (currentEvent.size() == 0) {
            binding.tvNoEvent.setText("Không có sự kiện nào sắp diễn ra");
            binding.tvNoEvent.setVisibility(View.VISIBLE);
        } else {
            adapter.setData(currentEvent);
            binding.lvEvent.setAdapter(adapter);
        }

        return binding.getRoot();
    }


}

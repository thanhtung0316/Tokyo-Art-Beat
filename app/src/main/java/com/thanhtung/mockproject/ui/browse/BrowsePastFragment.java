package com.thanhtung.mockproject.ui.browse;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.thanhtung.mockproject.EndlessRecyclerViewScrollListener;
import com.thanhtung.mockproject.R;
import com.thanhtung.mockproject.activity.EventDetailActivity;
import com.thanhtung.mockproject.adapter.EventAdapter;
import com.thanhtung.mockproject.api.ApiBuilder;
import com.thanhtung.mockproject.api.ApiSearchResult;
import com.thanhtung.mockproject.dao.AppEventdatabase;
import com.thanhtung.mockproject.databinding.FragmentEventInCategoryBinding;
import com.thanhtung.mockproject.model.Event;
import com.thanhtung.mockproject.model.EventInSearch;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BrowsePastFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, Callback<ApiSearchResult>, EventAdapter.ItemEventListener {
    private FragmentEventInCategoryBinding binding;
    private EventAdapter adapter;
    private List<Event> datas;
    private BrowseSearchFragment fmBrowse;
    private String query;
    private String TOKEN;
    private List<Event> pastEvent;
    private int pageIndex;
    private EndlessRecyclerViewScrollListener scrollListener;
    private LinearLayoutManager manager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_event_in_category,container,false);
        adapter = new EventAdapter(getContext());
        fmBrowse = new BrowseSearchFragment();
        manager = new LinearLayoutManager(getContext());
        scrollListener = new EndlessRecyclerViewScrollListener(manager) {
            @Override
            public void onLoadMore(final int page, int totalItemsCount, RecyclerView view) {
                ApiBuilder.getInstance().search(TOKEN, query, page, 10).enqueue(new Callback<ApiSearchResult>() {
                    @Override
                    public void onResponse(Call<ApiSearchResult> call, Response<ApiSearchResult> response) {
                        if (response.body()!=null){
                            List<EventInSearch> searchList = response.body().getResponse().getEvents();
                            for ( EventInSearch e: searchList) {
                                if (BrowseSearchFragment.checkDates(e.getScheduleEndDate())){
                                    pastEvent.add(e);
                                }
                            }
                            adapter.notifyItemRangeInserted(page*10,10);
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiSearchResult> call, Throwable t) {
                        Toast.makeText(getContext(), "System Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };
        binding.lvEvent.setLayoutManager(manager);
        binding.lvEvent.addOnScrollListener(scrollListener);
        binding.swipeToRefresh.setOnRefreshListener(this);
        adapter.setListener(this);
        datas = AppEventdatabase.getInstance(getContext(),"search-event-database").getEventDao().getAll();
        pastEvent = new ArrayList<>();

        for (Event event: datas) {
            if (BrowseSearchFragment.checkDates(event.getScheduleEndDate())){
                pastEvent.add(event);
            }
        }
        if (pastEvent.size()==0){
            binding.tvNoEvent.setVisibility(View.VISIBLE);
            binding.tvNoEvent.setText("Chưa có sự kiện nào đã kết thúc");
        } else {
            adapter.setData(pastEvent);
            binding.lvEvent.setAdapter(adapter);
        }
        return binding.getRoot();
    }

    @Override
    public void onRefresh() {
        pageIndex++;
        ApiBuilder.getInstance().search(TOKEN, query, pageIndex, 10).enqueue(this);
    }
    public void dataSetup(String query,String TOKEN){
        this.query= query;
        this.TOKEN=TOKEN;
    }

    @Override
    public void onResponse(Call<ApiSearchResult> call, Response<ApiSearchResult> response) {
        if (response.body()!=null){
            List<EventInSearch> searchList = response.body().getResponse().getEvents();
            pastEvent.clear();
            for ( EventInSearch event: searchList) {
                if (BrowseSearchFragment.checkDates(event.getScheduleEndDate())){
                    pastEvent.add(event);
                }
            }
            adapter.setData(pastEvent);
            binding.lvEvent.setAdapter(adapter);
        }
        binding.swipeToRefresh.setRefreshing(false);
    }

    @Override
    public void onFailure(Call<ApiSearchResult> call, Throwable t) {
        Log.e("LOG","Fail");
    }

    @Override
    public void OnItemClick(long eventID) {
        Intent intent = new Intent(getActivity(), EventDetailActivity.class);
        intent.putExtra("event_id",eventID);
        startActivity(intent);
    }
}

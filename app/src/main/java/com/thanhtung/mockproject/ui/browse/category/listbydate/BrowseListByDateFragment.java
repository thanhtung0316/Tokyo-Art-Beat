package com.thanhtung.mockproject.ui.browse.category.listbydate;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import com.thanhtung.mockproject.adapter.EventInCategoryAdapter;
import com.thanhtung.mockproject.api.ApiBuilder;
import com.thanhtung.mockproject.api.ApiResultInCategory;
import com.thanhtung.mockproject.dao.AppEventInCategory;
import com.thanhtung.mockproject.databinding.FragmentEventInCategoryBinding;
import com.thanhtung.mockproject.model.Category;
import com.thanhtung.mockproject.model.EventInCateGory;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BrowseListByDateFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, Callback<ApiResultInCategory>, EventInCategoryAdapter.ItemEventListener {
    private FragmentEventInCategoryBinding binding;
    private EventInCategoryAdapter adapter;
    private List<EventInCateGory> events;
    private Category category;
    private String TOKEN;
    private LinearLayoutManager manager;
    private EndlessRecyclerViewScrollListener scrollListener;

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setTOKEN(String TOKEN) {
        this.TOKEN = TOKEN;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_event_in_category, container, false);
        adapter = new EventInCategoryAdapter(getContext());
        binding.swipeToRefresh.setOnRefreshListener(this);
        manager = new LinearLayoutManager(getContext());
        binding.lvEvent.setHasFixedSize(true);
        adapter.setListener(this);
        scrollListener = new EndlessRecyclerViewScrollListener(manager) {
            @Override
            public void onLoadMore(final int page, int totalItemsCount, RecyclerView view) {
                ApiBuilder.getInstance().listEventsByCategory(TOKEN,category.getId(),page,10).enqueue(new Callback<ApiResultInCategory>() {
                    @Override
                    public void onResponse(Call<ApiResultInCategory> call, Response<ApiResultInCategory> response) {
                        if (response.body()!=null){
                            if (response.body().getResponse().getEvents().size()!=0){
                                events.addAll(response.body().getResponse().getEvents());
                                adapter.notifyItemRangeInserted(page*10,10);
                            } else {
                                Toast.makeText(getContext(), "Đã hết sự kiện!", Toast.LENGTH_SHORT).show();
                            }

                            Log.e("LOG","SIZE: "+response.body().getResponse().getEvents().size());
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiResultInCategory> call, Throwable t) {
                        Toast.makeText(getContext(), "ERROR", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };
        binding.lvEvent.setLayoutManager(manager);
        binding.lvEvent.addOnScrollListener(scrollListener);


        return binding.getRoot();

    }


    public void bindData(List<EventInCateGory> events) {
        this.events=events;
        scrollListener.resetState();
        binding.tvNoEvent.setVisibility(View.INVISIBLE);
        adapter.setData(events);
        binding.lvEvent.setAdapter(adapter);
        if (events.size() == 0) {
            binding.tvNoEvent.setText("Không có sự kiện nào!");
            binding.tvNoEvent.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onRefresh() {
        ApiBuilder.getInstance().listEventsByCategory(TOKEN, category.getId(), 1, 10)
                .enqueue(this);

    }

    @Override
    public void onResponse(Call<ApiResultInCategory> call, Response<ApiResultInCategory> response) {
        if (response.body() != null) {
            List<EventInCateGory> eventInCateGories = response.body().getResponse().getEvents();

            AppEventInCategory.getInstance(getContext(), "event-category-database")
                    .getEventDao().deleteAll();
            AppEventInCategory.getInstance(getContext(), "event-category-database")
                    .getEventDao().insert(eventInCateGories);
            List<EventInCateGory> eventFromDatabase = AppEventInCategory.getInstance(getContext(), "event-category-database")
                    .getEventDao().getAll();
            bindData(eventFromDatabase);
            binding.swipeToRefresh.setRefreshing(false);
        }
    }

    @Override
    public void onFailure(Call<ApiResultInCategory> call, Throwable t) {
        Toast.makeText(getContext(), "Error Occured", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnItemClick(EventInCateGory event) {
        Intent intent = new Intent(getActivity(), EventDetailActivity.class);
        intent.putExtra("event_id",event.getId());
        startActivity(intent);
    }
}

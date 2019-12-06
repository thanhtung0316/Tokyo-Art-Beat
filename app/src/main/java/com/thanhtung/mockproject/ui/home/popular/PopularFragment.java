package com.thanhtung.mockproject.ui.home.popular;

import android.content.Intent;
import android.os.Build;
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
import com.thanhtung.mockproject.MyShared;
import com.thanhtung.mockproject.R;
import com.thanhtung.mockproject.activity.EventDetailActivity;
import com.thanhtung.mockproject.adapter.EventAdapter;
import com.thanhtung.mockproject.api.ApiBuilder;
import com.thanhtung.mockproject.api.ApiResult;
import com.thanhtung.mockproject.api.ApiResultEventDetail;
import com.thanhtung.mockproject.dao.AppEventDetaildatabase;
import com.thanhtung.mockproject.dao.AppEventdatabase;
import com.thanhtung.mockproject.databinding.FragmentPopularBinding;
import com.thanhtung.mockproject.model.Event;
import com.thanhtung.mockproject.model.EventDetail;
import com.thanhtung.mockproject.model.News;
import com.thanhtung.mockproject.ui.mypage.login.LoginFragment;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PopularFragment extends Fragment implements Callback<ApiResult>, SwipeRefreshLayout.OnRefreshListener, EventAdapter.ItemEventListener {
    private FragmentPopularBinding binding;
    public static final String EVENT_DATABASE = "event_database";
    private EventAdapter adapter;
    private List<Event> data;
    private int pageIndex = 2;
    private String TOKEN;
    private MyShared myShared;
    private List<EventDetail> eventDetailList;
    public static final String EVENT_DETAIL_DATABASE = "event-detail";
    private EndlessRecyclerViewScrollListener scrollListener;
    private LinearLayoutManager manager;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_popular,container,false);
        binding.swipeToRefresh.setOnRefreshListener(this);
        adapter = new EventAdapter(getContext());
        myShared = new MyShared(getContext());
        TOKEN = myShared.get(LoginFragment.KEY_TOKEN);
        adapter.setListener(this);
        manager= new LinearLayoutManager(getContext());
        scrollListener = new EndlessRecyclerViewScrollListener(manager) {
            @Override
            public void onLoadMore(final int page, int totalItemsCount, RecyclerView view) {
                ApiBuilder.getInstance().getPopullar(0,10).enqueue(new Callback<ApiResult>() {
                    @Override
                    public void onResponse(Call<ApiResult> call, Response<ApiResult> response) {
                        if (response.body()!=null){
                            if (response.body().getResponse().getEvents().size()!=0){
                                List<Event> events = response.body().getResponse().getEvents();
                                data.addAll(events);
                                adapter.notifyItemRangeInserted(page*10,10);
                            } else {
                                Toast.makeText(getContext(), "Đã hết sự kiện!", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                    @Override
                    public void onFailure(Call<ApiResult> call, Throwable t) {
                        if (getContext()!=null){
                            Toast.makeText(getContext(), "Kết nối internet để load thêm sự kiện!", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        };
        binding.lvPopular.setLayoutManager(manager);
        binding.lvPopular.addOnScrollListener(scrollListener);

        if (AppEventdatabase.getInstance(getContext(), EVENT_DATABASE).getEventDao().getAll().isEmpty()){
            ApiBuilder.getInstance().getPopullar(0,10).enqueue(this);
        } else {
            data = AppEventdatabase.getInstance(getContext(), EVENT_DATABASE).getEventDao().getAll();
            adapter.setData(data);
            binding.lvPopular.setAdapter(adapter);

        }
        return binding.getRoot();
    }

    @Override
    public void onResponse(Call<ApiResult> call, Response<ApiResult> response) {

        if (response.body()!=null){
            data = response.body().getResponse().getEvents();
            AppEventdatabase.getInstance(getContext(),"event-database")
                    .getEventDao().insert(data);
            adapter.setData(data);
            binding.lvPopular.setAdapter(adapter);
        } else {
            Toast.makeText(getContext(), "Lỗi hệ thống", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onFailure(Call<ApiResult> call, Throwable t) {
        if (getContext()!=null){
            Toast.makeText(getContext(), "Kiểm tra kết nối và thử lại!", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onRefresh() {
        pageIndex++;
        ApiBuilder.getInstance().getPopullar(pageIndex,10).enqueue(new Callback<ApiResult>() {
            @Override
            public void onResponse(Call<ApiResult> call, Response<ApiResult> response) {
                if (response.body()!=null){
                    data = response.body().getResponse().getEvents();
                    AppEventdatabase.getInstance(getContext(), EVENT_DATABASE).getEventDao().deleteAll();
                    AppEventdatabase.getInstance(getContext(), EVENT_DATABASE).getEventDao().insert(data);
                }
                adapter.setData(data);
                binding.lvPopular.setAdapter(adapter);
                Toast.makeText(getContext(), "Đã cập nhật", Toast.LENGTH_SHORT).show();
                binding.swipeToRefresh.setRefreshing(false);
            }
            @Override
            public void onFailure(Call<ApiResult> call, Throwable t) {
                Toast.makeText(getContext(), "Kết nối internet để có tải sự kiện mới nhất!", Toast.LENGTH_SHORT).show();
                binding.swipeToRefresh.setRefreshing(false);
            }
        });
    }

    @Override
    public void OnItemClick(long eventID) {
        Intent intent = new Intent(getActivity(), EventDetailActivity.class);
        intent.putExtra("event_id",eventID);
        startActivity(intent);
    }
}

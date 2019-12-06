package com.thanhtung.mockproject.ui.home.news;

import android.content.Intent;
import android.os.Bundle;
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
import com.thanhtung.mockproject.activity.WebViewActivity;
import com.thanhtung.mockproject.dao.AppNewsdatabase;
import com.thanhtung.mockproject.databinding.FragmentNewsBinding;
import com.thanhtung.mockproject.api.ApiBuilder;
import com.thanhtung.mockproject.model.News;
import com.thanhtung.mockproject.adapter.NewsAdapter;
import com.thanhtung.mockproject.api.ApiResult;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsFragment extends Fragment implements Callback<ApiResult>, SwipeRefreshLayout.OnRefreshListener, NewsAdapter.ItemListener {
    private NewsAdapter adapter;
    private FragmentNewsBinding binding;
    private int pageIndex = 2;
    private List<News> data;
    private String databaseName = "news-database";
    private EndlessRecyclerViewScrollListener scrollListener;
    private LinearLayoutManager manager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_news,container,false);
        binding.lyPull.setOnRefreshListener(this);
        adapter = new NewsAdapter(getContext());
        manager = new LinearLayoutManager(getContext());
        adapter.setListener(this);
        binding.lvNews.setLayoutManager(manager);
        scrollListener = new EndlessRecyclerViewScrollListener(manager) {
            @Override
            public void onLoadMore(final int page, int totalItemsCount, RecyclerView view) {
                ApiBuilder.getInstance().getListNews(page,10).enqueue(new Callback<ApiResult>() {
                    @Override
                    public void onResponse(Call<ApiResult> call, Response<ApiResult> response) {
                        if (response.body()!=null){
                            List<News> news = response.body().getResponse().getNews();
                            data.addAll(news);
                            adapter.notifyItemRangeInserted(page*10,10);
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiResult> call, Throwable t) {
                        if (getContext()!=null){
                            Toast.makeText(getContext(), "Kết nối Internet để cập nhật những tin tức mới nhất!", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        };
        binding.lvNews.addOnScrollListener(scrollListener);



        if (AppNewsdatabase.getInstance(getContext(),databaseName).getNewsDao().getAll().isEmpty()){
            ApiBuilder.getInstance().getListNews(0,10).enqueue(this);
        } else {
            data = AppNewsdatabase.getInstance(getContext(),databaseName).getNewsDao().getAll();
            adapter.setData(data);
            binding.lvNews.setAdapter(adapter);
            binding.lvNews.setHasFixedSize(false);
        }
        return binding.getRoot();

    }

    @Override
    public void onResponse(Call<ApiResult> call, Response<ApiResult> response) {
        if (response.body()!=null){
            data = response.body().getResponse().getNews();
            AppNewsdatabase.getInstance(getContext(),databaseName).getNewsDao().insert(data);
            adapter.setData(data);
            binding.lvNews.setAdapter(adapter);
        }else{
            Toast.makeText(getContext(), "ERROR", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onFailure(Call<ApiResult> call, Throwable t) {
        if (getContext()!=null){
            Toast.makeText(getContext(), "Lỗi mạng!", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onRefresh() {
        pageIndex++;
        ApiBuilder.getInstance().getListNews(pageIndex,10).enqueue(new Callback<ApiResult>() {
            @Override
            public void onResponse(Call<ApiResult> call, Response<ApiResult> response) {
                if (response.body()!=null){
                    data = response.body().getResponse().getNews();
                    AppNewsdatabase.getInstance(getContext(),databaseName).getNewsDao().update(data);
                    adapter.setData(data);
                    binding.lvNews.setAdapter(adapter);
                    Toast.makeText(getContext(), "Đã làm mới", Toast.LENGTH_SHORT).show();
                    binding.lyPull.setRefreshing(false);
                }else {
                    Toast.makeText(getContext(), "ERROR", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ApiResult> call, Throwable t) {
                Toast.makeText(getContext(), "Kết nối Internet để cập nhật những tin tức mới nhất!", Toast.LENGTH_SHORT).show();
                binding.lyPull.setRefreshing(false);
            }
        });

    }

    @Override
    public void OnItemClick(News news) {
        Intent intent = new Intent(getActivity() ,WebViewActivity.class);
        intent.putExtra("url",news.getDetail_url());
        startActivity(intent);
    }
}

package com.thanhtung.mockproject.ui.browse;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import com.thanhtung.mockproject.MyShared;
import com.thanhtung.mockproject.R;
import com.thanhtung.mockproject.adapter.CategoryAdapter;
import com.thanhtung.mockproject.api.ApiBuilder;
import com.thanhtung.mockproject.api.ApiResult;
import com.thanhtung.mockproject.api.ApiResultInCategory;
import com.thanhtung.mockproject.dao.AppCategorydatabase;
import com.thanhtung.mockproject.dao.AppEventInCategory;
import com.thanhtung.mockproject.databinding.FragmentBrowseCategoryBinding;
import com.thanhtung.mockproject.model.Category;
import com.thanhtung.mockproject.model.EventInCateGory;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.thanhtung.mockproject.ui.mypage.login.LoginFragment.KEY_TOKEN;

public class BrowseCategoryFragment extends Fragment implements Callback<ApiResult>, CategoryAdapter.ItemListener, View.OnClickListener {
    private FragmentBrowseCategoryBinding binding;
    private CategoryAdapter adapter;
    private List<Category> categories;
    private BrowseFragment fmBrowse;
    private MyShared myShared;
    private String TOKEN;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_browse_category, container, false);
        initData();
        fmBrowse = (BrowseFragment) getParentFragment();
        adapter.setData(categories);
        binding.lvCategory.setAdapter(adapter);
        adapter.setListener(this);
        binding.imvSearch.setOnClickListener(this);
        myShared = new MyShared(getContext());
        TOKEN = "bearer " + myShared.get(KEY_TOKEN);
        return binding.getRoot();
    }


    private void initData() {
        categories = new ArrayList<>();
        adapter = new CategoryAdapter(getContext());
        if (AppCategorydatabase.getInstance(getContext(),"category-database")
                .getCategoryDao().getAll().isEmpty()){
            ApiBuilder.getInstance().getCategory().enqueue(this);
        } else {
            categories = AppCategorydatabase.getInstance(getContext(),"category-database")
                    .getCategoryDao().getAll();
        }

    }

    @Override
    public void onResponse(Call<ApiResult> call, Response<ApiResult> response) {
        if (response.body() != null) {
            categories = response.body().getResponse().getCategories();
            AppCategorydatabase.getInstance(getContext(),"category-database")
                    .getCategoryDao().insert(categories);
            adapter.setData(categories);
        }
    }

    @Override
    public void onFailure(Call<ApiResult> call, Throwable t) {
        Toast.makeText(getContext(), "Kiểm tra kết nối và thử lại!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnItemClicked(final Category category) {
        fmBrowse.getFmDetail().binData(category);

        ApiBuilder.getInstance().listEventsByCategory(TOKEN,category.getId(),0,10).enqueue(new Callback<ApiResultInCategory>() {
            @Override
            public void onResponse(Call<ApiResultInCategory> call, Response<ApiResultInCategory> response) {
                if (response.body()!=null){
                    List<EventInCateGory> events = response.body().getResponse().getEvents();
                    if (events.size()==0){
                        AppEventInCategory.getInstance(getContext(),"event-category-database")
                                .getEventDao().deleteAll();
                    } else {
                        AppEventInCategory.getInstance(getContext(),"event-category-database")
                                .getEventDao().deleteAll();
                        AppEventInCategory.getInstance(getContext(),"event-category-database")
                                .getEventDao().insert(events);
                    }
                    fmBrowse.getFmDetail().getFmbyPopular().bindData(AppEventInCategory.getInstance(getContext(),"event-category-database")
                            .getEventDao().getAll());
                    fmBrowse.getFmDetail().getFmbyPopular().setCategory(category);
                    fmBrowse.getFmDetail().getFmbyPopular().setTOKEN(TOKEN);

                    fmBrowse.getFmDetail().getFmbyDate().bindData(AppEventInCategory.getInstance(getContext(),"event-category-database")
                            .getEventDao().getAllByDate());
                    fmBrowse.getFmDetail().getFmbyDate().setCategory(category);
                    fmBrowse.getFmDetail().getFmbyDate().setTOKEN(TOKEN);
                }

            }

            @Override
            public void onFailure(Call<ApiResultInCategory> call, Throwable t) {
                Toast.makeText(getContext(), "Lỗi hệ thống, kiểm tra kết nối và thử lại", Toast.LENGTH_SHORT).show();
            }
        });
        fmBrowse.showFragment(fmBrowse.getFmDetail(),R.anim.slide_in_right,R.anim.slide_out_left);
    }

    @Override
    public void onClick(View v) {
        fmBrowse.showFragment(fmBrowse.getFmSearch(),R.anim.slide_in_right,R.anim.slide_out_left);
    }
}
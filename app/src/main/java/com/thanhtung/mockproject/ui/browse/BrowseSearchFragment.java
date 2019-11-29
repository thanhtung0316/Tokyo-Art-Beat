package com.thanhtung.mockproject.ui.browse;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.thanhtung.mockproject.MyShared;
import com.thanhtung.mockproject.R;
import com.thanhtung.mockproject.api.ApiBuilder;
import com.thanhtung.mockproject.api.ApiSearchResult;
import com.thanhtung.mockproject.dao.AppEventInSearchdatabase;
import com.thanhtung.mockproject.databinding.FragmentBrowseSearchBinding;
import com.thanhtung.mockproject.model.EventInSearch;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.thanhtung.mockproject.ui.mypage.login.LoginFragment.KEY_TOKEN;

public class BrowseSearchFragment extends Fragment implements View.OnClickListener, SearchView.OnQueryTextListener, Callback<ApiSearchResult> {
    private FragmentBrowseSearchBinding binding;
    private BrowseFragment fmBrowse;
    private MyShared myShared;
    private String TOKEN;
    private String query;
    private ProgressDialog dialog;
    private BrowseViewPagerFragment fmViewPager = new BrowseViewPagerFragment();
    private BrowseNothingFragment fmNothing = new BrowseNothingFragment();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_browse_search, container, false);
        fmBrowse = (BrowseFragment) getParentFragment();
        myShared = new MyShared(getContext());
        dialog = new ProgressDialog(getContext());
        TOKEN = "bearer " + myShared.get(KEY_TOKEN);
        binding.imvBack.setOnClickListener(this);
        binding.searchview.setOnQueryTextListener(this);
        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        fmBrowse.showFragment(fmBrowse.getFmCategory(), android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        this.query = query;
        AppEventInSearchdatabase.getInstance(getContext(),"search-event-database").getEventDao().deleteAll();
        ApiBuilder.getInstance().search(TOKEN, query, 0, 10).enqueue(this);
        dialog.setMessage("Loading, please wait...");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        fmViewPager.getFmPast().dataSetup(query,TOKEN);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void onResponse(Call<ApiSearchResult> call, Response<ApiSearchResult> response) {
        if (response.body() != null) {
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            if (response.body().getResponse().getEvents().size() != 0) {
                List<EventInSearch> datas = response.body().getResponse().getEvents();
                transaction.replace(R.id.ly_frame_viewpager, fmViewPager);
                transaction.addToBackStack(null);
                transaction.commitAllowingStateLoss();
                AppEventInSearchdatabase.getInstance(getContext(),"search-event-database")
                        .getEventDao().insert(datas);
                dialog.dismiss();
            } else {
                transaction.replace(R.id.ly_frame_viewpager, fmNothing);
                transaction.addToBackStack(null);
                transaction.commitAllowingStateLoss();
                dialog.dismiss();
            }

        }
    }

    @Override
    public void onFailure(Call<ApiSearchResult> call, Throwable t) {
        Toast.makeText(getContext(), "Kết nối internet để sử dụng tính năng này", Toast.LENGTH_SHORT).show();
        dialog.dismiss();
    }

    public BrowseViewPagerFragment getFmViewPager() {
        return fmViewPager;
    }

    public static boolean checkDates(String scheduleEndDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");
        Date strDate = null;
        try {
            strDate = sdf.parse(scheduleEndDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date().after(strDate);
    }

}

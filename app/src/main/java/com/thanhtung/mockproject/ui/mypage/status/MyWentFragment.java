package com.thanhtung.mockproject.ui.mypage.status;

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

import com.thanhtung.mockproject.MyShared;
import com.thanhtung.mockproject.R;
import com.thanhtung.mockproject.adapter.EventAdapter;
import com.thanhtung.mockproject.api.ApiBuilder;
import com.thanhtung.mockproject.api.ApiResult;
import com.thanhtung.mockproject.databinding.FragmentMywentBinding;
import com.thanhtung.mockproject.model.Event;
import com.thanhtung.mockproject.ui.mypage.login.LoginFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyWentFragment extends Fragment implements Callback<ApiResult> {
    private FragmentMywentBinding binding;
    private MyShared myShared;
    private String TOKEN;
    private EventAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_mywent, container, false);
        myShared = new MyShared(getContext());
        adapter = new EventAdapter(getContext());
        binding.swipeToRefresh.setEnabled(false);
        TOKEN = "bearer "+myShared.get(LoginFragment.KEY_TOKEN);
        ApiBuilder.getInstance().getGoingEvent(TOKEN,2).enqueue(this);
        return binding.getRoot();
    }

    @Override
    public void onResponse(Call<ApiResult> call, Response<ApiResult> response) {
        if (response.body()!=null){
            if (response.body().getStatus()==1){
                List<Event> events = response.body().getResponse().getEvents();
                if (events.size()==0){
                    binding.tvNoEvent.setVisibility(View.VISIBLE);
                } else {
                    adapter.setData(events);
                    binding.lvWent.setAdapter(adapter);
                }
            } else {
                Toast.makeText(getContext(), ""+response.body().getError_message(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onFailure(Call<ApiResult> call, Throwable t) {

    }
}

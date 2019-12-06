package com.thanhtung.mockproject.ui.mypage.status.mygoing;

import android.os.Bundle;
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
import com.thanhtung.mockproject.databinding.FragmentMygoingBinding;
import com.thanhtung.mockproject.model.Event;
import com.thanhtung.mockproject.ui.mypage.login.LoginFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.thanhtung.mockproject.ui.mypage.login.LoginFragment.KEY_TOKEN;

public class MyGoingFragment extends Fragment implements Callback<ApiResult> {
    private FragmentMygoingBinding binding;
    private MyShared myShared;
    private String TOKEN;
    private EventAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_mygoing,container,false);
        myShared = new MyShared(getContext());
        binding.swipeToRefresh.setEnabled(false);
        adapter = new EventAdapter(getContext());
        TOKEN ="bearer "+myShared.get(KEY_TOKEN);
        ApiBuilder.getInstance().getGoingEvent(TOKEN,1).enqueue(this);
        return binding.getRoot();
    }

    @Override
    public void onResponse(Call<ApiResult> call, Response<ApiResult> response) {
        if (response.body()!=null){
            if (response.body().getStatus()==1){
                List<Event> events =response.body().getResponse().getEvents();
                if (events.size()==0){
                    binding.tvNoEvent.setVisibility(View.VISIBLE);
                } else {

                    adapter.setData(events);
                    binding.lvGoing.setAdapter(adapter);
                }
            } else {
                Toast.makeText(getContext(), ""+response.body().getError_message(), Toast.LENGTH_SHORT).show();
                if (response.body().getError_code()==401||response.body()
                        .getError_message().equals("expired")){
                    myShared.remove(KEY_TOKEN);
                    Toast.makeText(getContext(), "Phiên đăng nhập đã hết hạn, Vui lòng đăng nhập lại!", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getContext(), ""+response.body().getError_message(), Toast.LENGTH_SHORT).show();
                }

            }
        }
    }
    @Override
    public void onFailure(Call<ApiResult> call, Throwable t) {
        if (getContext()!=null){
            Toast.makeText(getContext(), "Kết nối mạng để có thể sử dụng tính năng này", Toast.LENGTH_SHORT).show();
        }

    }
}

package com.thanhtung.mockproject.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.thanhtung.mockproject.MyShared;
import com.thanhtung.mockproject.R;
import com.thanhtung.mockproject.adapter.EventInDetailAdapter;
import com.thanhtung.mockproject.api.ApiBuilder;
import com.thanhtung.mockproject.api.ApiResult;
import com.thanhtung.mockproject.api.ApiResultEventDetail;
import com.thanhtung.mockproject.api.ApiResultNearlyEvent;
import com.thanhtung.mockproject.databinding.ActivityEventDetailBinding;
import com.thanhtung.mockproject.model.Event;
import com.thanhtung.mockproject.model.EventDetail;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.thanhtung.mockproject.ui.mypage.login.LoginFragment.KEY_TOKEN;

@RequiresApi(api = Build.VERSION_CODES.M)
public class EventDetailActivity extends AppCompatActivity implements Callback<ApiResultEventDetail>, View.OnClickListener {
    private ActivityEventDetailBinding binding;
    private long eventId;
    private Intent intent;
    private MyShared myShared;
    private String TOKEN;
    private EventDetail eventDetail;
    private EventInDetailAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        binding.btnFollow.setOnClickListener(this);
        binding.btnGoing.setOnClickListener(this);
        binding.imvClose.setOnClickListener(this);
        binding.btnWent.setOnClickListener(this);
    }

    private void init() {
        myShared = new MyShared(this);
        TOKEN = "bearer " + myShared.get(KEY_TOKEN);
        intent = getIntent();
        eventId = intent.getLongExtra("event_id", 0);
        overridePendingTransition(R.anim.slide_in_bottom, R.anim.fade_out);
        ApiBuilder.getInstance().getDetailEvent(TOKEN,eventId).enqueue(this);
        adapter = new EventInDetailAdapter(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_event_detail);
        if (!isLogin()){
            binding.btnGoing.setBackgroundColor(Color.WHITE);
            binding.btnWent.setBackgroundColor(Color.WHITE);
        }
    }

    public Boolean isLogin() {
        return !myShared.get(KEY_TOKEN).contentEquals("");
    }


    @Override
    public void onResponse(Call<ApiResultEventDetail> call, Response<ApiResultEventDetail> response) {
        if (response.body() != null) {
            if (response.body().getStatus() == 1) {
                eventDetail = response.body().getResponse().getEventDetail();
                binding.setEventDetail(eventDetail);
                binding.tvGoingCount.setVisibility(View.VISIBLE);
                if (eventDetail.getPhoto() != null) {
                    Glide.with(binding.imvEvent)
                            .load(eventDetail.getPhoto())
                            .into(binding.imvEvent);
                }
                Double geoLat = eventDetail.getVenue().getGeoLat();
                Double geoLong = eventDetail.getVenue().getGeoLong();

                ApiBuilder.getInstance().listNearlyEvents(TOKEN,250,geoLong,geoLat).enqueue(callbackNearlyEvent);
            } else {
                Toast.makeText(this, "Lỗi hệ thống!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Lỗi hệ thống!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure(Call<ApiResultEventDetail> call, Throwable t) {
        binding.btnWent.setEnabled(false);
        binding.btnFollow.setEnabled(false);
        binding.btnWent.setEnabled(false);
        binding.btnGoing.setEnabled(false);
        Toast.makeText(this, "Lỗi hệ thống! Vui lòng kiểm tra kết nối và thử lại", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imv_close:
                overridePendingTransition(R.anim.slide_out_bottom, R.anim.fade_out);
                finish();
                break;
            case R.id.btn_follow:
                Log.e("LOG","TOKEN: "+TOKEN);
                Log.e("LOG","VenueID: "+eventDetail.getVenue().getId_venue());
                if (isLogin()){
                    ApiBuilder.getInstance().doFollow(TOKEN,eventDetail.getVenue().getId_venue()).enqueue(new Callback<ApiResultEventDetail>() {
                        @Override
                        public void onResponse(Call<ApiResultEventDetail> call, Response<ApiResultEventDetail> response) {
                            if (response.body()!=null){
                                Log.e("LOG","Status:"+response.body().getStatus());
                                if (response.body().getStatus()!=0){
                                    binding.btnFollow.setText("Followed");
                                    Toast.makeText(EventDetailActivity.this, "Đã Follow", Toast.LENGTH_SHORT).show();
                                } else {
                                    if (response.body().getErrorCode()==401||response.body().getErrorMessage().contains("expired")){
                                        myShared.clear();
                                        Toast.makeText(EventDetailActivity.this, "Phiên đăng nhập hết hạn, mời bạn đăng nhập lại", Toast.LENGTH_SHORT).show();

                                    } else {
                                        Toast.makeText(EventDetailActivity.this, ""+response.body().getErrorMessage(), Toast.LENGTH_SHORT).show();
                                    }

                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ApiResultEventDetail> call, Throwable t) {
                            Toast.makeText(EventDetailActivity.this, "Lỗi hệ thống", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    showLoginFragment();
                    Toast.makeText(this, "Vui lòng đăng nhập", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.btn_going:
                if (!isLogin()){
                    showLoginFragment();
                    Toast.makeText(this, "Vui lòng đăng nhập", Toast.LENGTH_SHORT).show();
                } else {
                    ApiBuilder.getInstance().doUpdateEvent(TOKEN,1,eventId).enqueue(callbackUpdateEvent);
                }


                break;
            case R.id.btn_went:
                if (!isLogin()){
                    binding.btnWent.setBackgroundColor(Color.WHITE);
                    showLoginFragment();
                } else {
                    ApiBuilder.getInstance().doUpdateEvent(TOKEN,2,eventId).enqueue(callbackUpdateEvent);
                }
                break;
        }
    }

    private void showLoginFragment(){
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("ACTION",true);
        startActivity(intent);
    }
    Callback<ApiResultNearlyEvent> callbackNearlyEvent = new Callback<ApiResultNearlyEvent>() {
        @Override
        public void onResponse(Call<ApiResultNearlyEvent> call, Response<ApiResultNearlyEvent> response) {
            if (response.body()!=null){
                if (response.body().getStatus()!=0){
                    List<Event> events = response.body().getResponse().getEvent();
                    adapter.setData(events);
                    binding.lvEvent.setAdapter(adapter);
                    adapter.setListener(new EventInDetailAdapter.ItemEventListener() {
                        @Override
                        public void OnItemClick(Event event) {
                            Intent intent = new Intent(EventDetailActivity.this, EventDetailActivity.class);
                            intent.putExtra("event_id",event.getId());
                            startActivity(intent);
                            finish();
                        }
                    });
                }
            }
        }

        @Override
        public void onFailure(Call<ApiResultNearlyEvent> call, Throwable t) {
            Toast.makeText(EventDetailActivity.this, "Lỗi hệ thống", Toast.LENGTH_SHORT).show();
        }
    };

    Callback<ApiResult> callbackUpdateEvent = new Callback<ApiResult>() {
        @Override
        public void onResponse(Call<ApiResult> call, Response<ApiResult> response) {
            if (response.body()!=null){
                if (response.body().getStatus()==1){
                    Toast.makeText(EventDetailActivity.this, "Đã cập nhật trạng thái", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EventDetailActivity.this, ""+response.body().getError_message(), Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onFailure(Call<ApiResult> call, Throwable t) {
            Toast.makeText(EventDetailActivity.this, "Lỗi hệ thống!", Toast.LENGTH_SHORT).show();
        }
    };


}

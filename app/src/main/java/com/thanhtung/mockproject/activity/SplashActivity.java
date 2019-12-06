package com.thanhtung.mockproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.thanhtung.mockproject.MyShared;
import com.thanhtung.mockproject.R;
import com.thanhtung.mockproject.api.ApiBuilder;
import com.thanhtung.mockproject.api.ApiResult;
import com.thanhtung.mockproject.dao.AppCategorydatabase;
import com.thanhtung.mockproject.dao.AppEventdatabase;
import com.thanhtung.mockproject.dao.AppNewsdatabase;
import com.thanhtung.mockproject.databinding.ActivitySplashBinding;
import com.thanhtung.mockproject.ui.mypage.login.LoginFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SplashActivity extends AppCompatActivity implements Callback<ApiResult> {
    private ActivitySplashBinding binding;
    private MyShared myShared;
    private String TOKEN;
    private String pastDate;
    private String currentDate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        myShared = new MyShared(this);
        TOKEN = "bearer " + myShared.get(LoginFragment.KEY_TOKEN);
        ApiBuilder.getInstance().getGoingEvent(TOKEN, 1).enqueue(this);

    }

    private void deleteAllTable() {
        AppNewsdatabase.getInstance(this, "news-database").getNewsDao().deleteAll();
        AppEventdatabase.getInstance(this, "event-database").getEventDao().deleteAll();
        AppCategorydatabase.getInstance(this, "event-category-database").getCategoryDao().deleteAll();
    }


    private boolean isDateEquals() {
        if (myShared.get("current_date").equals("")) {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            pastDate = dateFormat.format(calendar.getTime());
            myShared.put("current_date", pastDate);
            Log.e("LOG", "PAST DATE: " + pastDate);
            return false;
        } else {
            pastDate = myShared.get("current_date");
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            currentDate = dateFormat.format(calendar.getTime());
            Log.e("LOG", "PAST DATE: " + pastDate);
            if (pastDate.equals(currentDate)) {
                return true;
            } else {
                myShared.put("current_date", currentDate);
                Log.e("LOG", "CURRENT DATE: " + currentDate);
                return false;
            }
        }
    }
    @Override
    public void onResponse(Call<ApiResult> call, Response<ApiResult> response) {
        if (response.body() != null) {
            if (response.body().getStatus() != 1) {
                myShared.remove(LoginFragment.KEY_TOKEN);
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                if (!isDateEquals()) {
                    deleteAllTable();
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

        } else {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onFailure(Call<ApiResult> call, Throwable t) {
        Toast.makeText(this, "Kiểm tra kết nối của bạn", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}

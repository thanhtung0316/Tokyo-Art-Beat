package com.thanhtung.mockproject;

import android.os.AsyncTask;
import android.util.Log;
import com.thanhtung.mockproject.api.ApiBuilder;
import com.thanhtung.mockproject.api.ApiResultNearlyEvent;
import com.thanhtung.mockproject.model.EventDetail;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetEventsAsync extends AsyncTask<String,Integer, List<EventDetail>> {

    private List<EventDetail> events;

    @Override
    protected void onPreExecute() {
        events = new ArrayList<>();
        super.onPreExecute();
    }

    @Override
    protected List<EventDetail> doInBackground(String... strings) {
        String TOKEN = strings[0];
        int radius = Integer.parseInt(strings[1]);
        final Double newLong = Double.parseDouble(strings[2]);
        final Double newLa = Double.parseDouble(strings[3]);
        ApiBuilder.getInstance()
                .listNearlyEvents(TOKEN, radius, newLong, newLa)
                .enqueue(new Callback<ApiResultNearlyEvent>() {
                    @Override
                    public void onResponse(Call<ApiResultNearlyEvent> call, Response<ApiResultNearlyEvent> response) {
                        if (response.body() != null) {
//                            events = response.body().getResponse().getEventDetailList();
                            Log.e("LOG", "Size Async: " + events.size());
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiResultNearlyEvent> call, Throwable t) {
                    }
                });
        return events ;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(List<EventDetail> events) {
        this.events =events;
        int a = events.size();
        Log.e("LOG","onPost: "+a);
        super.onPostExecute(events);
    }
}

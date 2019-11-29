package com.thanhtung.mockproject.ui.near;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.thanhtung.mockproject.MyShared;
import com.thanhtung.mockproject.R;
import com.thanhtung.mockproject.adapter.EventInDetailAdapter;
import com.thanhtung.mockproject.adapter.MarkerAdapter;
import com.thanhtung.mockproject.api.ApiBuilder;
import com.thanhtung.mockproject.api.ApiResultNearlyEvent;
import com.thanhtung.mockproject.dao.AppMarkerdatabase;
import com.thanhtung.mockproject.databinding.FragmentNearBinding;
import com.thanhtung.mockproject.model.Event;
import com.thanhtung.mockproject.model.MarkerEvent;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.thanhtung.mockproject.ui.mypage.login.LoginFragment.KEY_TOKEN;

public class NearFragment extends Fragment {

    private Location mlocation;
    private Marker prevMarker;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;
    private FragmentNearBinding binding;
    private Context context;
    private SupportMapFragment mapFragment;
    private List<MarkerEvent> markerEvents;
    private final String[] PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };
    private MyShared myShared;
    private List<MarkerOptions> markerOptionsList;
    private String TOKEN;
    private GoogleMap mMap;
    private EventInDetailAdapter adapter;
    private double lat;
    private double lng;
    private MarkerAdapter markerAdapter;
    private SnapHelper snapHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        context = container.getContext();
        myShared = new MyShared(context);
        markerAdapter = new MarkerAdapter(context);
        snapHelper = new PagerSnapHelper();
        adapter = new EventInDetailAdapter(context);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_near, container, false);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
        TOKEN = "bearer " + myShared.get(KEY_TOKEN);
        snapHelper.attachToRecyclerView(binding.lvEvent);
        binding.lvEvent.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int position = getCurrentItem();
                    MoveToItem(markerEvents.get(position));
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        if (checkPermission() == true) {
            GetLastLocation();
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(PERMISSIONS, 0);
            }
        }


        return binding.getRoot();
    }

    private void GetLastLocation() {
//
        if (checkPermission()) {
            Task<Location> task = fusedLocationProviderClient.getLastLocation();
            task.addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        mlocation = location;
                        mapFragment = (SupportMapFragment) getChildFragmentManager()
                                .findFragmentById(R.id.map);
                        mapFragment.getMapAsync(new OnMapReadyCallback() {
                            @Override
                            public void onMapReady(GoogleMap googleMap) {
                                mMap = googleMap;
                                mMap.setOnMarkerClickListener(listener);
                                mMap.setMyLocationEnabled(true);
                                mMap.setOnCameraMoveListener(cameraMoveListener);
                                lat = mlocation.getLatitude();
                                lng = mlocation.getLongitude();
                                LatLng latLng = new LatLng(lat, lng);
                                MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("You're HERE");
//                                googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                                googleMap.addMarker(markerOptions);

                                List<MarkerEvent> dbMarkerEvents = AppMarkerdatabase.getInstance(context, "marker-database").getMarkerDao().getAll();

                                if (dbMarkerEvents.size() == 0) {
                                    ApiBuilder.getInstance().listNearlyEvents(TOKEN, 50, lng, lat).enqueue(new retrofit2.Callback<ApiResultNearlyEvent>() {
                                        @Override
                                        public void onResponse(Call<ApiResultNearlyEvent> call, Response<ApiResultNearlyEvent> response) {
                                            if (response.body() != null) {
                                                List<Event> events = response.body().getResponse().getEvent();
                                                List<MarkerEvent> markerEventList = initMarker(events);
                                                AppMarkerdatabase.getInstance(context, "marker-database").getMarkerDao().insert(markerEventList);
                                                markerEvents = markerEventList;
                                                adapter.setData(events);
                                                binding.lvEvent.setAdapter(adapter);
                                                addMarker(initMarker(events));
                                                initMarker(events);

                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<ApiResultNearlyEvent> call, Throwable t) {
                                            Toast.makeText(context, "Lỗi hệ thống!", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                } else {
                                    markerEvents = dbMarkerEvents;
                                    markerAdapter.setData(markerEvents);
                                    binding.lvEvent.setAdapter(markerAdapter);
                                    addMarker(markerEvents);
                                    binding.lvEvent.setHasFixedSize(false);
                                }


                            }
                        });
                    }
                }
            });
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (checkPermission()) {
            GetLastLocation();
        } else {
            if (mMap!=null){
                LatLng latLng = new LatLng(21.02, 105.78);
//                                googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));

            }
            Toast.makeText(context, "Hãy cấp quyền GPS để có thể sử dụng đầy đủ chúc năng!", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String p : PERMISSIONS) {
                int check = context.checkSelfPermission(p);
                if (check != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    private void addMarker(List<MarkerEvent> events) {
        double geoLong;
        double geoLat;
        markerOptionsList = new ArrayList<>();
        for (MarkerEvent event : events) {
            geoLong = event.getLongitude();
            geoLat = event.getLatitude();
            LatLng loc = new LatLng(geoLat, geoLong);
            BitmapDescriptor icon = bitmapDescriptorFromVector(context, R.drawable.ic_location_on_black_24dp);
            MarkerOptions m = new MarkerOptions().position(loc).title(event.getEventName())
                    .snippet("Cách bạn: " + convertDistance(calculateDistance(geoLat, geoLong)[0]))
                    .icon(icon);
            mMap.addMarker(m);

        }

    }


    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth()
                , vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth()
                , vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }


    GoogleMap.OnMarkerClickListener listener = new GoogleMap.OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(Marker marker) {
            clickMarker(marker);

            if (prevMarker != null) {
                //Set prevMarker back to default color
                prevMarker.setIcon(bitmapDescriptorFromVector(context, R.drawable.ic_location_on_black_24dp));
            }

            //leave MarkerEvent default color if re-click current MarkerEvent
            if (!marker.equals(prevMarker)) {
                marker.setIcon(bitmapDescriptorFromVector(context, R.drawable.ic_place_black_24dp));
                prevMarker = marker;
            }
            prevMarker = marker;


            return false;
        }
    };

    private float[] calculateDistance(double endLat, double endLng) {
        float[] results = new float[1];
        Location.distanceBetween(lat, lng, endLat, endLng, results);
        return results;
    }

    private String convertDistance(float distance) {
        if (distance < 1000) {
            return (int) distance + " m";
        } else {
            return (Math.round(distance * 10) / 10) / 1000 + " Km";
        }
    }

    private List<MarkerEvent> initMarker(List<Event> events) {
        markerEvents = new ArrayList<>();
        for (Event event : events) {
            MarkerEvent markerEvent = new MarkerEvent();
            markerEvent.setDateEndEvent(event.getScheduleEndDate());
            markerEvent.setDateStartEvent(event.getScheduleStartDate());
            markerEvent.setDistance(calculateDistance(event.getVenue().getGeoLat(), event.getVenue().getGeoLong())[0]);
            markerEvent.setEventDesc(event.getDescriptionHtml());
            markerEvent.setEventName(event.getName());
            markerEvent.setGoingCount(event.getGoingCount());
            markerEvent.setPhoto(event.getPhoto());
            markerEvent.setLatitude(event.getVenue().getGeoLat());
            markerEvent.setLongitude(event.getVenue().getGeoLong());
            markerEvents.add(markerEvent);
        }
        return markerEvents;
    }

    private void MoveToItem(MarkerEvent markerEvent) {
        LatLng loc = new LatLng(markerEvent.getLatitude(), markerEvent.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 15));
        for (MarkerEvent m : markerEvents) {
            if (m.getLongitude() == (loc.longitude) && m.getLatitude() == (loc.latitude)) {
                mMap.addMarker(new MarkerOptions().title(m.getEventName()).snippet("Cách bạn: " + m.getDistance()).position(loc))
                        .setIcon(bitmapDescriptorFromVector(context, R.drawable.ic_place_black_24dp));
            } else {
                mMap.addMarker(new MarkerOptions().position(loc))
                        .setIcon(bitmapDescriptorFromVector(context, R.drawable.ic_location_on_black_24dp));
            }
        }
    }

    private int getCurrentItem() {
        return ((LinearLayoutManager) binding.lvEvent.getLayoutManager()).findFirstVisibleItemPosition();
    }

    private void clickMarker(Marker marker) {
        for (int i = 1; i < markerEvents.size(); i++) {
            if (marker.getTitle()!=null){
                if (marker.getTitle().equals(markerEvents.get(i).getEventName())) {
                    binding.lvEvent.scrollToPosition(i);
                }
            }

        }

    }

    private GoogleMap.OnCameraMoveListener cameraMoveListener = new GoogleMap.OnCameraMoveListener() {
        @Override
        public void onCameraMove() {

            mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
                @Override
                public void onMapLoaded() {
                    double newLa;
                    double newLong;
                    newLa = mMap.getCameraPosition().target.latitude;
                    newLong = mMap.getCameraPosition().target.longitude;
                    if (Math.abs(newLa - lat) > 0.015 || Math.abs(newLong - lng) > 0.015) {
                        ApiBuilder.getInstance().listNearlyEvents(TOKEN, 50, newLong, newLa).enqueue(eventCallback);
                    }

                }
            });
        }
    };

    private Callback<ApiResultNearlyEvent> eventCallback = new Callback<ApiResultNearlyEvent>() {
        @Override
        public void onResponse(Call<ApiResultNearlyEvent> call, Response<ApiResultNearlyEvent> response) {
            if (response.body()!=null){
                List<Event> events = response.body().getResponse().getEvent();
                List<MarkerEvent> markerEventList = initMarker(events);
                AppMarkerdatabase.getInstance(context, "marker-database").getMarkerDao().insert(markerEventList);
                markerEvents = markerEventList;
                adapter.setData(events);
                binding.lvEvent.setAdapter(adapter);
                addMarker(initMarker(events));
                initMarker(events);
            }
        }

        @Override
        public void onFailure(Call<ApiResultNearlyEvent> call, Throwable t) {
            Toast.makeText(context, "Kiểm tra kết nối và thử lại!", Toast.LENGTH_SHORT).show();
        }
    };

}

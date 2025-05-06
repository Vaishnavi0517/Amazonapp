package com.example.amazonapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.Locale;

public class CurrentLocationActivity {
    private static AppCompatActivity activity = null;
    private static FusedLocationProviderClient fusedLocationProviderClient = null;

    public CurrentLocationActivity(AppCompatActivity activity) {
        this.activity = activity;
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity);
    }

    public static void getCurrentLocation(OnLocationResultListener listener) {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }

        LocationRequest locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10000)
                .setFastestInterval(5000);

        fusedLocationProviderClient.requestLocationUpdates(locationRequest, new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                Location location = locationResult.getLastLocation();
                if (location != null) {
                    try {
                        Geocoder geocoder = new Geocoder(activity, Locale.getDefault());
                        String city = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1)
                                .get(0).getLocality();
                        String state = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1)
                                .get(0).getAdminArea();
                        String country = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1)
                                .get(0).getCountryName();

                        listener.onLocationResult(city, state, country);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                fusedLocationProviderClient.removeLocationUpdates(this);
            }
        }, Looper.getMainLooper());
    }

    public interface OnLocationResultListener {
        void onLocationResult(String city, String state, String country);
    }
}
package com.example.amazonapp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.amazonapp.Interfaces.WeatherUtility;
import com.example.amazonapp.model.WeatherResponse;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SwitchThemesActivity extends AppCompatActivity {
    private double longitude;
    private double latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        if (hour >= 6 && hour <= 12) {
            setTheme(R.style.Base_Theme_Amazonapp_light);
        } else {
            setTheme(R.style.Base_Theme_Amazonapp_dark);
        }

        setContentView(R.layout.activity_switch_themes);


        fetchLocationAndWeather(hour);
    }

    private void fetchLocationAndWeather(int hour) {

        CurrentLocationActivity currentLocationActivity = new CurrentLocationActivity(this);
        CurrentLocationActivity.getCurrentLocation((city, state, country) -> {
            if (hour >= 6 && hour <= 12) {

                updateUI(city, state, null);
            } else {

                WeatherUtility service = new Retrofit.Builder()
                        .baseUrl("https://api.openweathermap.org/data/2.5/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                        .create(WeatherUtility.class);


                service.getCurrentWeather(latitude, longitude, "42a23e8d046e229a76e4cb5938a1de67", "metric")
                        .enqueue(new Callback<WeatherResponse>() {
                            @Override
                            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                                if (response.isSuccessful() && response.body() != null) {
                                    double temp = response.body().getMain().getTemp();
                                    String fetchedCity = response.body().getName();
                                    String country = response.body().getSys().getCountry();

                                    updateUI(fetchedCity, null, temp + "°C, " + country);
                                }
                            }

                            @Override
                            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                                t.printStackTrace();
                            }
                        });
            }
        });
    }

    private void updateUI(String city, String state, String additionalInfo) {
        TextView locationTextView = findViewById(R.id.locationTextView);
        if (additionalInfo != null) {
            locationTextView.setText(city + ", " + additionalInfo);
        } else {
            locationTextView.setText(city + ", " + state);
        }
    }
}
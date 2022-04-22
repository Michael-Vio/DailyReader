package com.example.dailyreader.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.dailyreader.WeatherApi;
import com.example.dailyreader.databinding.WeatherFragmentBinding;
import com.example.dailyreader.WeatherRoot;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class WeatherFragment extends Fragment {

    private WeatherFragmentBinding weatherBinding;


    public WeatherFragment () {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        weatherBinding = WeatherFragmentBinding.inflate(inflater, container, false);
        View view = weatherBinding.getRoot();


        //Use weather API
        //https://api.openweathermap.org/data/2.5/weather?lat=37.8136&lon=144.9631&appid=2e1018bb418dd391d11b25c5a7fad3a0
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeatherApi weatherApi = retrofit.create(WeatherApi.class);

        Call<WeatherRoot> call = weatherApi.getWeather();

        //Call
        call.enqueue(new Callback<WeatherRoot>()
        {
            @Override
            public void onResponse(Call<WeatherRoot> call, Response<WeatherRoot> response){
                WeatherRoot weatherRoot = response.body();
                System.out.println();
            }

            @Override
            public void onFailure(Call<WeatherRoot> call, Throwable throwable){
                System.out.println(throwable.getMessage());
            }


        });

        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        weatherBinding = null;
    }







}
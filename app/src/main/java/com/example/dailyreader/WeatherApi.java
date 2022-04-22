package com.example.dailyreader;

import retrofit2.Call;
import retrofit2.http.GET;

public interface WeatherApi {

    //Use weather API
    //https://api.openweathermap.org/data/2.5/weather?lat=37.8136&lon=144.9631&appid=2e1018bb418dd391d11b25c5a7fad3a0
    @GET("weather?lat=37.8136&lon=144.9631&appid=2e1018bb418dd391d11b25c5a7fad3a0")
    Call<WeatherRoot> getWeather();

    //@Get("weather")
    //Call<Object> getWeather(@Path("lat") double);

}

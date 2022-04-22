package com.example.dailyreader;

import java.util.ArrayList;

public class WeatherRoot{
    public Coord coord;
    public ArrayList<Weather> weather;
    public String base;
    public Main main;
    public int visibility;
    public Wind wind;
    public Clouds clouds;
    public int dt;
    public Sys sys;
    public int timezone;
    public int id;
    public String name;
    public int cod;
}

class Coord{
    public double lon;
    public double lat;
}

class Weather{
    public int id;
    public String main;
    public String description;
    public String icon;
}

class Main{
    public double temp;
    public double feels_like;
    public double temp_min;
    public double temp_max;
    public int pressure;
    public int humidity;
    public int sea_level;
    public int grnd_level;
}

class Wind{
    public double speed;
    public int deg;
    public double gust;
}

class Clouds{
    public int all;
}

class Sys{
    public int sunrise;
    public int sunset;
}



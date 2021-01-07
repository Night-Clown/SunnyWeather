package com.example.sunnyweather.logic.dao;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.sunnyweather.SunnyWeatherApplication;
import com.example.sunnyweather.logic.model.Place;
import com.google.gson.Gson;

public class PlaceDao {
    private static SharedPreferences sharedPreference() {
        return SunnyWeatherApplication.getContext().getSharedPreferences("sunny_weather", Context.MODE_PRIVATE);
    }

    public static void savePlace(Place place) {
        SharedPreferences.Editor edit = sharedPreference().edit();
        edit.putString("place", new Gson().toJson(place));
        edit.apply();
    }

    public static Place getSavedPlace() {
        String string = sharedPreference().getString("place", "");
        return new Gson().fromJson(string, Place.class);
    }

    public static boolean isPlaceSaved() {
        return sharedPreference().contains("place");
    }
}

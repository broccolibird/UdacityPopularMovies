package com.katmitchell.udacitypopularmovies.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;

/**
 * Created by Kat on 10/4/15.
 */
public class GsonSingleton {

    private Gson mGson;

    private static GsonSingleton ourInstance = new GsonSingleton();

    public static GsonSingleton getInstance() {
        if (ourInstance == null) {
            ourInstance = new GsonSingleton();
        }
        return ourInstance;
    }

    private GsonSingleton() {
        mGson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateDeserializer())
                .create();
    }

    public Gson getGson() {
        return mGson;
    }
}

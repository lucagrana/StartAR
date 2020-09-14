package com.example.startar;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class MyModel {

    private static MyModel instance = null;
    private static ArrayList<Double> latitudini = null;
    private static ArrayList<Double> longitudini = null;

    public static synchronized MyModel getInstance() {
        if (instance == null) {
            instance = new MyModel();
        }
        return instance;

    }

    public static void coordinate(JSONObject punti) throws JSONException {
        JSONArray points = punti.getJSONArray("punti");
        latitudini = new ArrayList<Double>(points.length());
        longitudini = new ArrayList<Double>(points.length());
        for (int i = 0; i < points.length(); ++i) {
            JSONObject puntis = points.getJSONObject(i);
            Double lat = puntis.getDouble("latitudine");
            Double lon = puntis.getDouble("longitudine");
            latitudini.add(lat);
            longitudini.add(lon);
        }
    }




}

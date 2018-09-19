package com.example.ahmedmesawer.kumapsdemo;


import com.google.android.gms.maps.model.LatLng;

public class Event {

    private String name;
    private LatLng latLng;

    Event(String name, LatLng latLng) {
        this.name = name;
        this.latLng = latLng;
    }

    public String getName() {
        return name;
    }

    public LatLng getLatLng() {
        return latLng;
    }
}

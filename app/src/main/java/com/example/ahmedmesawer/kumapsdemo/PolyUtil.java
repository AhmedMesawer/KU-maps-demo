package com.example.ahmedmesawer.kumapsdemo;

import com.google.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

class PolyUtil {
    public static List<com.google.android.gms.maps.model.LatLng> decode(List<LatLng> latLngs) {
        List<com.google.android.gms.maps.model.LatLng> latLngs1 = new ArrayList<>();
        for (LatLng latLng : latLngs) {
            latLngs1.add(new com.google.android.gms.maps.model.LatLng(latLng.lat, latLng.lng));
        }
        return latLngs1;
    }
}

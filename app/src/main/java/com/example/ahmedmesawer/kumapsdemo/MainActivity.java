package com.example.ahmedmesawer.kumapsdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements EventsAdapter.OnItemClickListener {

    @BindView(R.id.rv)
    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setupAdapter();
    }

    private void setupAdapter() {
        EventsAdapter eventsAdapter = new EventsAdapter(this);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        rv.setAdapter(eventsAdapter);
    }

    @Override
    public void onItemClicked(LatLng latLng) {
        showDirectionsOnMap(latLng);
    }

    private void showDirectionsOnMap(LatLng latLng) {
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra("to", latLng);
        startActivity(intent);
    }
}

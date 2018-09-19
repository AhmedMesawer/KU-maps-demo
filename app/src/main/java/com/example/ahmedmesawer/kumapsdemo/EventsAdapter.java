package com.example.ahmedmesawer.kumapsdemo;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {

    private List<Event> events;
    private OnItemClickListener onItemClickListener;

    EventsAdapter(OnItemClickListener onItemClickListener) {
        events = new ArrayList<>();
        events.add(new Event("Law School", new LatLng(29.3408152, 47.9128265)));
        events.add(new Event("Coffee Republic", new LatLng(29.3416195, 47.9184485)));
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_event, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Event event = events.get(position);
        holder.eventName.setText(event.getName());
        holder.eventLocation.setOnClickListener(
                v -> onItemClickListener.onItemClicked(event.getLatLng()));
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.eventName)
        TextView eventName;
        @BindView(R.id.eventLocation)
        ImageView eventLocation;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    interface OnItemClickListener {
        void onItemClicked(LatLng latLng);
    }
}

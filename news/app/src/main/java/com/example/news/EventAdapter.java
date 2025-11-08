package com.example.news;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {
    private List<Event> events;

    public EventAdapter(List<Event> events) {
        this.events = events;
    }

    public void updateEvents(List<Event> events) {
        this.events = events;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_event, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Event event = events.get(position);
        holder.title.setText(event.getTitle());
        holder.description.setText(event.getDescription()); // ДОБАВИЛИ ОПИСАНИЕ
        holder.date.setText("Дата: " + event.getDate() + " " + event.getTime());
        holder.location.setText("Место: " + event.getLocation());
        holder.city.setText("Город: " + event.getCity());
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    static class EventViewHolder extends RecyclerView.ViewHolder {
        TextView title, description, date, location, city; // ДОБАВИЛИ description

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.eventTitle);
            description = itemView.findViewById(R.id.eventDescription); // ДОБАВИЛИ
            date = itemView.findViewById(R.id.eventDate);
            location = itemView.findViewById(R.id.eventLocation);
            city = itemView.findViewById(R.id.eventCity);
        }
    }
}
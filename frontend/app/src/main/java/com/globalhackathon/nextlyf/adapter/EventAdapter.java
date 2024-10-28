package com.globalhackathon.nextlyf.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.globalhackathon.nextlyf.R;
import com.globalhackathon.nextlyf.databinding.EventItemBinding;
import com.globalhackathon.nextlyf.databinding.RoomItem2Binding;
import com.globalhackathon.nextlyf.model.Events;
import com.google.gson.Gson;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter {

    private List<Events> eventsList;

    private Gson gson;

    public EventAdapter(){}

    public EventAdapter(List<Events> eventsList){
        this.eventsList = eventsList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item, parent, false);

        gson = new Gson();
        return new EventAdapter.EventViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        final EventViewHolder viewHolder = (EventViewHolder) holder;

        if(eventsList != null && !eventsList.isEmpty()){
            Log.d("EventAdapter-Bind", "onBindViewHolder");
            Events event = eventsList.get(position);

            Log.d("EventAdapter-EventData", gson.toJson(event));

            viewHolder.binding.roomName.setText(event.getName());
            viewHolder.binding.roomLocation.setText(event.getEventLocation());
            viewHolder.binding.roomAmenitiesFirst.setText(event.getEventTags());

            viewHolder.binding.roomSize.setText(
                    event.getMutual() != null ? String.format("%s Mutuals %s Attendees", event.getMutual(), String.valueOf(event.getTotal())) :
                    String.format("%s Attendees", event.getTotal()));

            viewHolder.binding.roomAmenitiesFirst2.setText(event.getDate());
            viewHolder.binding.roomPrice.setText(String.format("$ %s", event.getEventPrice()));

            Glide.with(viewHolder.binding.roomImage.getContext()).
                    load(event.getEventImage()).
                    centerInside().
                    diskCacheStrategy(DiskCacheStrategy.RESOURCE).
                    placeholder(R.drawable.lyf_event).
                    override(200,100).
                    into(viewHolder.binding.roomImage);
        }

    }

    @Override
    public int getItemCount() {
        return eventsList != null ? eventsList.size() : 1;
    }

    public class EventViewHolder extends RecyclerView.ViewHolder{
        EventItemBinding binding;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);

            Log.i("EventAdapter-Bind", "EventViewHolder");

            binding = EventItemBinding.bind(itemView);
        }
    }

}

package com.globalhackathon.nextlyf.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.globalhackathon.nextlyf.R;
import com.globalhackathon.nextlyf.databinding.EventItemBinding;
import com.globalhackathon.nextlyf.databinding.RoomItem2Binding;

public class EventAdapter extends RecyclerView.Adapter {
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item, parent, false);
        return new EventAdapter.EventViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
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

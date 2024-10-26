package com.globalhackathon.nextlyf.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.globalhackathon.nextlyf.R;
import com.globalhackathon.nextlyf.databinding.RoomItem2Binding;
import com.globalhackathon.nextlyf.databinding.RoomItemBinding;

public class RoomAdapter extends RecyclerView.Adapter{

    Context context;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.room_item2, parent, false);
        return new RoomViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }


    public class RoomViewHolder extends RecyclerView.ViewHolder{
        RoomItem2Binding binding;

        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);

            Log.i("RoomAdapter-Bind", "RoomViewHolder");

            binding = RoomItem2Binding.bind(itemView);
        }
    }
}

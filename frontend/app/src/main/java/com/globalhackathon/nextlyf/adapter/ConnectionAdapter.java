package com.globalhackathon.nextlyf.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.globalhackathon.nextlyf.R;
import com.globalhackathon.nextlyf.databinding.ConnectionItemBinding;
import com.globalhackathon.nextlyf.databinding.EventItemBinding;

public class ConnectionAdapter extends RecyclerView.Adapter {
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.connection_item, parent, false);
        return new ConnectionAdapter.ConnectionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ConnectionViewHolder extends RecyclerView.ViewHolder{
        ConnectionItemBinding binding;

        public ConnectionViewHolder(@NonNull View itemView) {
            super(itemView);

            Log.i("ConnectionAdapter-Bind", "ConnectionViewHolder");

            binding = ConnectionItemBinding.bind(itemView);
        }
    }
}

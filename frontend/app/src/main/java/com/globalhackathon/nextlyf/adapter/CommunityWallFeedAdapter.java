package com.globalhackathon.nextlyf.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.globalhackathon.nextlyf.R;
import com.globalhackathon.nextlyf.databinding.ChatItemBotBinding;
import com.globalhackathon.nextlyf.databinding.WallItemBinding;
import com.globalhackathon.nextlyf.model.ChatMessage;

public class CommunityWallFeedAdapter extends RecyclerView.Adapter {


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wall_item, parent, false);
        return new CommunityWallFeedAdapter.CommunityWallFeedViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class CommunityWallFeedViewHolder extends RecyclerView.ViewHolder {

        WallItemBinding wallItemBinding;

        public CommunityWallFeedViewHolder(@NonNull View itemView) {
            super(itemView);

            wallItemBinding = WallItemBinding.bind(itemView);

        }
    }

}

package com.globalhackathon.nextlyf.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.globalhackathon.nextlyf.R;
import com.globalhackathon.nextlyf.databinding.CarouselGroupBinding;

public class CommunityGroupEventHighlightAdapter extends RecyclerView.Adapter {



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.carousel_group, parent, false);
        return new CommunityGroupEventHighlightAdapter.CommunityGroupEventHighlightViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class CommunityGroupEventHighlightViewHolder extends RecyclerView.ViewHolder {

        CarouselGroupBinding carouselGroupBinding;

        public CommunityGroupEventHighlightViewHolder(@NonNull View itemView) {
            super(itemView);

            carouselGroupBinding = CarouselGroupBinding.bind(itemView);
        }
    }
}

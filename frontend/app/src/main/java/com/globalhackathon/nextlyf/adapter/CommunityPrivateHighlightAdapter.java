package com.globalhackathon.nextlyf.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.globalhackathon.nextlyf.R;
import com.globalhackathon.nextlyf.databinding.CarouselPrivateBinding;

public class CommunityPrivateHighlightAdapter extends RecyclerView.Adapter {


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.carousel_private, parent, false);
        return new CommunityPrivateHighlightAdapter.CommunityPrivateHighlightViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class CommunityPrivateHighlightViewHolder extends RecyclerView.ViewHolder {

        CarouselPrivateBinding carouselPrivateBinding;

        public CommunityPrivateHighlightViewHolder(@NonNull View itemView) {
            super(itemView);

            carouselPrivateBinding = CarouselPrivateBinding.bind(itemView);

        }
    }
}

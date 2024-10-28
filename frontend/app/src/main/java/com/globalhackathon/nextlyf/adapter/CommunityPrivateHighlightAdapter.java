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

        final CommunityPrivateHighlightViewHolder viewHolder = (CommunityPrivateHighlightViewHolder) holder;

        if(position == 0){
            viewHolder.carouselPrivateBinding.carouselImageView.setImageResource(R.drawable.download);
            viewHolder.carouselPrivateBinding.carouselTitle.setText("John Doe");
            viewHolder.carouselPrivateBinding.carouselSubtitle.setText("Checked in Lyf Funan");
        } else if (position == 1){
            viewHolder.carouselPrivateBinding.carouselImageView.setImageResource(R.drawable.download_1);
            viewHolder.carouselPrivateBinding.carouselTitle.setText("Rohit");
            viewHolder.carouselPrivateBinding.carouselSubtitle.setText("Join Tech Meetup");
        } else if (position == 2){
            viewHolder.carouselPrivateBinding.carouselImageView.setImageResource(R.drawable.download_2);
            viewHolder.carouselPrivateBinding.carouselTitle.setText("Sky");
            viewHolder.carouselPrivateBinding.carouselSubtitle.setText("Joined Yoga Class");
        } else if (position == 3){
            viewHolder.carouselPrivateBinding.carouselImageView.setImageResource(R.drawable.download_3);
            viewHolder.carouselPrivateBinding.carouselTitle.setText("Jack");
            viewHolder.carouselPrivateBinding.carouselSubtitle.setText("Checked in Lyf Tokyo");
        } else if (position == 4){
            viewHolder.carouselPrivateBinding.carouselImageView.setImageResource(R.drawable.download_1);
            viewHolder.carouselPrivateBinding.carouselTitle.setText("Vivek");
            viewHolder.carouselPrivateBinding.carouselSubtitle.setText("New Resident");
        } else if (position == 5){
            viewHolder.carouselPrivateBinding.carouselImageView.setImageResource(R.drawable.download_2);
            viewHolder.carouselPrivateBinding.carouselTitle.setText("Sky");
            viewHolder.carouselPrivateBinding.carouselSubtitle.setText("7 Week Fitness Challenge");
        }


    }

    @Override
    public int getItemCount() {
        return 6;
    }

    public class CommunityPrivateHighlightViewHolder extends RecyclerView.ViewHolder {

        CarouselPrivateBinding carouselPrivateBinding;

        public CommunityPrivateHighlightViewHolder(@NonNull View itemView) {
            super(itemView);

            carouselPrivateBinding = CarouselPrivateBinding.bind(itemView);

        }
    }
}

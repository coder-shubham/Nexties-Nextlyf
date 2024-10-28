package com.globalhackathon.nextlyf.adapter;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.globalhackathon.nextlyf.R;
import com.globalhackathon.nextlyf.databinding.ConnectionItemBinding;
import com.globalhackathon.nextlyf.model.UserSignUpData;

import java.util.List;
import java.util.Random;

public class ConnectionAdapter extends RecyclerView.Adapter {

    private List<UserSignUpData> userSignUpDataList;

    int count = 0;

    public ConnectionAdapter(){

    }

    public ConnectionAdapter(List<UserSignUpData> userSignUpData){
        this.userSignUpDataList = userSignUpData;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.connection_item, parent, false);
        return new ConnectionAdapter.ConnectionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Log.i("ConnectionAdapter-Bind", "onBindViewHolder");

        final ConnectionViewHolder viewHolder = (ConnectionViewHolder) holder;

        if(userSignUpDataList != null && !userSignUpDataList.isEmpty()){
            UserSignUpData userSignUpData = userSignUpDataList.get(position);

            viewHolder.binding.roomName.setText(userSignUpData.getName());
            viewHolder.binding.roomOffer.setText(userSignUpData.getProfession());
            viewHolder.binding.roomAmenitiesFirst.setText(userSignUpData.getFieldsOfInterest().substring(0,16)
                    .replace(",", " \u2022 "));
            viewHolder.binding.roomLocation.setText("lyf funan, Singapore");

            Glide.with(viewHolder.binding.roomImage.getContext()).
                    load(userSignUpData.getProfilePic()).
                    centerInside().
                    diskCacheStrategy(DiskCacheStrategy.NONE).placeholder(getUserPic()).
                    override(100,110).
                    into(viewHolder.binding.roomImage);
        }

    }

    public int getUserPic(){
        int drawable;
        if(count == 0){
            count++;
            return R.drawable.download_5;
        } else if (count == 1) {
            count++;
            return R.drawable.download_6;
        }else if (count == 2) {
            count++;
            return R.drawable.download_4;
        }else if (count == 3) {
            count++;
            return R.drawable.download_3;
        }else if (count == 4) {
            count++;
            return R.drawable.download_2;
        }else if (count == 5) {
            count++;
            return R.drawable.download_1;
        }else if (count == 6) {
            count++;
            return R.drawable.download;
        }else{
            count = 0;
            return R.drawable.download_5;
        }



    }

    @Override
    public int getItemCount() {
        return userSignUpDataList != null ? userSignUpDataList.size() : 10;
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

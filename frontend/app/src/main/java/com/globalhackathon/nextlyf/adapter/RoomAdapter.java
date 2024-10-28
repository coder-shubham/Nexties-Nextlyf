package com.globalhackathon.nextlyf.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.globalhackathon.nextlyf.R;
import com.globalhackathon.nextlyf.databinding.RoomItem2Binding;
import com.globalhackathon.nextlyf.databinding.RoomItemBinding;
import com.globalhackathon.nextlyf.listeners.PaymentConfirmationListener;
import com.globalhackathon.nextlyf.model.Rooms;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RoomAdapter extends RecyclerView.Adapter{

    Context context;

    private List<Rooms> rooms;

    private Boolean fromBot;

    PaymentConfirmationListener paymentConfirmationListener;

    public RoomAdapter() {

    }

    public RoomAdapter(List<Rooms> rooms){
        this.rooms = rooms;
    }

    public RoomAdapter(List<Rooms> rooms, Boolean fromBot){
        this.rooms = rooms;
        this.fromBot = fromBot;
    }

    public RoomAdapter(List<Rooms> rooms, Boolean fromBot, PaymentConfirmationListener paymentConfirmationListener){
        this.rooms = rooms;
        this.fromBot = fromBot;
        this.paymentConfirmationListener = paymentConfirmationListener;
    }

    public Boolean getFromBot(){
        return fromBot;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.room_item2, parent, false);
        return new RoomViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        final RoomViewHolder viewHolder = (RoomViewHolder) holder;

        if(rooms != null && !rooms.isEmpty()){
            Log.d("RoomAdapter-Bind", "onBindViewHolder");
            Rooms room = rooms.get(position);

            viewHolder.binding.roomName.setText(room.getName());

            Integer roomSize = room.getNoOfBeds() * 2;
            Log.d("RoomAdapter-Bind", "Room Size: " + roomSize);
            viewHolder.binding.roomSize.setText(String.format("Max %s pax", roomSize));

            viewHolder.binding.roomAmenitiesFirst.setText(room.getTags());

            Double price = room.getPrice();

            int offer = getRandomOfferValue();

            Double discountedPrice = price - (price * offer / 100);

            //Format discounted price to have single digit after decimal
            discountedPrice = Math.round(discountedPrice * 10.0) / 10.0;

            viewHolder.binding.roomPrice.setText(String.format("$ %s", discountedPrice));

            viewHolder.binding.roomOffer.setText(String.format("%s%% off", offer));

            viewHolder.binding.roomLocation.setText(room.getProperty());

            viewHolder.binding.totalRatingText.setText(String.format("(%s reviews)", getRandomRatingCountValue()));

            viewHolder.binding.roomRating.setRating(getRandomRatingValue());

            Glide.with(viewHolder.binding.roomImage.getContext()).
                    load(room.getImage()).
                    centerInside().
                    diskCacheStrategy(DiskCacheStrategy.RESOURCE).placeholder(R.drawable.lyf_one).
                    override(200,100).
                    into(viewHolder.binding.roomImage);

            if(fromBot != null && fromBot){
                Log.d("RoomAdapter-Bind", "From Bot");
                viewHolder.binding.payLayout.setVisibility(View.VISIBLE);
            }

            viewHolder.binding.payButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("RoomAdapter-Bind", "Pay Button Clicked");
                    paymentConfirmationListener.onPaymentConfirmed();
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return rooms != null ? rooms.size():10;
    }

    private int getRandomOfferValue(){

        //Generate random number between 10 - 20

        int randomNum = ThreadLocalRandom.current().nextInt(10, 20 + 1);

        return randomNum;

    }

    private int getRandomRatingCountValue(){

        //Generate random number between 10 - 20

        return ThreadLocalRandom.current().nextInt(3000, 8000 + 1);

    }

    private int getRandomRatingValue(){

        //Generate random number between 10 - 20

        return ThreadLocalRandom.current().nextInt(3, 5);

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

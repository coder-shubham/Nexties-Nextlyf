package com.globalhackathon.nextlyf.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.globalhackathon.nextlyf.R;
import com.globalhackathon.nextlyf.databinding.CarouselPrivateBinding;
import com.globalhackathon.nextlyf.databinding.ChatItemBinding;

public class CommunityPrivateChatAdapter extends RecyclerView.Adapter {


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item, parent, false);
        return new CommunityPrivateChatAdapter.CommunityPrivateChatViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        final CommunityPrivateChatViewHolder viewHolder = (CommunityPrivateChatViewHolder) holder;

        if(position == 0){
            viewHolder.chatItemBinding.chatName.setText("John Doe");
            viewHolder.chatItemBinding.chatMessage.setText("Hello! How are you?");
            viewHolder.chatItemBinding.chatTime.setText("10:00 AM");
            viewHolder.chatItemBinding.messageCount.setText("2");
            viewHolder.chatItemBinding.chatAvatar.setImageResource(R.drawable.download);
        } else if(position == 1){
            viewHolder.chatItemBinding.chatName.setText("Rohit");
            viewHolder.chatItemBinding.chatMessage.setText("Hey! Do you want to join game?");
            viewHolder.chatItemBinding.chatTime.setText("10:05 AM");
            viewHolder.chatItemBinding.messageCount.setText("1");
            viewHolder.chatItemBinding.chatAvatar.setImageResource(R.drawable.download_1);
        } else if(position == 2){
            viewHolder.chatItemBinding.chatName.setText("Sky");
            viewHolder.chatItemBinding.chatMessage.setText("I am good too! Thanks for asking.");
            viewHolder.chatItemBinding.chatTime.setText("10:10 AM");
            viewHolder.chatItemBinding.messageCount.setText("3");
            viewHolder.chatItemBinding.chatAvatar.setImageResource(R.drawable.download_2);
        } else if(position == 3){
            viewHolder.chatItemBinding.chatName.setText("Jack");
            viewHolder.chatItemBinding.chatMessage.setText("Let's meet tmr at Kitchen");
            viewHolder.chatItemBinding.chatTime.setText("10:00 AM");
            viewHolder.chatItemBinding.messageCount.setText("2");
            viewHolder.chatItemBinding.chatAvatar.setImageResource(R.drawable.download_3);
        } else if(position == 4){
            viewHolder.chatItemBinding.chatName.setText("Vivek");
            viewHolder.chatItemBinding.chatMessage.setText("Can we have a call at 6?");
            viewHolder.chatItemBinding.chatTime.setText("10:05 AM");
            viewHolder.chatItemBinding.messageCount.setText("1");
            viewHolder.chatItemBinding.chatAvatar.setImageResource(R.drawable.download_1);
        } else if(position == 5){
            viewHolder.chatItemBinding.chatName.setText("Manish");
            viewHolder.chatItemBinding.chatMessage.setText("I am good too! Thanks for asking.");
            viewHolder.chatItemBinding.chatTime.setText("10:10 AM");
            viewHolder.chatItemBinding.messageCount.setText("4");
            viewHolder.chatItemBinding.chatAvatar.setImageResource(R.drawable.download_2);
        } else if(position == 6){
            viewHolder.chatItemBinding.chatName.setText("Christian");
            viewHolder.chatItemBinding.chatMessage.setText("Hello! are you coming?");
            viewHolder.chatItemBinding.chatTime.setText("10:00 AM");
            viewHolder.chatItemBinding.messageCount.setText("2");
            viewHolder.chatItemBinding.chatAvatar.setImageResource(R.drawable.download_3);
        } else if(position == 7){
            viewHolder.chatItemBinding.chatName.setText("Deepak");
            viewHolder.chatItemBinding.chatMessage.setText("Let's call our group for next trip.");
            viewHolder.chatItemBinding.chatTime.setText("10:05 AM");
            viewHolder.chatItemBinding.messageCount.setText("1");
            viewHolder.chatItemBinding.chatAvatar.setImageResource(R.drawable.download_4);
        } else if(position == 8) {
            viewHolder.chatItemBinding.chatName.setText("Rohan");
            viewHolder.chatItemBinding.chatMessage.setText("Let's discuss it in depth.");
            viewHolder.chatItemBinding.chatTime.setText("10:10 AM");
            viewHolder.chatItemBinding.messageCount.setText("7");
            viewHolder.chatItemBinding.chatAvatar.setImageResource(R.drawable.download_5);
        }

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class CommunityPrivateChatViewHolder extends RecyclerView.ViewHolder {

        ChatItemBinding chatItemBinding;

        public CommunityPrivateChatViewHolder(@NonNull View itemView) {
            super(itemView);

            chatItemBinding = ChatItemBinding.bind(itemView);
        }
    }
}

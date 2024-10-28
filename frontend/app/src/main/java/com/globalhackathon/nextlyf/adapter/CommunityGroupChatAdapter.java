package com.globalhackathon.nextlyf.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.globalhackathon.nextlyf.R;
import com.globalhackathon.nextlyf.databinding.ChatItemBinding;
import com.globalhackathon.nextlyf.listeners.OnItemClickListener;

public class CommunityGroupChatAdapter extends RecyclerView.Adapter {


    private OnItemClickListener listener;

    public CommunityGroupChatAdapter(){
    }

    public CommunityGroupChatAdapter(OnItemClickListener listener){
        this.listener = listener;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item, parent, false);
        return new CommunityGroupChatAdapter.CommunityGroupChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        final CommunityGroupChatViewHolder viewHolder = (CommunityGroupChatViewHolder) holder;

        if(position == 0){
            viewHolder.chatItemBinding.chatName.setText("Fitness Warriors - Fitness Enthusiasts");
            viewHolder.chatItemBinding.chatMessage.setText("Hey lets plan something for this weekend.");
            viewHolder.chatItemBinding.chatTime.setText("10:00 AM");
            viewHolder.chatItemBinding.messageCount.setText("2");
            viewHolder.chatItemBinding.chatAvatar.setImageResource(R.drawable.fitness1);
            viewHolder.bind(listener);

        } else if(position == 1){
            viewHolder.chatItemBinding.chatName.setText("Serene Souls - Yoga & Wellbeing ");
            viewHolder.chatItemBinding.chatMessage.setText("Hey! Do you want to join game?");
            viewHolder.chatItemBinding.chatTime.setText("10:05 AM");
            viewHolder.chatItemBinding.messageCount.setText("1");
            viewHolder.chatItemBinding.chatAvatar.setImageResource(R.drawable.yoga1);
        } else if(position == 2){
            viewHolder.chatItemBinding.chatName.setText("Trendy Threads - Fashion");
            viewHolder.chatItemBinding.chatMessage.setText("I am good too! Thanks for asking.");
            viewHolder.chatItemBinding.chatTime.setText("10:10 AM");
            viewHolder.chatItemBinding.messageCount.setText("3");
            viewHolder.chatItemBinding.chatAvatar.setImageResource(R.drawable.fashion1);
        } else if(position == 3){
            viewHolder.chatItemBinding.chatName.setText("Foodie Fiesta - Cooking/Food");
            viewHolder.chatItemBinding.chatMessage.setText("Let's meet tmr at Kitchen");
            viewHolder.chatItemBinding.chatTime.setText("10:00 AM");
            viewHolder.chatItemBinding.messageCount.setText("2");
            viewHolder.chatItemBinding.chatAvatar.setImageResource(R.drawable.cookingfood);
        } else if(position == 4){
            viewHolder.chatItemBinding.chatName.setText("Wanderlust Nomads - Travel");
            viewHolder.chatItemBinding.chatMessage.setText("Can we have a call at 6?");
            viewHolder.chatItemBinding.chatTime.setText("10:05 AM");
            viewHolder.chatItemBinding.messageCount.setText("1");
            viewHolder.chatItemBinding.chatAvatar.setImageResource(R.drawable.travel1);
        } else if(position == 5){
            viewHolder.chatItemBinding.chatName.setText("Artistic Aesthetics - Art & Design");
            viewHolder.chatItemBinding.chatMessage.setText("I am good too! Thanks for asking.");
            viewHolder.chatItemBinding.chatTime.setText("10:10 AM");
            viewHolder.chatItemBinding.messageCount.setText("4");
            viewHolder.chatItemBinding.chatAvatar.setImageResource(R.drawable.art1);
        } else if(position == 6){
            viewHolder.chatItemBinding.chatName.setText("Tech Titans - Technology & Gadgets");
            viewHolder.chatItemBinding.chatMessage.setText("Hello! are you coming?");
            viewHolder.chatItemBinding.chatTime.setText("10:00 AM");
            viewHolder.chatItemBinding.messageCount.setText("2");
            viewHolder.chatItemBinding.chatAvatar.setImageResource(R.drawable.technology1);
        } else if(position == 7){
            viewHolder.chatItemBinding.chatName.setText("Stock Market Mavericks - Finance/Investment");
            viewHolder.chatItemBinding.chatMessage.setText("Let's call our group for next trip.");
            viewHolder.chatItemBinding.chatTime.setText("10:05 AM");
            viewHolder.chatItemBinding.messageCount.setText("1");
            viewHolder.chatItemBinding.chatAvatar.setImageResource(R.drawable.finance1);
        } else if(position == 8) {
            viewHolder.chatItemBinding.chatName.setText("Bookworm Buddies - Reading Club");
            viewHolder.chatItemBinding.chatMessage.setText("Let's discuss it in depth.");
            viewHolder.chatItemBinding.chatTime.setText("10:10 AM");
            viewHolder.chatItemBinding.messageCount.setText("7");
            viewHolder.chatItemBinding.chatAvatar.setImageResource(R.drawable.reading1);
        }

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class CommunityGroupChatViewHolder extends RecyclerView.ViewHolder {

        ChatItemBinding chatItemBinding;

        public CommunityGroupChatViewHolder(@NonNull View itemView) {
            super(itemView);

            chatItemBinding = ChatItemBinding.bind(itemView);
        }

        public void bind(OnItemClickListener listener){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("CommunityGroupChatAdapter..Listener", "onClick" );
                    if(listener != null) {
                        Log.i("CommunityGroupChatAdapter..Listener", "onItemClick" );
                        listener.onItemClick(getAdapterPosition());
                    }else {
                        Log.i("CommunityGroupChatAdapter..Listener", "null");
                    }
                }
            });
        }
    }
}

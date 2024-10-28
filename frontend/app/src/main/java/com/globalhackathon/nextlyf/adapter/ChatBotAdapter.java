package com.globalhackathon.nextlyf.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.globalhackathon.nextlyf.R;
import com.globalhackathon.nextlyf.databinding.ChatItemBotBinding;
import com.globalhackathon.nextlyf.databinding.ChatItemBotCarouselBinding;
import com.globalhackathon.nextlyf.databinding.ChatItemUserBinding;
import com.globalhackathon.nextlyf.databinding.RoomItem2Binding;
import com.globalhackathon.nextlyf.model.ChatMessage;

import java.util.List;

public class ChatBotAdapter extends RecyclerView.Adapter {

    private List<ChatMessage> messagesList;

    private int BOT_MESSAGE = 0;
    private int USER_MESSAGE = 1;
    private int BOT_ROOM_LIST = 2;

    public ChatBotAdapter(List<ChatMessage> messagesList) {
        this.messagesList = messagesList;
    }

    @Override
    public int getItemViewType(int position) {
        ChatMessage message = messagesList.get(position);
        if (!message.getIsBot()) {
            return USER_MESSAGE;
        } else if(message.getIsBot() && message.getIsRoomObject()) {
            return BOT_ROOM_LIST;
        }else {
            return BOT_MESSAGE;
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == BOT_MESSAGE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_bot, parent, false);
            return new ChatBotViewHolder(view);
        } else if(viewType == USER_MESSAGE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_user, parent, false);
            return new UserViewHolder(view);
        } else if(viewType == BOT_ROOM_LIST) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_bot_carousel, parent, false);
            return new ChatBotRoomViewHolder(view);
        }else
            throw new IllegalArgumentException("Invalid view type");
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ChatMessage message = messagesList.get(position);

        if(holder instanceof ChatBotViewHolder) {
            ((ChatBotViewHolder) holder).bind(message);
        } else if(holder instanceof UserViewHolder){
            ((UserViewHolder) holder).bind(message);
        } else if (holder instanceof ChatBotRoomViewHolder) {
            Log.i("RoomAdapter-Bind", "onBindViewHolder");
            ((ChatBotRoomViewHolder) holder).bind();
        }

    }

    @Override
    public int getItemCount() {
        return messagesList.size();
    }

    public class ChatBotViewHolder extends RecyclerView.ViewHolder {

        ChatItemBotBinding binding;
        public ChatBotViewHolder(@NonNull View itemView) {
            super(itemView);

            binding = ChatItemBotBinding.bind(itemView);
        }

        public void bind(ChatMessage message) {
            binding.textViewMessageBot.setText(message.getText());
        }
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {

        ChatItemUserBinding binding;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            binding = ChatItemUserBinding.bind(itemView);
        }

        public void bind(ChatMessage message) {
            binding.textViewMessageUser.setText(message.getText());
        }
    }

    public class ChatBotRoomViewHolder extends RecyclerView.ViewHolder{

        ChatItemBotCarouselBinding binding;

        public ChatBotRoomViewHolder(@NonNull View itemView) {
            super(itemView);

            Log.i("RoomAdapter-Bind", "ChatBotRoomViewHolder");

            binding = ChatItemBotCarouselBinding.bind(itemView);
        }

        public void bind() {
            RoomAdapter roomAdapter = new RoomAdapter();

            LinearLayoutManager layoutManager = new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false);
            binding.recyclerViewCards.setLayoutManager(layoutManager);
            binding.recyclerViewCards.setAdapter(roomAdapter);
        }
    }

}

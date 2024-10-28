package com.globalhackathon.nextlyf.adapter;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.globalhackathon.nextlyf.R;
import com.globalhackathon.nextlyf.databinding.ChatItemBotBinding;
import com.globalhackathon.nextlyf.databinding.ChatItemBotCarouselBinding;
import com.globalhackathon.nextlyf.databinding.ChatItemUserBinding;
import com.globalhackathon.nextlyf.model.ChatMessage;
import com.globalhackathon.nextlyf.model.GroupChatMessage;
import com.globalhackathon.nextlyf.model.GroupMessage;

import java.util.List;

public class GroupChatBotAdapter extends RecyclerView.Adapter {


    private GroupChatMessage groupChatMessage;

    private int BOT_MESSAGE = 0;
    private int USER_MESSAGE = 1;
    private int BOT_EVENT_LIST = 2;
    private int BOT_GROUP_MESSAGE = 3;

    private EventAdapter eventAdapter;


    public GroupChatBotAdapter(GroupChatMessage groupChatMessage) {
        this.groupChatMessage = groupChatMessage;
    }

    public GroupChatBotAdapter() {

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        eventAdapter = new EventAdapter();

        if (viewType == BOT_MESSAGE || viewType == BOT_GROUP_MESSAGE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_bot, parent, false);
            return new GroupChatBotAdapter.GroupBotViewHolder(view, viewType);
        } else if(viewType == USER_MESSAGE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_user, parent, false);
            return new GroupChatBotAdapter.PersonalUserViewHolder(view);
        } else if(viewType == BOT_EVENT_LIST) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_bot_carousel, parent, false);
            return new GroupChatBotAdapter.GroupEventViewHolder(view);
        }else
            throw new IllegalArgumentException("Invalid view type");

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        GroupMessage message = groupChatMessage.getMessages().get(position);

        if(holder instanceof GroupChatBotAdapter.GroupBotViewHolder) {
            ((GroupChatBotAdapter.GroupBotViewHolder) holder).bind(message.getText());
        } else if(holder instanceof GroupChatBotAdapter.PersonalUserViewHolder){
            ((GroupChatBotAdapter.PersonalUserViewHolder) holder).bind(message.getText());
        } else if (holder instanceof GroupChatBotAdapter.GroupEventViewHolder) {
            ((GroupChatBotAdapter.GroupEventViewHolder) holder).bind();
        }

    }


    public void setGroupChatMessage(GroupChatMessage groupChatMessage) {
        this.groupChatMessage = groupChatMessage;
    }

    public GroupChatMessage getGroupChatMessage() {
        return groupChatMessage;
    }


    @Override
    public int getItemViewType(int position) {
        GroupMessage message = groupChatMessage.getMessages().get(position);
        if (message.getType().startsWith("user")) {
            return BOT_GROUP_MESSAGE;
        } else if(message.getType().equalsIgnoreCase("newUser")) {
            return USER_MESSAGE;
        }else if(message.getType().equalsIgnoreCase("bot")) {
            return BOT_MESSAGE;
        } else if (message.getType().equalsIgnoreCase("event")) {
            return BOT_EVENT_LIST;
        } else {
            return -1;

        }
    }

    @Override
    public int getItemCount() {
        return groupChatMessage.getMessages().size();
    }

    public class GroupBotViewHolder extends RecyclerView.ViewHolder {

        ChatItemBotBinding binding;
        public GroupBotViewHolder(@NonNull View itemView, int viewType) {
            super(itemView);

            binding = ChatItemBotBinding.bind(itemView);

            if (viewType == BOT_GROUP_MESSAGE) {
                Drawable drawable = ContextCompat.getDrawable(itemView.getContext(), R.drawable.ic_baseline_people_alt_24);
                binding.textViewMessageBot.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
            }
        }

        public void bind(String message) {
            binding.textViewMessageBot.setText(message);
        }
    }

    public class PersonalUserViewHolder extends RecyclerView.ViewHolder {

        ChatItemUserBinding binding;
        public PersonalUserViewHolder(@NonNull View itemView) {
            super(itemView);

            binding = ChatItemUserBinding.bind(itemView);
        }

        public void bind(String message) {
            binding.textViewMessageUser.setText(message);
        }
    }

    public class GroupEventViewHolder extends RecyclerView.ViewHolder{

        ChatItemBotCarouselBinding binding;

        public GroupEventViewHolder(@NonNull View itemView) {
            super(itemView);

            Log.i("RoomAdapter-Bind", "ChatBotRoomViewHolder");

            binding = ChatItemBotCarouselBinding.bind(itemView);
        }

        public void bind() {
            LinearLayoutManager layoutManager = new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false);
            binding.recyclerViewCards.setLayoutManager(layoutManager);
            binding.recyclerViewCards.setAdapter(eventAdapter);
        }
    }


}

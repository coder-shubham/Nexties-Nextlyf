package com.globalhackathon.nextlyf.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.globalhackathon.nextlyf.R;
import com.globalhackathon.nextlyf.api.APIManager;
import com.globalhackathon.nextlyf.databinding.ChatItemBotBinding;
import com.globalhackathon.nextlyf.databinding.ChatItemBotCarouselBinding;
import com.globalhackathon.nextlyf.databinding.ChatItemUserBinding;
import com.globalhackathon.nextlyf.databinding.RoomItem2Binding;
import com.globalhackathon.nextlyf.listeners.PaymentConfirmationListener;
import com.globalhackathon.nextlyf.listeners.RecommendationListener;
import com.globalhackathon.nextlyf.model.ChatMessage;
import com.globalhackathon.nextlyf.model.RecommendationRequest;
import com.globalhackathon.nextlyf.model.Rooms;
import com.globalhackathon.nextlyf.model.UserSignUpData;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.type.DateTime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

public class ChatBotAdapter extends RecyclerView.Adapter {

    private List<ChatMessage> messagesList;

    private int BOT_MESSAGE = 0;
    private int USER_MESSAGE = 1;
    private int BOT_ROOM_LIST = 2;

    boolean roomPreferencesMessage = false;

    private UserSignUpData userSignUpData;
    private RoomAdapter roomAdapterObj;

    private APIManager apiManager;

    PaymentConfirmationListener paymentConfirmationListener;

    public ChatBotAdapter(List<ChatMessage> messagesList) {
        this.messagesList = messagesList;
    }

    public ChatBotAdapter(List<ChatMessage> messagesList, UserSignUpData userSignUpData) {
        this.messagesList = messagesList;
        this.userSignUpData = userSignUpData;
    }

    public void setRoomAdapterObj(RoomAdapter roomAdapterObj) {
        this.roomAdapterObj = roomAdapterObj;
        Log.d("ChatBotRoom", "Room Adapter Set");

    }

    public RoomAdapter getRoomAdapter() {
        return roomAdapterObj;
    }

    public void setPaymentConfirmationListener(PaymentConfirmationListener paymentConfirmationListener) {
        this.paymentConfirmationListener = paymentConfirmationListener;
    }

    public PaymentConfirmationListener getPaymentConfirmationListener() {
        return paymentConfirmationListener;
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

        if(apiManager == null){
            apiManager = new APIManager(APIManager.ML_BASE_URL);
        }

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
            ((ChatBotRoomViewHolder) holder).bind(roomAdapterObj != null ? roomAdapterObj :
                    new RoomAdapter());
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

        public void bind(RoomAdapter roomAdapter) {
            Log.d("ChatBotRoom", roomAdapter.getFromBot().toString());
            LinearLayoutManager layoutManager = new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false);
            binding.recyclerViewCards.setLayoutManager(layoutManager);
            binding.recyclerViewCards.setAdapter(roomAdapter);
        }
    }

}

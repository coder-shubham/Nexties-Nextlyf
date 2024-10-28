package com.globalhackathon.nextlyf.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.globalhackathon.nextlyf.R;
import com.globalhackathon.nextlyf.databinding.ChatItemBinding;

public class CommunityGroupChatAdapter extends RecyclerView.Adapter {


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item, parent, false);
        return new CommunityGroupChatAdapter.CommunityGroupChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

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
    }
}

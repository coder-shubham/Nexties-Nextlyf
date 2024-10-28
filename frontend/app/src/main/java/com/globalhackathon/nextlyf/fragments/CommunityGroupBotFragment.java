package com.globalhackathon.nextlyf.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.globalhackathon.nextlyf.adapter.ChatBotAdapter;
import com.globalhackathon.nextlyf.adapter.GroupChatBotAdapter;
import com.globalhackathon.nextlyf.api.APIManager;
import com.globalhackathon.nextlyf.databinding.BottomSheetChatBinding;
import com.globalhackathon.nextlyf.model.ChatMessage;
import com.globalhackathon.nextlyf.model.GroupChatMessage;
import com.globalhackathon.nextlyf.model.GroupMessage;
import com.globalhackathon.nextlyf.utils.SpaceItemDecoration;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class CommunityGroupBotFragment extends BottomSheetDialogFragment {

    BottomSheetChatBinding binding;

    private GroupChatMessage groupChatMessage;

    private FirebaseFirestore db;

    GroupChatBotAdapter groupChatBotAdapter;


    private GroupChatMessage subGroupChatMessageFirst;
    private GroupChatMessage subGroupChatMessageSecond;





    public CommunityGroupBotFragment() {
        // Required empty public constructor
    }
    public CommunityGroupBotFragment(GroupChatMessage groupChatMessage) {
        this.groupChatMessage = groupChatMessage;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = BottomSheetChatBinding.inflate(inflater);
        db = FirebaseFirestore.getInstance();
        subGroupChatMessageFirst = new GroupChatMessage();
        subGroupChatMessageSecond = new GroupChatMessage();

        //Enter 8 objects from GroupChatMessage to subGroupChatMessageFirst
        for(int i = 0; i < 9; i++){
            //Check if message list is null then initialize and then enter
            if(subGroupChatMessageFirst.getMessages() == null){
                subGroupChatMessageFirst.setMessages(new ArrayList<>());
            }
            subGroupChatMessageFirst.getMessages().add(groupChatMessage.getMessages().get(i));
        }
        groupChatBotAdapter = new GroupChatBotAdapter(subGroupChatMessageFirst);

        binding.recyclerViewChat.setAdapter(groupChatBotAdapter);
        binding.recyclerViewChat.addItemDecoration(new SpaceItemDecoration(16));
        binding.recyclerViewChat.setLayoutManager(new LinearLayoutManager(getContext()));

        binding.chatTextLayout.setVisibility(View.GONE);
        binding.chatTextLayout.setAlpha(0f);
        binding.chatTextLayout.animate().alpha(1f).setDuration(300);
        binding.chatListeningLayout.setVisibility(View.GONE);
        binding.chatJoiningLayout.setVisibility(View.VISIBLE);

        binding.buttonJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.chatJoiningLayout.setVisibility(View.GONE);
                binding.chatTextLayout.setVisibility(View.VISIBLE);


                subGroupChatMessageFirst.getMessages().add(groupChatMessage.getMessages().get(9));
                groupChatBotAdapter.notifyItemInserted(subGroupChatMessageFirst.getMessages().size() - 1);
                binding.recyclerViewChat.scrollToPosition(subGroupChatMessageFirst.getMessages().size() - 1);
                simulateOtherConversation(10, 2000);
            }
        });

        binding.buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = binding.editTextMessage.getText().toString();
                if(!message.isEmpty()){
                    subGroupChatMessageFirst.getMessages().add(new GroupMessage(message, "newUser"));
                    groupChatBotAdapter.notifyItemInserted(subGroupChatMessageFirst.getMessages().size() - 1);
                    binding.recyclerViewChat.scrollToPosition(subGroupChatMessageFirst.getMessages().size() - 1);
                    binding.editTextMessage.setText("");
                    simulateConversationWithDelay(13, 2000);
                }
            }
        });


        return binding.getRoot();
    }

    private void simulateOtherConversation(int index, int delayMillis)  {

        if (index >= 12) {
            // Stop if we've processed all messages
            return;
        }

        // Post a delayed message to simulate message appearance after delayMillis
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            // Add the current message to the conversation
            subGroupChatMessageFirst.getMessages().add(groupChatMessage.getMessages().get(index));
            groupChatBotAdapter.notifyItemInserted(subGroupChatMessageFirst.getMessages().size() - 1);
            binding.recyclerViewChat.scrollToPosition(subGroupChatMessageFirst.getMessages().size() - 1);

            // Recursively call the next message with the same delay
            simulateOtherConversation(index + 1, delayMillis);

        }, delayMillis);

    }

    private void simulateConversationWithDelay(int index, int delayMillis) {
        if (index >= groupChatMessage.getMessages().size()) {
            // Stop if we've processed all messages
            return;
        }

        // Post a delayed message to simulate message appearance after delayMillis
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            // Add the current message to the conversation

            if(groupChatMessage.getMessages().get(index).getType().equalsIgnoreCase("event")){
                subGroupChatMessageFirst.getMessages().add(new GroupMessage(groupChatMessage.getMessages().get(index).getText(), "bot"));
                groupChatBotAdapter.notifyItemInserted(subGroupChatMessageFirst.getMessages().size() - 1);
                binding.recyclerViewChat.scrollToPosition(subGroupChatMessageFirst.getMessages().size() - 1);
            }
            subGroupChatMessageFirst.getMessages().add(groupChatMessage.getMessages().get(index));
            groupChatBotAdapter.notifyItemInserted(subGroupChatMessageFirst.getMessages().size() - 1);
            binding.recyclerViewChat.scrollToPosition(subGroupChatMessageFirst.getMessages().size() - 1);

            // Recursively call the next message with the same delay
            simulateConversationWithDelay(index + 1, delayMillis);

        }, delayMillis);
    }

}

package com.globalhackathon.nextlyf.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.globalhackathon.nextlyf.adapter.CommunityGroupChatAdapter;
import com.globalhackathon.nextlyf.adapter.CommunityGroupEventHighlightAdapter;
import com.globalhackathon.nextlyf.api.APIManager;
import com.globalhackathon.nextlyf.databinding.CommunityFragmentBinding;
import com.globalhackathon.nextlyf.databinding.CommunityGroupFragmentBinding;
import com.globalhackathon.nextlyf.listeners.OnItemClickListener;
import com.globalhackathon.nextlyf.model.GroupChatMessage;
import com.globalhackathon.nextlyf.model.GroupMessageListener;
import com.globalhackathon.nextlyf.model.UserDetails;
import com.globalhackathon.nextlyf.utils.SpaceItemDecoration;
import com.google.android.material.carousel.CarouselLayoutManager;
import com.google.android.material.carousel.CarouselSnapHelper;
import com.google.android.material.carousel.HeroCarouselStrategy;

import java.util.UUID;

public class CommunityGroupFragment extends Fragment implements OnItemClickListener, GroupMessageListener {

    CommunityGroupFragmentBinding communityGroupFragmentBinding;

    GroupMessageListener groupMessageListener;

    private APIManager apiManager;

    private CommunityGroupChatAdapter communityGroupChatAdapter;


    public CommunityGroupFragment() {
        // Required empty public constructor
    }




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        communityGroupFragmentBinding = CommunityGroupFragmentBinding.inflate(inflater);
        communityGroupChatAdapter = new CommunityGroupChatAdapter(this);

        communityGroupFragmentBinding.userGroupChatRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        communityGroupFragmentBinding.userGroupChatRecyclerView.setAdapter(communityGroupChatAdapter);
        communityGroupFragmentBinding.userGroupChatRecyclerView.addItemDecoration(new SpaceItemDecoration(16));


        apiManager = new APIManager("custom");
        communityGroupFragmentBinding.userGroupEventRecyclerView.setLayoutManager(
                new CarouselLayoutManager(new HeroCarouselStrategy()));
        CarouselSnapHelper carouselSnapHelper = new CarouselSnapHelper();
        carouselSnapHelper.attachToRecyclerView(communityGroupFragmentBinding.userGroupEventRecyclerView);
        communityGroupFragmentBinding.userGroupEventRecyclerView.setAdapter(new CommunityGroupEventHighlightAdapter());

        return communityGroupFragmentBinding.getRoot();

    }

    @Override
    public void onItemClick(int position) {

        Log.d("CommunityGroupFragment", "onItemClick: " + position);
        apiManager.getGroupMessage(this);

//        showBottomSheetDialog();
    }

    public void showBottomSheetDialog() {

    }

    @Override
    public void onGroupMessageReceived(GroupChatMessage groupChatMessage) {
        String sessionId = UUID.randomUUID().toString();

        CommunityGroupBotFragment communityGroupFragment = new CommunityGroupBotFragment(groupChatMessage);
        communityGroupFragment.show(getParentFragmentManager(), "ModelBottomSheet");
    }

    @Override
    public void onGroupMessageError(String error) {

    }
}

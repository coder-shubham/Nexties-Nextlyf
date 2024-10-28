package com.globalhackathon.nextlyf.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.globalhackathon.nextlyf.adapter.CommunityGroupChatAdapter;
import com.globalhackathon.nextlyf.adapter.CommunityGroupEventHighlightAdapter;
import com.globalhackathon.nextlyf.databinding.CommunityFragmentBinding;
import com.globalhackathon.nextlyf.databinding.CommunityGroupFragmentBinding;
import com.globalhackathon.nextlyf.utils.SpaceItemDecoration;
import com.google.android.material.carousel.CarouselLayoutManager;
import com.google.android.material.carousel.CarouselSnapHelper;
import com.google.android.material.carousel.HeroCarouselStrategy;

public class CommunityGroupFragment extends Fragment {

    CommunityGroupFragmentBinding communityGroupFragmentBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        communityGroupFragmentBinding = CommunityGroupFragmentBinding.inflate(inflater);

        communityGroupFragmentBinding.userGroupChatRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        communityGroupFragmentBinding.userGroupChatRecyclerView.setAdapter(new CommunityGroupChatAdapter());
        communityGroupFragmentBinding.userGroupChatRecyclerView.addItemDecoration(new SpaceItemDecoration(16));


        communityGroupFragmentBinding.userGroupEventRecyclerView.setLayoutManager(
                new CarouselLayoutManager(new HeroCarouselStrategy()));
        CarouselSnapHelper carouselSnapHelper = new CarouselSnapHelper();
        carouselSnapHelper.attachToRecyclerView(communityGroupFragmentBinding.userGroupEventRecyclerView);
        communityGroupFragmentBinding.userGroupEventRecyclerView.setAdapter(new CommunityGroupEventHighlightAdapter());

        return communityGroupFragmentBinding.getRoot();

    }
}

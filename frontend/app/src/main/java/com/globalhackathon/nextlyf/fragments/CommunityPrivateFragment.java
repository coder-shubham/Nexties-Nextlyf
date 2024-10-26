package com.globalhackathon.nextlyf.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager2.widget.ViewPager2;

import com.globalhackathon.nextlyf.adapter.CommunityPrivateChatAdapter;
import com.globalhackathon.nextlyf.adapter.CommunityPrivateHighlightAdapter;
import com.globalhackathon.nextlyf.databinding.CommunityPrivateFragmentBinding;
import com.globalhackathon.nextlyf.utils.CustomRecyclerView;
import com.globalhackathon.nextlyf.utils.SpaceItemDecoration;
import com.google.android.material.carousel.CarouselSnapHelper;

public class CommunityPrivateFragment extends Fragment {

    private ViewPager2 viewPager;


    public CommunityPrivateFragment() {
        // Required empty public constructor
    }

    public CommunityPrivateFragment(ViewPager2 viewPager) {
        this.viewPager = viewPager;
    }


    CommunityPrivateFragmentBinding communityPrivateFragmentBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        communityPrivateFragmentBinding = CommunityPrivateFragmentBinding.inflate(inflater);

        communityPrivateFragmentBinding.userChatRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        communityPrivateFragmentBinding.userChatRecyclerView.setAdapter(new CommunityPrivateChatAdapter());
        communityPrivateFragmentBinding.userChatRecyclerView.addItemDecoration(new SpaceItemDecoration(16));


        communityPrivateFragmentBinding.userHighlightRecyclerView.setViewPager2(viewPager);
        communityPrivateFragmentBinding.userHighlightRecyclerView.setAdapter(new CommunityPrivateHighlightAdapter());

        CarouselSnapHelper carouselSnapHelper = new CarouselSnapHelper();
        carouselSnapHelper.attachToRecyclerView(communityPrivateFragmentBinding.userHighlightRecyclerView);



        return communityPrivateFragmentBinding.getRoot();

    }
}

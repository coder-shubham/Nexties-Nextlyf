package com.globalhackathon.nextlyf.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.globalhackathon.nextlyf.adapter.CommunityWallFeedAdapter;
import com.globalhackathon.nextlyf.databinding.CommunityWallFragmentBinding;
import com.globalhackathon.nextlyf.utils.SpaceItemDecoration;

public class CommunityWallFragment extends Fragment {

    CommunityWallFragmentBinding communityWallFragmentBinding;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        communityWallFragmentBinding = CommunityWallFragmentBinding.inflate(inflater);

        communityWallFragmentBinding.communityWallRecyclerView.setAdapter(new CommunityWallFeedAdapter());
        communityWallFragmentBinding.communityWallRecyclerView.addItemDecoration(new SpaceItemDecoration(16));
        communityWallFragmentBinding.communityWallRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return communityWallFragmentBinding.getRoot();

    }
}

package com.globalhackathon.nextlyf.fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.globalhackathon.nextlyf.R;
import com.globalhackathon.nextlyf.adapter.CommunityStateAdapter;
import com.globalhackathon.nextlyf.databinding.CommunityFragmentBinding;
import com.google.android.material.tabs.TabLayoutMediator;

public class CommunityFragments extends Fragment {

    CommunityFragmentBinding communityFragmentBinding;


    public CommunityFragments() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        communityFragmentBinding = CommunityFragmentBinding.inflate(inflater, container, false);

        ViewPager2 viewPager = communityFragmentBinding.communityViewPager;

        viewPager.setAdapter(new CommunityStateAdapter(requireActivity(), viewPager));

        new TabLayoutMediator(communityFragmentBinding.communityTabLayout, viewPager,
                (tab, position) -> {
                    switch (position) {
                        case 0:
                            Log.d("CommunityFragments", "Wall");
                            tab.setText("Hub");
                            Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_hub);
                            tab.setIcon(drawable);
                            break;
                        case 1:
                            Log.d("CommunityFragments", "Private");
                            tab.setText("Nexus");
                            Drawable drawable2 = ContextCompat.getDrawable(getContext(), R.drawable.ic_nexus_new);
                            tab.setIcon(drawable2);
                            break;
                        case 2:
                            Log.d("CommunityFragments", "Group");
                            tab.setText("Circle");
                            Drawable drawable3 = ContextCompat.getDrawable(getContext(), R.drawable.ic_group);
                            tab.setIcon(drawable3);
                            break;
                    }
                }
        ).attach();

        return communityFragmentBinding.getRoot();

    }


}

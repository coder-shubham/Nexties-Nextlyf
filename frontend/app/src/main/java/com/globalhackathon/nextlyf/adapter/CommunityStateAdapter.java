package com.globalhackathon.nextlyf.adapter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.globalhackathon.nextlyf.fragments.CommunityGroupFragment;
import com.globalhackathon.nextlyf.fragments.CommunityPrivateFragment;
import com.globalhackathon.nextlyf.fragments.CommunityWallFragment;
import com.globalhackathon.nextlyf.fragments.UserIntroBasicFragment;
import com.globalhackathon.nextlyf.fragments.UserIntroFinalFragment;
import com.globalhackathon.nextlyf.fragments.UserIntroQueSetOneFragment;
import com.globalhackathon.nextlyf.fragments.UserIntroQueSetThreeFragment;
import com.globalhackathon.nextlyf.fragments.UserIntroQueSetTwoFragment;

public class CommunityStateAdapter extends FragmentStateAdapter {

    private ViewPager2 viewPager;
    public CommunityStateAdapter(@NonNull FragmentActivity fragmentActivity, ViewPager2 viewPager) {
        super(fragmentActivity);
        this.viewPager = viewPager;
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position) {
            case 0:
                return new CommunityWallFragment();
            case 1:
                return new CommunityPrivateFragment(viewPager);
            case 2:
                return new CommunityGroupFragment();
            default:
                Log.d("CommunityStateAdapter", "Invalid position");
                return new CommunityWallFragment();

        }

    }

    @Override
    public int getItemCount() {
        return 3;
    }
}

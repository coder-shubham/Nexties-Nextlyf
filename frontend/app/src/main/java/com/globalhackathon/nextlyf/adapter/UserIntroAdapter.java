package com.globalhackathon.nextlyf.adapter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.globalhackathon.nextlyf.fragments.UserIntroBasicFragment;
import com.globalhackathon.nextlyf.fragments.UserIntroFinalFragment;
import com.globalhackathon.nextlyf.fragments.UserIntroQueSetOneFragment;
import com.globalhackathon.nextlyf.fragments.UserIntroQueSetThreeFragment;
import com.globalhackathon.nextlyf.fragments.UserIntroQueSetTwoFragment;
import com.globalhackathon.nextlyf.model.UserSignUpData;

public class UserIntroAdapter extends FragmentStateAdapter {

    private UserSignUpData userSignUpData;

    public UserIntroAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        this.userSignUpData = new UserSignUpData();
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new UserIntroBasicFragment(userSignUpData);
            case 1:
                return new UserIntroQueSetOneFragment(userSignUpData);
            case 2:
                return new UserIntroQueSetTwoFragment(userSignUpData);
            case 3:
                return new UserIntroQueSetThreeFragment(userSignUpData);
            case 4:
                return new UserIntroFinalFragment(userSignUpData);
            default:
                Log.d("UserIntroAdapter", "Invalid position");
                return new UserIntroBasicFragment(userSignUpData);

        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public UserSignUpData getUserSignUpData() {
        return userSignUpData;
    }
}

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

import com.globalhackathon.nextlyf.adapter.CommunityWallFeedAdapter;
import com.globalhackathon.nextlyf.databinding.CommunityWallFragmentBinding;
import com.globalhackathon.nextlyf.model.CommunityWall;
import com.globalhackathon.nextlyf.model.Rooms;
import com.globalhackathon.nextlyf.utils.SpaceItemDecoration;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class CommunityWallFragment extends Fragment {

    CommunityWallFragmentBinding communityWallFragmentBinding;

    CommunityWallFeedAdapter communityWallFeedAdapter;
    private List<CommunityWall> communityWallList;

    private FirebaseFirestore db;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        communityWallFragmentBinding = CommunityWallFragmentBinding.inflate(inflater);
        communityWallList = new ArrayList<>();
        communityWallFeedAdapter = new CommunityWallFeedAdapter(communityWallList);
        db = FirebaseFirestore.getInstance();

        communityWallFragmentBinding.communityWallRecyclerView.setAdapter(communityWallFeedAdapter);
        communityWallFragmentBinding.communityWallRecyclerView.addItemDecoration(new SpaceItemDecoration(16));
        communityWallFragmentBinding.communityWallRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        getCommunityWallPosts();

        return communityWallFragmentBinding.getRoot();

    }

    private void getCommunityWallPosts() {
        db.collection("hub")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    Log.d("CommunityWallFragment", "Hub data loaded successfully QueryDocumentSnapshots: " +
                            queryDocumentSnapshots.size());
                    List<CommunityWall> tempList = new ArrayList<>(); // Temporary list to avoid main thread blocking
                    for (DocumentSnapshot document : queryDocumentSnapshots) {
                        CommunityWall wall = document.toObject(CommunityWall.class);
                        Log.d("CommunityWallFragment", "CommunityWall: " + wall.toString());
                        tempList.add(wall);
                    }
                    // Update RecyclerView on the main thread
                     getActivity().runOnUiThread(() -> {
                        communityWallList.clear();
                        communityWallList.addAll(tempList);
                        communityWallFeedAdapter.notifyDataSetChanged();
                    });


                })
                .addOnFailureListener(e -> {
                    // Handle any errors
                    Log.d("CommunityWallFragment", "Failed to load CommunityWall data: " + e.getMessage());
                });
    }


}

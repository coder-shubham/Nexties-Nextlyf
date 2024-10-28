package com.globalhackathon.nextlyf.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.globalhackathon.nextlyf.R;
import com.globalhackathon.nextlyf.databinding.ChatItemBotBinding;
import com.globalhackathon.nextlyf.databinding.WallItemBinding;
import com.globalhackathon.nextlyf.model.ChatMessage;
import com.globalhackathon.nextlyf.model.CommunityWall;
import com.google.gson.Gson;

import java.util.List;

public class CommunityWallFeedAdapter extends RecyclerView.Adapter {

    private List<CommunityWall> communityWallList;

    Gson gson;

    public CommunityWallFeedAdapter(){
    }

    public CommunityWallFeedAdapter(List<CommunityWall> communityWallList) {
        this.communityWallList = communityWallList;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wall_item, parent, false);
        gson = new Gson();
        return new CommunityWallFeedAdapter.CommunityWallFeedViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        final CommunityWallFeedViewHolder viewHolder = (CommunityWallFeedViewHolder) holder;

        if(communityWallList != null && !communityWallList.isEmpty()){
            CommunityWall communityWall = communityWallList.get(position);

            Log.d("CommunityWallFeedAdapter", "CommunityWall: " + gson.toJson(communityWall));

            viewHolder.wallItemBinding.username.setText(communityWall.getUserName());
            viewHolder.wallItemBinding.postContent.setText(communityWall.getUserPostText());
            viewHolder.wallItemBinding.userHandle.setText(communityWall.getUserType() != null &&
                    communityWall.getUserType().equals("Resident")
                    ? "lyf Resident" : "lyf Admin");
            viewHolder.wallItemBinding.postDate.setText(communityWall.getPostDate());

            Glide.with(viewHolder.wallItemBinding.userAvatar.getContext()).
                    load(communityWall.getUserPic()).
                    centerInside().
                    diskCacheStrategy(DiskCacheStrategy.RESOURCE).
                    placeholder(R.drawable.my_pic).
                    override(40,40).
                    into(viewHolder.wallItemBinding.userAvatar);

            if(communityWall.getUserPostImage() != null && !communityWall.getUserPostImage().isEmpty()){
                Glide.with(viewHolder.wallItemBinding.postImage.getContext()).
                        load(communityWall.getUserPostImage()).
                        centerCrop().
                        diskCacheStrategy(DiskCacheStrategy.RESOURCE).
                        placeholder(R.drawable.ic_group).
                        override(40,40).
                        into(viewHolder.wallItemBinding.postImage);
            }

        }



    }

    @Override
    public int getItemCount() {
        return communityWallList != null ? communityWallList.size() : 10;
    }

    public class CommunityWallFeedViewHolder extends RecyclerView.ViewHolder {

        WallItemBinding wallItemBinding;

        public CommunityWallFeedViewHolder(@NonNull View itemView) {
            super(itemView);

            wallItemBinding = WallItemBinding.bind(itemView);

        }
    }

}

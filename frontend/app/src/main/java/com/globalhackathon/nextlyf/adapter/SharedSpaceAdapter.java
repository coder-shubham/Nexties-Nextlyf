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
import com.globalhackathon.nextlyf.databinding.SharedSpaceItemBinding;
import com.globalhackathon.nextlyf.model.SharedSpace;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class SharedSpaceAdapter extends RecyclerView.Adapter{


    private List<SharedSpace> sharedSpaces;

    public SharedSpaceAdapter(){

    }

    public SharedSpaceAdapter(List<SharedSpace> sharedSpaces){
        this.sharedSpaces = sharedSpaces;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shared_space_item, parent, false);
        return new SharedSpaceAdapter.SharedSpaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Log.d("SharedSpaceAdapter-Bind", "onBindViewHolder");

        final SharedSpaceViewHolder viewHolder = (SharedSpaceViewHolder) holder;

        SharedSpace sharedSpace = sharedSpaces.get(position);

        viewHolder.binding.roomName.setText(sharedSpace.getSpaceName());

        viewHolder.binding.roomSize.setText(String.format("Max %s pax",
                sharedSpace.getSpaceCapacity()));

        viewHolder.binding.roomAmenitiesFirst.setText(sharedSpace.getSpaceMain() != null ? sharedSpace.getSpaceMain()
                .replace(",", " \u2022"):"Bond \u2022 Connect \u2022 Memory");

        viewHolder.binding.roomLocation.setText(sharedSpace.getSpaceLocation());

        viewHolder.binding.roomPriceLayout.setVisibility(View.GONE);

        Glide.with(viewHolder.binding.roomImage.getContext()).
                load(sharedSpace.getSpaceImage()).
                centerInside().
                diskCacheStrategy(DiskCacheStrategy.RESOURCE).
                placeholder(R.drawable.shared_space).
                override(200,100).
                into(viewHolder.binding.roomImage);

    }

    @Override
    public int getItemCount() {
        return sharedSpaces != null ? sharedSpaces.size():10;
    }

    public class SharedSpaceViewHolder extends RecyclerView.ViewHolder{

        SharedSpaceItemBinding binding;

        public SharedSpaceViewHolder(@NonNull View itemView) {
            super(itemView);

            Log.d("SharedSpaceAdapter-Bind", "SharedSpaceViewHolder");

            binding = SharedSpaceItemBinding.bind(itemView);

        }
    }
}

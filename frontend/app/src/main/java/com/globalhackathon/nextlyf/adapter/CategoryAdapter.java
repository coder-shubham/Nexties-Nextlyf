package com.globalhackathon.nextlyf.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.globalhackathon.nextlyf.R;
import com.globalhackathon.nextlyf.model.Category;
import com.globalhackathon.nextlyf.model.SearchResult;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter {

    private List<Category> categories;
    private Context context;

    // Maximum number of items to display when collapsed
    private static final int COLLAPSED_ITEM_COUNT = 2;

    public CategoryAdapter(Context context, List<Category> categories) {
        this.context = context;
        this.categories = categories;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_result_layout, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder.getClass() == CategoryViewHolder.class){
            Category category = categories.get(position);

            final CategoryViewHolder viewHolder = (CategoryViewHolder)holder;

            viewHolder.categoryTitleTextView.setText(category.getCategoryName());

            // Set up the inner RecyclerView
            List<SearchResult> displayItems;

            // Show limited items if collapsed, show all items if expanded
            if (category.isExpanded()) {
                displayItems = category.getSearchResults();
                viewHolder.expandCollapseIcon.setImageResource(R.drawable.ic_collapse); // Change to collapse icon
            } else {
                displayItems = category.getSearchResults().subList(0, Math.min(COLLAPSED_ITEM_COUNT, category.getSearchResults().size()));
                viewHolder.expandCollapseIcon.setImageResource(R.drawable.ic_expand); // Change to expand icon
            }

            SearchResultsAdapter searchResultsAdapter = new SearchResultsAdapter(context, displayItems);
            viewHolder.categoryItemsRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            viewHolder.categoryItemsRecyclerView.setAdapter(searchResultsAdapter);

            // Handle expand/collapse on icon click
            viewHolder.expandCollapseIcon.setOnClickListener(v -> {
                category.setExpanded(!category.isExpanded()); // Toggle the expanded state
                notifyItemChanged(position); // Notify the adapter to refresh this item
            });

        }


    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView categoryTitleTextView;
        RecyclerView categoryItemsRecyclerView;
        ImageView expandCollapseIcon;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryTitleTextView = itemView.findViewById(R.id.categoryTitleTextView);
            categoryItemsRecyclerView = itemView.findViewById(R.id.categoryItemsRecyclerView);
            expandCollapseIcon = itemView.findViewById(R.id.expandCollapseIcon);
        }
    }
}

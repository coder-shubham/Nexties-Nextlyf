package com.globalhackathon.nextlyf.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.globalhackathon.nextlyf.R;
import com.globalhackathon.nextlyf.model.SearchResult;

import java.util.List;

public class SearchResultsAdapter extends RecyclerView.Adapter {

    private List<SearchResult> searchResults;
    private Context context;

    public SearchResultsAdapter(Context context, List<SearchResult> searchResults) {
        this.context = context;
        this.searchResults = searchResults;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_search_result, parent, false);
        return new SearchResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        SearchResult searchResult = searchResults.get(position);

        final SearchResultViewHolder viewHolder = (SearchResultViewHolder)holder;

        viewHolder.titleTextView.setText(searchResult.getTitle());
        viewHolder.subtitleTextView.setText(searchResult.getSubtitle());
        viewHolder.ratingTextView.setText(String.valueOf(searchResult.getRating()));
    }

    @Override
    public int getItemCount() {
        return searchResults.size();
    }

    public class SearchResultViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, subtitleTextView, ratingTextView;

        public SearchResultViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.titleTextView);
            subtitleTextView = itemView.findViewById(R.id.subtitleTextView);
            ratingTextView = itemView.findViewById(R.id.ratingTextView);
        }
    }
}


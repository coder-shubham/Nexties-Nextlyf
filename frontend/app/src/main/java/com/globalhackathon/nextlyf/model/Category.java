package com.globalhackathon.nextlyf.model;

import java.util.List;

public class Category {

    private String categoryName;
    private List<SearchResult> searchResults;
    private boolean isExpanded;

    public Category(String categoryName, List<SearchResult> searchResults, boolean isExpanded) {
        this.categoryName = categoryName;
        this.searchResults = searchResults;
        this.isExpanded = isExpanded;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public List<SearchResult> getSearchResults() {
        return searchResults;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }
}


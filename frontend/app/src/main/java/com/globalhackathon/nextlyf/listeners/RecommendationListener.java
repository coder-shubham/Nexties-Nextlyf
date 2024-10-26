package com.globalhackathon.nextlyf.listeners;

import java.util.List;

public interface RecommendationListener {

    public void onRecommendationSuccess(List<String> response);

    public void onRecommendationFailure(String response);
}

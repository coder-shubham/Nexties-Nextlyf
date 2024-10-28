package com.globalhackathon.nextlyf.model;

import java.util.List;

public class RecommendationResponse {

    private List<String> recommendations;

    public RecommendationResponse(List<String> recommendations) {
        this.recommendations = recommendations;
    }

    public List<String> getRecommendations() {
        return recommendations;
    }
}

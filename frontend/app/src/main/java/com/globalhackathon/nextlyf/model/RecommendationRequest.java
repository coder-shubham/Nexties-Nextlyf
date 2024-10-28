package com.globalhackathon.nextlyf.model;

import com.google.gson.annotations.SerializedName;

public class RecommendationRequest {

    @SerializedName("user")
    private UserSignUpData userSignUpData;

    @SerializedName("recommendation_type")
    private String recommendationType;


    public enum RecommendationType {
        FRIEND, ROOM, EVENT, SOCIALSPACES
    }

    public RecommendationRequest(UserSignUpData userSignUpData, String recommendationType) {
        this.userSignUpData = userSignUpData;
        this.recommendationType = recommendationType;
    }

    public UserSignUpData getUserSignUpData() {
        return userSignUpData;
    }

    public String getRecommendationType() {
        return recommendationType;
    }

    public void setUserSignUpData(UserSignUpData userSignUpData) {
        this.userSignUpData = userSignUpData;
    }

    public void setRecommendationType(RecommendationType recommendationType) {
        this.recommendationType = recommendationType.name();
    }
}

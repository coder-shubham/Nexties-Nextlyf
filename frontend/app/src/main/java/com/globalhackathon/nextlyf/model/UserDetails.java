package com.globalhackathon.nextlyf.model;

import com.google.gson.annotations.SerializedName;

public class UserDetails {

    @SerializedName("name")
    private String name;

    @SerializedName("age_group")
    private String ageGroup;



    public UserDetails(String name, String ageGroup) {
        this.name = name;
        this.ageGroup = ageGroup;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAgeGroup() {
        return ageGroup;
    }

    public void setAgeGroup(){
        this.ageGroup = ageGroup;
    }

}

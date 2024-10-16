package com.globalhackathon.nextlyf.model;

import java.util.List;

public class UserSignUpData {

    private String name;
    private String email;
    private String mobile;
    private String dob;
    private String country;
    private String city;
    private String gender;
    private String language;
    private String referralCode;

    private String profession;
    private List<String> interests;
    private List<String> hobbies;


    public UserSignUpData() {
    }

    public UserSignUpData(String name, String email, String mobile, String dob,
                          String country, String city, String gender, String language,
                          String referralCode, String profession,
                          List<String> interests, List<String> hobbies) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.dob = dob;
        this.country = country;
        this.city = city;
        this.gender = gender;
        this.language = language;
        this.referralCode = referralCode;
        this.profession = profession;
        this.interests = interests;
        this.hobbies = hobbies;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public String getDob() {
        return dob;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getGender() {
        return gender;
    }

    public String getLanguage() {
        return language;
    }

    public String getReferralCode() {
        return referralCode;
    }

    public String getProfession() {
        return profession;
    }

    public List<String> getInterests() {
        return interests;
    }

    public List<String> getHobbies() {
        return hobbies;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public void setInterests(List<String> interests) {
        this.interests = interests;
    }

    public void setHobbies(List<String> hobbies) {
        this.hobbies = hobbies;
    }
}

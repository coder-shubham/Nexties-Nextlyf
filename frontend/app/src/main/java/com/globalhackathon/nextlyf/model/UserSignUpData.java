package com.globalhackathon.nextlyf.model;

import com.google.firebase.firestore.PropertyName;

public class UserSignUpData {

    private String userId;
    private String name;
    private String email;
    private Object mobile;
    private String dob;
    private String country;
    private String city;
    private String gender;
    private String language;
    private String referralCode;

    private String profession;
    private String fieldsOfInterest;
    private String hobbies;
    private String propertyLocation;
    private String userMainInterestHighlight;

    @PropertyName("profile_pic")
    private String profilePic;

    public UserSignUpData() {
    }

    public UserSignUpData(String userId, String name, String email, String mobile, String dob,
                          String country, String city, String gender, String language,
                          String referralCode, String profession, String fieldsOfInterest,
                          String hobbies, String propertyLocation, String userMainInterestHighlight,
                          String profilePic) {
        this.userId = userId;
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
        this.fieldsOfInterest = fieldsOfInterest;
        this.hobbies = hobbies;
        this.propertyLocation = propertyLocation;
        this.userMainInterestHighlight = userMainInterestHighlight;
        this.profilePic = profilePic;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }



    @PropertyName("mobile")
    public Object getMobile() {
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

    public String getFieldsOfInterest() {
        return fieldsOfInterest;
    }

    public String getHobbies() {
        return hobbies;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMobile(Object mobile) {

        if (mobile instanceof Long) {
            this.mobile = mobile;
        } else if (mobile instanceof String) {
            // Attempt to parse the string as a Long, if possible
            try {
                this.mobile = Long.parseLong((String) mobile);
            } catch (NumberFormatException e) {
                this.mobile = mobile; // Keep as String if parsing fails
            }
        } else {
            this.mobile = mobile; // Assign as is for other types
        }

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

    public void setFieldsOfInterest(String fieldsOfInterest) {
        this.fieldsOfInterest = fieldsOfInterest;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }

    public String getPropertyLocation() {
        return propertyLocation;
    }

    public void setPropertyLocation(String propertyLocation) {
        this.propertyLocation = propertyLocation;
    }

    public String getUserMainInterestHighlight() {
        return userMainInterestHighlight;
    }

    public void setUserMainInterestHighlight(String userMainInterestHighlight) {
        this.userMainInterestHighlight = userMainInterestHighlight;
    }

    @PropertyName("profile_pic")
    public String getProfilePic() {
        return profilePic;
    }


    @PropertyName("profile_pic")
    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }
}

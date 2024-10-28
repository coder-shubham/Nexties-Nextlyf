package com.globalhackathon.nextlyf.model;

import com.google.firebase.firestore.PropertyName;

public class CommunityWall {


    @PropertyName("profile_pic")
    private String userPic;
    @PropertyName("Admin_name")
    private String userName;

    @PropertyName("post")
    private String userPostText;

    @PropertyName("image")
    private String userPostImage;
    @PropertyName("Type")
    private String userType;

    @PropertyName("Date")
    private String postDate;

    private String postLike;
    private String postComment;
    private String postShare;
    private String postReport;

    public CommunityWall(){
    }

    public CommunityWall(String userPic, String userName, String userPostText, String userPostImage, String userType, String postDate, String postLike, String postComment, String postShare, String postReport) {
        this.userPic = userPic;
        this.userName = userName;
        this.userPostText = userPostText;
        this.userPostImage = userPostImage;
        this.userType = userType;
        this.postDate = postDate;
        this.postLike = postLike;
        this.postComment = postComment;
        this.postShare = postShare;
        this.postReport = postReport;
    }

    @PropertyName("profile_pic")
    public String getUserPic() {
        return userPic;
    }

    @PropertyName("profile_pic")
    public void setUserPic(String userPic) {
        this.userPic = userPic;
    }

    @PropertyName("Admin_name")
    public String getUserName() {
        return userName;
    }


    @PropertyName("Admin_name")
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @PropertyName("post")
    public String getUserPostText() {
        return userPostText;
    }

    @PropertyName("post")
    public void setUserPostText(String userPostText) {
        this.userPostText = userPostText;
    }


    @PropertyName("image")
    public String getUserPostImage() {
        return userPostImage;
    }

    @PropertyName("image")
    public void setUserPostImage(String userPostImage) {
        this.userPostImage = userPostImage;
    }

    @PropertyName("Type")
    public String getUserType() {
        return userType;
    }

    @PropertyName("Type")
    public void setUserType(String userType) {
        this.userType = userType;
    }

    @PropertyName("Date")
    public String getPostDate() {
        return postDate;
    }

    @PropertyName("Date")
    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public String getPostLike() {
        return postLike;
    }

    public void setPostLike(String postLike) {
        this.postLike = postLike;
    }

    public String getPostComment() {
        return postComment;
    }

    public void setPostComment(String postComment) {
        this.postComment = postComment;
    }

    public String getPostShare() {
        return postShare;
    }

    public void setPostShare(String postShare) {
        this.postShare = postShare;
    }

    public String getPostReport() {
        return postReport;
    }

    public void setPostReport(String postReport) {
        this.postReport = postReport;
    }
}

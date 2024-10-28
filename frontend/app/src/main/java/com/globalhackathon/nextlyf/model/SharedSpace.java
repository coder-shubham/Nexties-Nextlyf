package com.globalhackathon.nextlyf.model;

public class SharedSpace {

    private String spaceId;

    private String spaceName;

    private String spaceImage;

    private String spaceDesc;

    private Long spaceArea;

    private String spaceLocation;

    private Integer spaceCapacity;

    private Long spacePrice;
    private String spaceMain;

    public SharedSpace() {
    }

    public SharedSpace(String spaceName, String spaceDesc, Long spaceArea, String spaceLocation,
                       Integer spaceCapacity, Long spacePrice, String spaceMain, String spaceImage) {
        this.spaceName = spaceName;
        this.spaceDesc = spaceDesc;
        this.spaceArea = spaceArea;
        this.spaceLocation = spaceLocation;
        this.spaceCapacity = spaceCapacity;
        this.spacePrice = spacePrice;
        this.spaceMain = spaceMain;
        this.spaceImage = spaceImage;
    }


    public String getSpaceName() {
        return spaceName;
    }

    public String getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(String spaceId) {
        this.spaceId = spaceId;
    }

    public void setSpaceName(String spaceName) {
        this.spaceName = spaceName;
    }

    public String getSpaceDesc() {
        return spaceDesc;
    }

    public void setSpaceDesc(String spaceDesc) {
        this.spaceDesc = spaceDesc;
    }

    public Long getSpaceArea() {
        return spaceArea;
    }

    public void setSpaceArea(Long spaceArea) {
        this.spaceArea = spaceArea;
    }

    public String getSpaceLocation() {
        return spaceLocation;
    }

    public void setSpaceLocation(String spaceLocation) {
        this.spaceLocation = spaceLocation;
    }

    public Integer getSpaceCapacity() {
        return spaceCapacity;
    }

    public void setSpaceCapacity(Integer spaceCapacity) {
        this.spaceCapacity = spaceCapacity;
    }

    public Long getSpacePrice() {
        return spacePrice;
    }

    public void setSpacePrice(Long spacePrice) {
        this.spacePrice = spacePrice;
    }

    public String getSpaceMain() {
        return spaceMain;
    }

    public void setSpaceMain(String spaceMain) {
        this.spaceMain = spaceMain;
    }

    public String getSpaceImage() {
        return spaceImage;
    }

    public void setSpaceImage(String spaceImage) {
        this.spaceImage = spaceImage;
    }
}

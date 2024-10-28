package com.globalhackathon.nextlyf.model;

import com.google.firebase.firestore.PropertyName;

public class Rooms {
        @PropertyName("AC")
        private String ac;

        @PropertyName("AmenitiesIncluded")
        private String amenitiesIncluded;

        @PropertyName("Area")
        private int area;

        @PropertyName("Balcony")
        private String balcony;

        @PropertyName("Bathroom")
        private String bathroom;

        @PropertyName("Bed Type")
        private String bedType;

        @PropertyName("Category")
        private String category;

        @PropertyName("Color")
        private String color;

        @PropertyName("Description")
        private String description;

        @PropertyName("Floor No.")
        private int floorNo;

        @PropertyName("Fridge")
        private String fridge;

        @PropertyName("Name")
        private String name;

        @PropertyName("No. of Beds")
        private int noOfBeds;

        @PropertyName("Price")
        private double price;

        @PropertyName("PropertyLocation")
        private String propertyLocation;

        @PropertyName("Rating/PopularityPercentage")
        private int ratingPopularityPercentage;

        @PropertyName("Status")
        private String status;

        @PropertyName("TV")
        private String tv;

        @PropertyName("Tags")
        private String tags;

        @PropertyName("Type")
        private String type;

        @PropertyName("Wifi")
        private String wifi;

        @PropertyName("image")
        private String image;

        @PropertyName("property")
        private String property;

        public Rooms() {
        }

        public Rooms(String ac, String amenitiesIncluded, int area, String balcony, String bathroom, String bedType, String category, String color, String description, int floorNo, String fridge, String name, int noOfBeds, double price, String propertyLocation, int ratingPopularityPercentage, String status, String tv, String tags, String type, String wifi, String image, String property) {
            this.ac = ac;
            this.amenitiesIncluded = amenitiesIncluded;
            this.area = area;
            this.balcony = balcony;
            this.bathroom = bathroom;
            this.bedType = bedType;
            this.category = category;
            this.color = color;
            this.description = description;
            this.floorNo = floorNo;
            this.fridge = fridge;
            this.name = name;
            this.noOfBeds = noOfBeds;
            this.price = price;
            this.propertyLocation = propertyLocation;
            this.ratingPopularityPercentage = ratingPopularityPercentage;
            this.status = status;
            this.tv = tv;
            this.tags = tags;
            this.type = type;
            this.wifi = wifi;
            this.image = image;
            this.property = property;
        }

        // Getters and Setters

        public String getAc() {
            return ac;
        }

        public void setAc(String ac) {
            this.ac = ac;
        }

        public String getAmenitiesIncluded() {
            return amenitiesIncluded;
        }

        public void setAmenitiesIncluded(String amenitiesIncluded) {
            this.amenitiesIncluded = amenitiesIncluded;
        }

        public int getArea() {
            return area;
        }

        public void setArea(int area) {
            this.area = area;
        }

        public String getBalcony() {
            return balcony;
        }

        public void setBalcony(String balcony) {
            this.balcony = balcony;
        }

        public String getBathroom() {
            return bathroom;
        }

        public void setBathroom(String bathroom) {
            this.bathroom = bathroom;
        }

        public String getBedType() {
            return bedType;
        }

        public void setBedType(String bedType) {
            this.bedType = bedType;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getFloorNo() {
            return floorNo;
        }

        public void setFloorNo(int floorNo) {
            this.floorNo = floorNo;
        }

        public String getFridge() {
            return fridge;
        }

        public void setFridge(String fridge) {
            this.fridge = fridge;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getNoOfBeds() {
            return noOfBeds;
        }

        public void setNoOfBeds(int noOfBeds) {
            this.noOfBeds = noOfBeds;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getPropertyLocation() {
            return propertyLocation;
        }

        public void setPropertyLocation(String propertyLocation) {
            this.propertyLocation = propertyLocation;
        }

        public int getRatingPopularityPercentage() {
            return ratingPopularityPercentage;
        }

        public void setRatingPopularityPercentage(int ratingPopularityPercentage) {
            this.ratingPopularityPercentage = ratingPopularityPercentage;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTv() {
            return tv;
        }

        public void setTv(String tv) {
            this.tv = tv;
        }

        public String getTags() {
            return tags;
        }

        public void setTags(String tags) {
            this.tags = tags;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getWifi() {
            return wifi;
        }

        public void setWifi(String wifi) {
            this.wifi = wifi;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getProperty() {
            return property;
        }

        public void setProperty(String property) {
            this.property = property;
        }

}

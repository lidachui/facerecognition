package com.work.leeds.facerecognition.bean;

/**
 * Created by leeds on 2016/8/30.
 * 用户类
 */
public class User {
    String id;
    String name;
    int apartId;
    String imageUri;

    public User() {
    }

    public User(String id, String name, int apartId, String imageUri) {
        this.id = id;
        this.name = name;
        this.apartId = apartId;
        this.imageUri = imageUri;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getApartId() {
        return apartId;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setApartId(int apartId) {
        this.apartId = apartId;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}

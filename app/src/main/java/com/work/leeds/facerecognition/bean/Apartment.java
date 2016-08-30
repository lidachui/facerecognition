package com.work.leeds.facerecognition.bean;

/**
 * Created by leeds on 2016/8/29.
 * 部门数据类
 */
public class Apartment {
    int apartmentId;
    String apartmentName;

    public Apartment() {
    }

    public Apartment(int apartmentId, String apartmentName) {
        this.apartmentId = apartmentId;
        this.apartmentName = apartmentName;
    }

    public int getApartmentId() {
        return apartmentId;
    }

    public String getApartmentName() {
        return apartmentName;
    }

    public void setApartmentId(int apartmentId) {
        this.apartmentId = apartmentId;
    }

    public void setApartmentName(String apartmentName) {
        this.apartmentName = apartmentName;
    }
}

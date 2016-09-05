package com.work.leeds.facerecognition.bean;

/**
 * Created by leeds on 2016/9/5.
 * 打卡记录类
 */
public class Record {

    String staffName;//员工名字
    String time;//打卡时间

    public Record() {
    }

    public Record(String staffName, String time) {
        this.staffName = staffName;
        this.time = time;
    }

    public String getStaffName() {
        return staffName;
    }

    public String getTime() {
        return time;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

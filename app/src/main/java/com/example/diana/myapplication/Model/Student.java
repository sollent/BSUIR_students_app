package com.example.diana.myapplication.Model;

public class Student {
    private String fullName;
    private String number;
    private String markInfo;

    public Student(String fullName, String number, String markInfo) {
        this.fullName = fullName;
        this.number = number;
        this.markInfo = markInfo;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getMarkInfo() {
        return markInfo;
    }

    public void setMarkInfo(String markInfo) {
        this.markInfo = markInfo;
    }
}

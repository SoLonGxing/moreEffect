package com.util;

public class StatusReport {
    private int isOK;
    private String message;
    private String className;

    public StatusReport(int isOK, String message, String className) {
        this.isOK = isOK;
        this.message = message;
        this.className = className;
    }

    public void setIsOK(int isOK) {
        this.isOK = isOK;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getIsOK() {
        return isOK;
    }

    public String getMessage() {
        return message;
    }

    public String getClassName() {
        return className;
    }




}

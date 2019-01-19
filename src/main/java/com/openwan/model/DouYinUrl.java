package com.openwan.model;

import java.io.Serializable;

public class DouYinUrl implements Serializable {

    private String url ;
    private String ip;
    private String status;
    private String dateTime;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "DouYinUrl{" +
                "url='" + url + '\'' +
                ", ip='" + ip + '\'' +
                ", dateTime='" + dateTime + '\'' +
                '}';
    }
}

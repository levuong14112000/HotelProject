package com.example.hotelproject;

public class Service {
    private int ServiceID;
    private String ServiceName;
    private double ServicePrice;
    private boolean Deleted;

    public Service(int serviceID, String serviceName, double servicePrice, boolean deleted) {
        ServiceID = serviceID;
        ServiceName = serviceName;
        ServicePrice = servicePrice;
        Deleted = deleted;
    }

    public int getServiceID() {
        return ServiceID;
    }

    public void setServiceID(int serviceID) {
        ServiceID = serviceID;
    }

    public String getServiceName() {
        return ServiceName;
    }

    public void setServiceName(String serviceName) {
        ServiceName = serviceName;
    }

    public double getServicePrice() {
        return ServicePrice;
    }

    public void setServicePrice(double servicePrice) {
        ServicePrice = servicePrice;
    }

    public boolean isDeleted() {
        return Deleted;
    }

    public void setDeleted(boolean deleted) {
        Deleted = deleted;
    }
}

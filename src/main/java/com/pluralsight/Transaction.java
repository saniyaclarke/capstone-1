package com.pluralsight;

public class Transaction {
    private String date;
    private String time;
    private String description;
    private String vendorName;
    private double amount;

    public Transaction(String date, String time, String description, String vendorName, double amount) {
        this.date = date;
        this.time = time;
        this.description = description;
        this.vendorName = vendorName;
        this.amount = amount;

    }
    public void setDate(String date){
        this.date = date;
    }
    public String getDate(){
        return date;
    }
    public void setTime(String time){
        this.time = time;
    }
    public String getTime(){
        return time;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public String getDescription(){
        return description;
    }
    public void setVendorName(String vendorName){
        this.vendorName = vendorName;
    }
    public String getVendorName(){
        return vendorName;
    }
    public void setAmount(double amount){
        this.amount = amount;
    }
    public double getAmount(){
        return amount;
    }

}

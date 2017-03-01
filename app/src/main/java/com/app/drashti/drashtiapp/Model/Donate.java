package com.app.drashti.drashtiapp.Model;

import java.io.Serializable;

/**
 * Created by BaNkEr on 29-11-2016.
 */
public class Donate implements Serializable{



    String f_id;
    String F_name;
    String f_description;
    String f_pickupDate;
    String f_address;
    String f_instaruction;
    String f_quantity;
    String F_image;
    String F_uid;
    String F_pickupDate;
    String F_pickupTime;
    String F_pincode;
    String F_status;
    String F_requestSent;
    String F_acceptedRequest;
    String F_UserFirstName;
    String F_USerLastNAme;

    public String getF_UserFirstName() {
        return F_UserFirstName;
    }

    public void setF_UserFirstName(String f_UserFirstName) {
        F_UserFirstName = f_UserFirstName;
    }

    public String getF_USerLastNAme() {
        return F_USerLastNAme;
    }

    public void setF_USerLastNAme(String f_USerLastNAme) {
        F_USerLastNAme = f_USerLastNAme;
    }

    public String getF_acceptedRequest() {
        return F_acceptedRequest;
    }

    public void setF_acceptedRequest(String f_acceptedRequest) {
        F_acceptedRequest = f_acceptedRequest;
    }

    public String getF_uid() {
        return F_uid;
    }

    public void setF_uid(String f_uid) {
        F_uid = f_uid;
    }

    public String getF_pickupTime() {
        return F_pickupTime;
    }

    public void setF_pickupTime(String f_pickupTime) {
        F_pickupTime = f_pickupTime;
    }

    public String getF_pincode() {
        return F_pincode;
    }

    public void setF_pincode(String f_pincode) {
        F_pincode = f_pincode;
    }

    public String getF_status() {
        return F_status;
    }

    public void setF_status(String f_status) {
        F_status = f_status;
    }

    public String getF_image() {
        return F_image;
    }

    public void setF_image(String f_image) {
        F_image = f_image;
    }

    public String getF_quantity() {
        return f_quantity;
    }

    public void setF_quantity(String f_quantity) {
        this.f_quantity = f_quantity;
    }

    public String getF_id() {
        return f_id;
    }

    public void setF_id(String f_id) {
        this.f_id = f_id;
    }

    public String getF_name() {
        return F_name;
    }

    public void setF_name(String f_name) {
        F_name = f_name;
    }

    public String getF_description() {
        return f_description;
    }

    public void setF_description(String f_description) {
        this.f_description = f_description;
    }

    public String getF_pickupDate() {
        return f_pickupDate;
    }

    public void setF_pickupDate(String f_pickupDate) {
        this.f_pickupDate = f_pickupDate;
    }

    public String getF_address() {
        return f_address;
    }

    public void setF_address(String f_address) {
        this.f_address = f_address;
    }

    public String getF_instaruction() {
        return f_instaruction;
    }

    public void setF_instaruction(String f_instaruction) {
        this.f_instaruction = f_instaruction;
    }

    public String getF_requestSent() {
        return F_requestSent;
    }

    public void setF_requestSent(String f_requestSent) {
        F_requestSent = f_requestSent;
    }
}

package com.app.drashti.drashtiapp.Model;

import java.io.Serializable;

/**
 * Created by Chirag on 03-12-2016.
 */
public class Recive implements Serializable{
    String FA_id, FA_fid, FA_aid, FA_status, F_confirmRequest, U_firstname, U_lastname, U_email, U_phone,U_Address,U_Pincod;

    public String getU_Pincod() {
        return U_Pincod;
    }

    public void setU_Pincod(String u_Pincod) {
        U_Pincod = u_Pincod;
    }

    public String getU_Address() {
        return U_Address;
    }

    public void setU_Address(String u_Address) {
        U_Address = u_Address;
    }

    public String getFA_id() {
        return FA_id;
    }

    public void setFA_id(String FA_id) {
        this.FA_id = FA_id;
    }

    public String getFA_fid() {
        return FA_fid;
    }

    public void setFA_fid(String FA_fid) {
        this.FA_fid = FA_fid;
    }

    public String getFA_aid() {
        return FA_aid;
    }

    public void setFA_aid(String FA_aid) {
        this.FA_aid = FA_aid;
    }

    public String getFA_status() {
        return FA_status;
    }

    public void setFA_status(String FA_status) {
        this.FA_status = FA_status;
    }

    public String getF_confirmRequest() {
        return F_confirmRequest;
    }

    public void setF_confirmRequest(String f_confirmRequest) {
        F_confirmRequest = f_confirmRequest;
    }

    public String getU_firstname() {
        return U_firstname;
    }

    public void setU_firstname(String u_firstname) {
        U_firstname = u_firstname;
    }

    public String getU_lastname() {
        return U_lastname;
    }

    public void setU_lastname(String u_lastname) {
        U_lastname = u_lastname;
    }

    public String getU_email() {
        return U_email;
    }

    public void setU_email(String u_email) {
        U_email = u_email;
    }

    public String getU_phone() {
        return U_phone;
    }

    public void setU_phone(String u_phone) {
        U_phone = u_phone;
    }
}




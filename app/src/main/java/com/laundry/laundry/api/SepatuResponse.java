package com.laundry.laundry.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.laundry.laundry.database.SepatuDAO;

import java.util.List;

public class SepatuResponse {
    @SerializedName("data")
    @Expose
    private List<SepatuDAO> sepatus = null;

    @SerializedName("message")
    @Expose
    private String message;

    public List<SepatuDAO> getSepatus() { return sepatus; }

    public String getMessage() { return message; }

    public void setSepatus(List<SepatuDAO> users) { this.sepatus = sepatus; }

    public void setMessage(String message) { this.message = message; }
}

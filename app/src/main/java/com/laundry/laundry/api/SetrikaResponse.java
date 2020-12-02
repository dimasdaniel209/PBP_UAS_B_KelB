package com.laundry.laundry.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.laundry.laundry.database.SetrikaDAO;

import java.util.List;

public class SetrikaResponse {
    @SerializedName("data")
    @Expose
    private List<SetrikaDAO> setrikas = null;

    @SerializedName("message")
    @Expose
    private String message;

    public List<SetrikaDAO> getSetrikas() { return setrikas; }

    public String getMessage() { return message; }

    public void setSetrikas(List<SetrikaDAO> users) { this.setrikas = setrikas; }

    public void setMessage(String message) { this.message = message; }
}

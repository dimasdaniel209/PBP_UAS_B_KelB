package com.laundry.laundry.database;

import com.google.gson.annotations.SerializedName;

public class SetrikaDAO {
    @SerializedName("id")
    private String id;

    @SerializedName("berat")
    private double berat;

    @SerializedName("jumlah_pakaian")
    private int jumlah_pakaian;

    @SerializedName("jenis_pakaian")
    private String jenis_pakaian;

    public SetrikaDAO(String id, double berat, int jumlah_pakaian, String jenis_pakaian){
        this.id=id;
        this.berat=berat;
        this.jumlah_pakaian=jumlah_pakaian;
        this.jenis_pakaian=jenis_pakaian;
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public double getBerat() { return berat; }

    public void setBerat(double berat) { this.berat = berat; }

    public int getJumlah_pakaian() { return jumlah_pakaian; }

    public void setJumlah_pakaian(int jumlah_pakaian) { this.jumlah_pakaian = jumlah_pakaian; }

    public String getJenis_pakaian() { return jenis_pakaian; }

    public void setJenis_pakaian(String jenis_pakaian) { this.jenis_pakaian = jenis_pakaian; }
}

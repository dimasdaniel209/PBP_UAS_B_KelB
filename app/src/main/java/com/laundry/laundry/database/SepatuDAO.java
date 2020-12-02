package com.laundry.laundry.database;

import com.google.gson.annotations.SerializedName;

public class SepatuDAO {
    @SerializedName("id")
    private String id;

    @SerializedName("jenis_layanan")
    private String jenis_layanan;

    @SerializedName("kondisi")
    private String kondisi;

    @SerializedName("jenis_sepatu")
    private String jenis_sepatu;

    public SepatuDAO(String id, String jenis_layanan, String kondisi, String jenis_sepatu) {
        this.id = id;
        this.jenis_layanan = jenis_layanan;
        this.kondisi = kondisi;
        this.jenis_sepatu = jenis_sepatu;
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getJenis_layanan() { return jenis_layanan; }

    public void setJenis_layanan(String jenis_layanan) { this.jenis_layanan = jenis_layanan; }

    public String getKondisi() { return kondisi; }

    public void setKondisi(String kondisi) { this.kondisi = kondisi; }

    public String getJenis_sepatu() { return jenis_sepatu; }

    public void setJenis_sepatu(String jenis_sepatu) { this.jenis_sepatu = jenis_sepatu; }
}

package com.laundry.laundry.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Order implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "jumlah_pakaian")
    public String jumlah_pakaian;

    @ColumnInfo(name = "berat")
    public String berat;

    @ColumnInfo(name = "layanan")
    public String layanan;

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getJumlah_pakaian() { return jumlah_pakaian; }

    public void setJumlah_pakaian(String jumlah_pakaian) { this.jumlah_pakaian = jumlah_pakaian; }

    public String getBerat() { return berat; }

    public void setBerat(String berat) { this.berat = berat; }

    public String getLayanan() { return layanan; }

    public void setLayanan(String layanan) { this.layanan = layanan; }
}

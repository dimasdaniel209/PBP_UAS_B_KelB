package com.laundry.laundry.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Order extends BaseObservable implements Serializable {

    public Order(){ }
    public Order(Integer id, Integer jumlah_pakaian, Double berat, String layanan){
        this.id = id;
        this.jumlah_pakaian = jumlah_pakaian;
        this.berat = berat;
        this.layanan = layanan;
    }

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "jumlah_pakaian")
    public int jumlah_pakaian;

    @ColumnInfo(name = "berat")
    public double berat;

    @ColumnInfo(name = "layanan")
    public String layanan;

    @Bindable
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        notifyPropertyChanged(BR.id);
    }

    @Bindable
    public String geStringId() {
        return String.valueOf(id);
    }

    public void setStringId(String id) {
        if(!id.isEmpty()){
            this.id = Integer.parseInt(id);
        }else {
            this.id = 0;
        }
        notifyPropertyChanged(BR.id);
    }

    @Bindable
    public int getJumlah_pakaian() {
        return jumlah_pakaian;
    }

    public void setJumlah_pakaian(int jumlah_pakaian) {
        this.jumlah_pakaian = jumlah_pakaian;
        notifyPropertyChanged(BR.jumlah_pakaian);
    }

    @Bindable
    public String getStringJumlah_pakaian() {
        return String.valueOf(jumlah_pakaian);
    }

    public void setStringJumlah_pakaian(String jumlah_pakaian) {
        if(!jumlah_pakaian.isEmpty()){
            this.jumlah_pakaian = Integer.parseInt(jumlah_pakaian);
        }else {
            this.jumlah_pakaian = 0;
        }
        notifyPropertyChanged(BR.jumlah_pakaian);
    }

    @Bindable
    public double getBerat() {
        return berat;
    }

    public void setBerat(double berat) {
        this.berat = berat;
        notifyPropertyChanged(BR.berat);
    }

    @Bindable
    public String getStringBerat() {
        return String.valueOf(berat);
    }

    public void setStringBerat(String  berat) {
        if(!berat.isEmpty()){
            this.berat = Double.parseDouble(berat);
        }else {
            this.berat = 0;
        }
        notifyPropertyChanged(BR.berat);
    }

    @Bindable
    public String getLayanan() {
        return layanan;
    }

    public void setLayanan(String layanan) {
        this.layanan = layanan;
        notifyPropertyChanged(BR.layanan);
    }
}
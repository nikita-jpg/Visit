package com.example.visit;

import android.net.Uri;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Update;

import static androidx.room.ColumnInfo.TEXT;

@Entity(tableName = "myTable")
public class Person {
    private String name;
    private String age;
    private String address;
    private String number;
    private String photoId;

    @PrimaryKey(autoGenerate = true)
    private long id;

    public Person(){}

    Person(String name, String address, String number, String photoId) {
        this.name = name;
        this.address = address;
        this.number = number;
        this.photoId = photoId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPhotoId() {
        return photoId;
    }

    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}

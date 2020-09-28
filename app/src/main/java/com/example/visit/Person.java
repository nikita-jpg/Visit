package com.example.visit;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.android.material.textfield.TextInputLayout;

@Entity(tableName = "myTable")
public class Person {
    private String name;
    private String post;
    private String email;
    private String vk;
    private String number;
    private String discord;
    private String photoId;

    @PrimaryKey(autoGenerate = true)
    private long id;

    public Person(){}

    public Person(String name, String post, String photoId, String email, String vk, String number, String discord) {
        this.name = name;
        this.post = post;
        this.email = email;
        this.vk = vk;
        this.number = number;
        this.discord = discord;
        this.photoId = photoId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVk() {
        return vk;
    }

    public void setVk(String vk) {
        this.vk = vk;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getDiscord() {
        return discord;
    }

    public void setDiscord(String discord) {
        this.discord = discord;
    }
}

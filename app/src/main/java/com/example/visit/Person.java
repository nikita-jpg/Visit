package com.example.visit;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "myTable")
public class Person {
    private String name;
    private String age;
    private int photoId;

    @PrimaryKey(autoGenerate = true)
    private long id;

    public Person(){}

    Person(String name, String age, int photoId) {
        this.name = name;
        this.age = age;
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

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}

package com.example.visit;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "events")
public class TeamEvent {
    private String title,desc1,desc2,img1,img2;

    @PrimaryKey(autoGenerate = true)
    private long id;

    public TeamEvent()
    {}

    public TeamEvent(String title, String desc1, String desc2, String img1, String img2) {
        this.title = title;
        this.desc1 = desc1;
        this.desc2 = desc2;
        this.img1 = img1;
        this.img2 = img2;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc1() {
        return desc1;
    }

    public void setDesc1(String desc1) {
        this.desc1 = desc1;
    }

    public String getDesc2() {
        return desc2;
    }

    public void setDesc2(String desc2) {
        this.desc2 = desc2;
    }

    public String getImg1() {
        return img1;
    }

    public void setImg1(String img1) {
        this.img1 = img1;
    }

    public String getImg2() {
        return img2;
    }

    public void setImg2(String img2) {
        this.img2 = img2;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}

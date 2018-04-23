package com.whut.umrhamster.gamesinfo.Model;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by 12421 on 2018/4/3.
 */

public class GameEvaluation extends BmobObject implements Serializable{
    private long id;
    private String title;
    private String gamename;
    private float grade;
    private String author;
    private BmobDate pubdate;
    private BmobFile jsonfile;
    private String link;
    private String test;

    public GameEvaluation(){}

    public GameEvaluation(String title, String test, String author, BmobDate bmobDate){
        this.title = title;
        this.test = test;
        this.author = author;
        this.pubdate = bmobDate;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setGamename(String gamename) {
        this.gamename = gamename;
    }

    public String getGamename() {
        return gamename;
    }

    public void setGrade(float grade) {
        this.grade = grade;
    }

    public float getGrade() {
        return grade;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public void setPubdate(BmobDate pubdate) {
        this.pubdate = pubdate;
    }

    public BmobDate getPubdate() {
        return pubdate;
    }

    public void setJsonfile(BmobFile jsonfile) {
        this.jsonfile = jsonfile;
    }

    public BmobFile getJsonfile() {
        return jsonfile;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }
}

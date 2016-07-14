package edu.buu.childhood.baidumap.poj;

/**
 * Created by lcc on 2016/7/2.
 */

public class Book {
    private int bIcon;
    private String bName;

    public Book() {
    }

    public Book(int aIcon, String aName) {
        this.bIcon = aIcon;
        this.bName = aName;
    }

    public int getaIcon() {
        return bIcon;
    }

    public String getaName() {
        return bName;
    }

    public void setaIcon(int aIcon) {
        this.bIcon = aIcon;
    }

    public void setaName(String aName) {
        this.bName = aName;
    }
}
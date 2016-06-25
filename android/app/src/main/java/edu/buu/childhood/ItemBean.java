package edu.buu.childhood;

/**
 * 初始数据
 * Created by lcc on 2016/6/16.
 */
public class ItemBean {
    public int Image;
    public int background;
    public String itemtext1;
    public String itemtext2;
    public String itemtext3;

    public ItemBean(int Image,int background,String itemtext11,String itemtext22, String itemtext33) {
        this.Image = Image;
        this.background=background;
        this.itemtext1 = itemtext11;
        this.itemtext2 = itemtext22;
        this.itemtext3 = itemtext33;
    }
    public ItemBean(String itemtext11,String itemtext22, String itemtext33) {
        this.itemtext1 = itemtext11;
        this.itemtext2 = itemtext22;
        this.itemtext3 = itemtext33;
    }
}


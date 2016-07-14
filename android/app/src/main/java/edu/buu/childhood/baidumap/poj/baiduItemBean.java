package edu.buu.childhood.baidumap.poj;

import java.io.Serializable;

/**
 * Created by lcc on 2016/7/8.
 */
public class baiduItemBean implements Serializable {

    private static final long serialVersionUID = -758459502806858414L;
    /**
     * 精度
     */
    private double latitude;
    /**
     * 纬度
     */
    private double longitude;

    /**
     * 名称
     */
    private String name;

    public baiduItemBean()
    {
    }

    public baiduItemBean(double latitude, double longitude,  String name )

    {
        super();
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
    }

    public double getLatitude()
    {
        return latitude;
    }

    public void setLatitude(double latitude)
    {
        this.latitude = latitude;
    }

    public double getLongitude()
    {
        return longitude;
    }

    public void setLongitude(double longitude)
    {
        this.longitude = longitude;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }



}
package com.happy.xyr.domain;

/**
 * Created by zhonglq on 2016/6/12.
 */
public class PieData {

    private String name;
    private float data;
    private float percentage;

    private int color;
    private float angle;

    public PieData(String name, float data) {
        this.name = name;
        this.data = data;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public float getData() {
        return data;
    }

    public void setData(float data) {
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPercentage() {
        return percentage;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }
}

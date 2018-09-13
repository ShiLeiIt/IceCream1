package com.samrt.qiushi.icecream.model;

import java.io.Serializable;

/**
 * Created by shilei on 2018/9/10
 */
public class IceCreamModel implements Serializable {
    private String name;//名字
    private String image;//头像
    private String type;
    private String composition;
    private String earlyAdoptersPrice;
    private double originalPrice;
    private String Amount;
    private int dishRemain;
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getDishRemain() {
        return dishRemain;
    }

    public void setDishRemain(int dishRemain) {
        this.dishRemain = dishRemain;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }


    public IceCreamModel(String name, String image, String type, String composition, double originalPrice, String earlyAdoptersPrice) {
        this.name = name;
        this.image = image;
        this.type = type;
        this.composition = composition;
        this.originalPrice = originalPrice;
        this.earlyAdoptersPrice = earlyAdoptersPrice;
    }


    public double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getEarlyAdoptersPrice() {
        return earlyAdoptersPrice;
    }

    public void setEarlyAdoptersPrice(String earlyAdoptersPrice) {
        this.earlyAdoptersPrice = earlyAdoptersPrice;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getComposition() {
        return composition;
    }

    public void setComposition(String composition) {
        this.composition = composition;
    }
}

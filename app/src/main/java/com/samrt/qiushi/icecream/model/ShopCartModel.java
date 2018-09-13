package com.samrt.qiushi.icecream.model;

import android.util.Log;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cheng on 16-11-12.
 */
public class ShopCartModel implements Serializable {
    private int shoppingAccount;//商品总数
    private double shoppingTotalPrice;//商品总价钱
    private Map<IceCreamModel,Integer> shoppingSingle;//单个物品的总价价钱

    public ShopCartModel(){
        this.shoppingAccount = 0;
        this.shoppingTotalPrice = 0;
        this.shoppingSingle = new HashMap<>();
    }

    public int getShoppingAccount() {
        return shoppingAccount;
    }

    public double getShoppingTotalPrice() {
        return shoppingTotalPrice;
    }

    public Map<IceCreamModel, Integer> getShoppingSingleMap() {
        return shoppingSingle;
    }

    public boolean addShoppingSingle(IceCreamModel food){
        int remain = food.getDishRemain();
        if(remain<=0)
            return false;
        food.setDishRemain(--remain);
        int num = 0;
        if(shoppingSingle.containsKey(food)){
            num = shoppingSingle.get(food);
        }
        num+=1;
        shoppingSingle.put(food,num);
        Log.e("TAG", "addShoppingSingle: "+shoppingSingle.get(food));

        shoppingTotalPrice += food.getOriginalPrice();
        shoppingAccount++;
        return true;
    }

    public boolean subShoppingSingle(IceCreamModel food){
        int num = 0;
        if(shoppingSingle.containsKey(food)){
            num = shoppingSingle.get(food);
        }
        if(num<=0) return false;
        num--;
        int remain = food.getDishRemain();
        food.setDishRemain(++remain);
        shoppingSingle.put(food,num);
        if (num ==0) shoppingSingle.remove(food);

        shoppingTotalPrice -= food.getOriginalPrice();
        shoppingAccount--;
        return true;
    }

    public int getDishAccount() {
        return shoppingSingle.size();
    }

    public void clear(){
        this.shoppingAccount = 0;
        this.shoppingTotalPrice = 0;
        this.shoppingSingle.clear();
    }
}

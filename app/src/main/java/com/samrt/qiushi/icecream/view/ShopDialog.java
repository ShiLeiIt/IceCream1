package com.samrt.qiushi.icecream.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.samrt.qiushi.icecream.R;
import com.samrt.qiushi.icecream.model.IceCreamModel;

import java.math.BigDecimal;

/**
 * Created by shilei on 2018/9/12
 */

public class ShopDialog extends Dialog {

    private Context mContext;
    private TextView mTvFoodAccount;
    private TextView mTvCombined;
    private LinearLayout mLl_settlement;
    private TextView mTvAction;
    private OnAddOrSubListener mListener;
    private int mPosition;
    private IceCreamModel mModel;
    private LinearLayout.LayoutParams mLp;

    public ShopDialog(@NonNull Context context) {
        super(context);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View dialogView = inflater.inflate(R.layout.dialog_add_shop_car, null);
        setContentView(dialogView);
        //活动满20减10
        mTvAction = (TextView) dialogView.findViewById(R.id.tv_action);
        //数量
        mTvFoodAccount = (TextView) dialogView.findViewById(R.id.tv_food_account);

        //总计
        mTvCombined = (TextView) dialogView.findViewById(R.id.tv_combined);

        ImageView ivFoodAdd = (ImageView) dialogView.findViewById(R.id.iv_food_add);//增加按钮
        ImageView ivFoodremove = (ImageView) dialogView.findViewById(R.id.iv_food_remove);//减少按钮
        ivFoodAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mModel != null)
                mListener.OnAddClick(mPosition,v);
                int count = mModel.getCount();
                count = count++;
                mModel.setCount(count);
                mTvFoodAccount.setText(count + "");
                double v1 = new BigDecimal(mModel.getOriginalPrice()).multiply(new BigDecimal(count)).doubleValue();
                mTvCombined.setText("总计 ： ￥" + v1);
            }
        });
        ivFoodremove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = mModel.getCount();
                if (count == 0){
                    return;
                }
                count = count--;
                mTvFoodAccount.setText(count + "");
                mModel.setCount(count);
                double v1 = new BigDecimal(mModel.getOriginalPrice()).multiply(new BigDecimal(count)).doubleValue();
                mTvCombined.setText("总计 ： ￥" + v1);
                if (mModel != null)
                mListener.OnSubClick(mPosition);
            }
        });
        //结算布局
        mLl_settlement = (LinearLayout) dialogView.findViewById(R.id.ll_settlement);

        //3.设置指定的宽高,如果不设置的话，弹出的对话框可能不会显示全整个布局，当然在布局中写死宽高也可以
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //注意要在Dialog show之后，再将宽高属性设置进去，才有效果
        // mDialog.show();
        window.setAttributes(lp);
        //设置点击其它地方让消失弹窗
        setCancelable(true);
    }

    public interface OnAddOrSubListener {
        void OnAddClick(int position,View v);

        void OnSubClick(int position);
    }

    public void setOnAddOrSubListener(OnAddOrSubListener listener) {
        mListener = listener;
    }

    public void showShopDialog(IceCreamModel iceCreamModel ,int position){
        show();
        mModel = iceCreamModel;
        mPosition = position;
        if (iceCreamModel == null){
            return;
        }
        double v1 = new BigDecimal(mModel.getOriginalPrice()).multiply(new BigDecimal(mModel.getCount())).doubleValue();
        mTvCombined.setText("总计 ： ￥" + v1);
        mTvFoodAccount.setText(mModel.getCount() + "");
        if (mModel.getType().equals("0")) {//通过type值来判断是否有活动
            mTvAction.setVisibility(View.GONE);
            mLp = (LinearLayout.LayoutParams) mLl_settlement.getLayoutParams();
            mLp.setMargins(0, 100, 0, 0);
            mLl_settlement.setLayoutParams(mLp);
        } else {
            mLp.setMargins(0, 0, 0, 0);
            mLl_settlement.setLayoutParams(mLp);
            mTvAction.setVisibility(View.VISIBLE);
        }
    }
}

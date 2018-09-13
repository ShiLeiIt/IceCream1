package com.samrt.qiushi.icecream.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.samrt.qiushi.icecream.R;
import com.samrt.qiushi.icecream.imp.ShopCartImp;
import com.samrt.qiushi.icecream.model.IceCreamModel;
import com.samrt.qiushi.icecream.model.ShopCartModel;

import java.util.List;

/**
 * Created by shilei on 2018/9/10 首页适配器
 */
public class RecycleViewMainAdapter extends RecyclerView.Adapter<RecycleViewMainAdapter.SimpleAdapterViewHolder> {
    private List<IceCreamModel> mList;
    private Context mContext;
    private int count = 0;
    private OnAddShopCarClickLisenter mOnAddShopCarClickLisenter;
    private OnBuyNowClickLisenter mOnBuyNowClickLisenter;

    public ShopCartImp getShopCartImp() {
        return shopCartImp;
    }

    public void setShopCartImp(ShopCartImp shopCartImp) {
        this.shopCartImp = shopCartImp;
    }

    private ShopCartImp shopCartImp;




    public RecycleViewMainAdapter(List<IceCreamModel> list, Context context) {
        this.mList = list;
        this.mContext = context;
    }

    @NonNull
    @Override
    public SimpleAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.main_grid_view_item, null, false);
        return new SimpleAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SimpleAdapterViewHolder holder, final int position) {
        IceCreamModel iceCreamModel = mList.get(position);
        if (holder!=null) {
            holder.tvIceCreamName.setText(iceCreamModel.getName());
            holder.tvIceCreamComposition.setText(iceCreamModel.getComposition());
            holder.tvIceCreamOriginalPrice.setText(iceCreamModel.getOriginalPrice() + "");
            if (mList.get(position).getType().equals("0")) {
                holder.tvIceCreamOriginalPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                holder.tvIceCreamOriginalPrice.setVisibility(View.GONE);
            }

            holder.tvIceCreamEarlyAdoptersPrice.setText(iceCreamModel.getEarlyAdoptersPrice());
            if (mOnAddShopCarClickLisenter != null) {
                holder.tvAddShopCar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //设置添加购物车监听事件
                        mOnAddShopCarClickLisenter.onAddShopCarClick(holder.tvAddShopCar, position);
                    }
                });
            }

            if (mOnBuyNowClickLisenter != null) {
                holder.tvBuyNow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //设置立即购买监听事件
                        mOnBuyNowClickLisenter.onBuyNowClick(holder.tvBuyNow, position);
                    }
                });
            }

        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class SimpleAdapterViewHolder extends RecyclerView.ViewHolder {
        public TextView tvIceCreamName;
        public TextView tvIceCreamComposition;
        public TextView tvIceCreamOriginalPrice;
        public TextView tvIceCreamEarlyAdoptersPrice;
        public TextView tvAddShopCar;
        public TextView tvBuyNow;
        public View rootView;
        public int position;


        public SimpleAdapterViewHolder(View itemView) {
            super(itemView);
            tvIceCreamName = (TextView) itemView
                    .findViewById(R.id.tv_icecream_name);
            tvIceCreamComposition = (TextView) itemView
                    .findViewById(R.id.tv_icecream_composition);
            tvIceCreamOriginalPrice = (TextView) itemView
                    .findViewById(R.id.tv_icecream_original_price);
            tvIceCreamEarlyAdoptersPrice = (TextView) itemView
                    .findViewById(R.id.tv_icecream_early_adopters_price);
            tvAddShopCar = (TextView) itemView
                    .findViewById(R.id.tv_add_shop_car);
            tvBuyNow = (TextView) itemView
                    .findViewById(R.id.tv_buy_now);
            rootView = itemView
                    .findViewById(R.id.ll_view);

        }
    }

    public interface OnAddShopCarClickLisenter {
        void onAddShopCarClick(TextView view, int position);
    }

    public void setOnAddShopCarClickLisenter(OnAddShopCarClickLisenter onAddShopCarClickLisenter) {
        mOnAddShopCarClickLisenter = onAddShopCarClickLisenter;
    }

    public interface OnBuyNowClickLisenter {
        void onBuyNowClick(TextView view, int position);
    }

    public void setOnBuyNowClickLisenter(OnBuyNowClickLisenter onBuyNowClickLisenter) {
        mOnBuyNowClickLisenter = onBuyNowClickLisenter;
    }

}
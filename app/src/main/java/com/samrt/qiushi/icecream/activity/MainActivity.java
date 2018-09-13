package com.samrt.qiushi.icecream.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.samrt.qiushi.icecream.R;
import com.samrt.qiushi.icecream.adapter.RecycleViewMainAdapter;
import com.samrt.qiushi.icecream.imp.ShopCartImp;
import com.samrt.qiushi.icecream.model.IceCreamModel;
import com.samrt.qiushi.icecream.model.ShopCartModel;
import com.samrt.qiushi.icecream.utils.DensityUtil;
import com.samrt.qiushi.icecream.view.FakeAddImageView;
import com.samrt.qiushi.icecream.view.PointFTypeEvaluator;
import com.samrt.qiushi.icecream.view.ShopDialog;
import com.samrt.qiushi.icecream.view.SpaceItemDecoration;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, RecycleViewMainAdapter.OnAddShopCarClickLisenter, RecycleViewMainAdapter.OnBuyNowClickLisenter {
    RecyclerView recyclerView;
    RecycleViewMainAdapter adapter;
    List<IceCreamModel> mIceCreamModels = new ArrayList<IceCreamModel>();
    GridLayoutManager layoutManager;
    @Bind(R.id.iv_shop_car)
    ImageView ivShopCar;
    @Bind(R.id.ll_buy)
    LinearLayout llBuy;
    @Bind(R.id.shopping_cart_total_num)
    TextView shoppingCartTotalNum;
    private TextView mTvGetIceCream;
    private TextView mTvLogin;
    private ViewGroup anim_mask_layout;// 动画层
    private int count;//购物车总量
    private TextView mTvFoodAccount;
    private TextView mTvCombined;
    private LinearLayout mLl_settlement;
    private TextView mTvAction;
    private ShopDialog mShopDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        initDialog();
        initData();
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_ice_cream);
        recyclerView.setHasFixedSize(true);
        adapter = new RecycleViewMainAdapter(mIceCreamModels, this);
        layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new SpaceItemDecoration(DensityUtil.dip2px(MainActivity.this, 50)));
        mTvGetIceCream = (TextView) findViewById(R.id.tv_get_icecream); //取冰淇淋
        mTvLogin = (TextView) findViewById(R.id.tv_login);//登录
        mTvGetIceCream.setOnClickListener(this);
        mTvLogin.setOnClickListener(this);
        adapter.setOnAddShopCarClickLisenter(this);//添加购物车监听
        adapter.setOnBuyNowClickLisenter(this);//立即购买监听



    }

    private void initData() {
        IceCreamModel iceCreamModel1 = new IceCreamModel("罗宁根黑松冰淇淋 ", "", "0", "优质小麦粉", 18, "尝鲜价：15");
        IceCreamModel iceCreamModel2 = new IceCreamModel("格根黑松冰淇淋 ", "", "0", "优质小麦粉", 18, "尝鲜价：15");
        IceCreamModel iceCreamModel3 = new IceCreamModel("根黑松冰淇淋 ", "", "1", "优质小麦粉", 18, "￥15");
        IceCreamModel iceCreamModel4 = new IceCreamModel("格黑松冰淇淋 ", "", "1", "优质小麦粉", 18, "￥15");
        mIceCreamModels.add(iceCreamModel1);
        mIceCreamModels.add(iceCreamModel2);
        mIceCreamModels.add(iceCreamModel3);
        mIceCreamModels.add(iceCreamModel4);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_get_icecream:
                Toast.makeText(this, getString(R.string.tv_get_icecream), Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_login:
                Toast.makeText(this, getString(R.string.tv_login), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onAddShopCarClick(TextView view, int position) {
//        showDialog(mIceCreamModels.get(position).getType(), position);
        mShopDialog.showShopDialog(mIceCreamModels.get(position),position);
    }

    public void initDialog() {
        mShopDialog = new ShopDialog(this);
        mShopDialog.setOnAddOrSubListener(new ShopDialog.OnAddOrSubListener() {
            @Override
            public void OnAddClick(int position,View v) {
                ++MainActivity.this.count;
                if (MainActivity.this.count > 0) {
                    //shoppingCartTotalNum 购物车总数量
                    shoppingCartTotalNum.setVisibility(View.VISIBLE);
                    shoppingCartTotalNum.setText(MainActivity.this.count + "");
                } else {
                    shoppingCartTotalNum.setVisibility(View.GONE);
                }
                // tvFoodAccount.setText(MainActivity.this.count + "");
                mIceCreamModels.get(position).setCount(mIceCreamModels.get(position).getCount() + 1);

                addShopCarAnimation(v);//添加到购物车红点动画
            }

            @Override
            public void OnSubClick(int position) {
                if (MainActivity.this.count > 0) {
                    --MainActivity.this.count;
                    // tvFoodAccount.setText(MainActivity.this.count + "");
                    shoppingCartTotalNum.setText(MainActivity.this.count + "");
                } else {
                    shoppingCartTotalNum.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, "空", Toast.LENGTH_SHORT).show();
                }
                mIceCreamModels.get(position).setCount(mIceCreamModels.get(position).getCount() - 1);
            }
        });

    }

    @Override
    public void onBuyNowClick(TextView view, int position) {
        //立即购买
        Toast.makeText(this, getString(R.string.tv_buy_now), Toast.LENGTH_SHORT).show();
    }


    public void addShopCarAnimation(View v) {
        int[] addLocation = new int[2];
        int[] cartLocation = new int[2];
        int[] recycleLocation = new int[2];
        v.getLocationInWindow(addLocation);
        ivShopCar.getLocationInWindow(cartLocation);
        recyclerView.getLocationInWindow(recycleLocation);

        PointF startP = new PointF();
        PointF endP = new PointF();
        PointF controlP = new PointF();

        startP.x = addLocation[0];
        startP.y = addLocation[1] - recycleLocation[1];
        endP.x = cartLocation[0];
        endP.y = cartLocation[1] - recycleLocation[1];
        controlP.x = endP.x;
        controlP.y = startP.y;

        final FakeAddImageView fakeAddImageView = new FakeAddImageView(MainActivity.this);
        llBuy.addView(fakeAddImageView);
        fakeAddImageView.setImageResource(R.drawable.sign);
        fakeAddImageView.getLayoutParams().width = getResources().getDimensionPixelSize(R.dimen.dp_16);
        fakeAddImageView.getLayoutParams().height = getResources().getDimensionPixelSize(R.dimen.dp_16);
        fakeAddImageView.setVisibility(View.VISIBLE);
        ObjectAnimator addAnimator = ObjectAnimator.ofObject(fakeAddImageView, "mPointF",
                new PointFTypeEvaluator(controlP), startP, endP);
        addAnimator.setInterpolator(new AccelerateInterpolator());
        addAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                fakeAddImageView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                fakeAddImageView.setVisibility(View.GONE);
                llBuy.removeView(fakeAddImageView);
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        ObjectAnimator scaleAnimatorX = new ObjectAnimator().ofFloat(ivShopCar, "scaleX", 0.6f, 1.0f);
        ObjectAnimator scaleAnimatorY = new ObjectAnimator().ofFloat(ivShopCar, "scaleY", 0.6f, 1.0f);
        scaleAnimatorX.setInterpolator(new AccelerateInterpolator());
        scaleAnimatorY.setInterpolator(new AccelerateInterpolator());
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(scaleAnimatorX).with(scaleAnimatorY).after(addAnimator);
        animatorSet.setDuration(300);
        animatorSet.start();

    }
}

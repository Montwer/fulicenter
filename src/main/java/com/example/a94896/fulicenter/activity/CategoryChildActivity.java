package com.example.a94896.fulicenter.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.a94896.fulicenter.I;
import com.example.a94896.fulicenter.R;
import com.example.a94896.fulicenter.Views.SpaceItemDecoration;
import com.example.a94896.fulicenter.adapter.GoodsAdapter;
import com.example.a94896.fulicenter.bean.NewGoodsBean;
import com.example.a94896.fulicenter.net.NetDao;
import com.example.a94896.fulicenter.net.OkHttpUtils;
import com.example.a94896.fulicenter.utils.CommonUtils;
import com.example.a94896.fulicenter.utils.ConvertUtils;
import com.example.a94896.fulicenter.utils.L;
import com.example.a94896.fulicenter.utils.MFGT;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CategoryChildActivity extends BaseActivity {

    @BindView(R.id.tv_refresh)
    TextView tvRefresh;
    @BindView(R.id.rvNewGoods)
    RecyclerView rlv;
    @BindView(R.id.srl)
    SwipeRefreshLayout sfl;
    CategoryChildActivity mContext;
    GoodsAdapter mAdapter;
    ArrayList<NewGoodsBean> mList;
    int pageId = 1;
    GridLayoutManager mGridLayoutManager;
    int catId;
    @BindView(R.id.btn_sort_price)
    Button btnSortPrice;
    @BindView(R.id.btn_sort_addtime)
    Button btnSortAddtime;
    boolean addTimeAsc=false;
    boolean priceAsc=false;
    int sortBy=I.SORT_BY_ADDTIME_DESC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_category_child);
        ButterKnife.bind(this);
        mContext = this;
        mList = new ArrayList<>();
        mAdapter = new GoodsAdapter(mList, mContext);
        catId = getIntent().getIntExtra(I.CategoryChild.CAT_ID, 0);
        if (catId == 0) {
            finish();
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        sfl.setColorSchemeColors(
                getResources().getColor(R.color.google_blue),
                getResources().getColor(R.color.google_yellow),
                getResources().getColor(R.color.google_red),
                getResources().getColor(R.color.google_green));
        mGridLayoutManager = new GridLayoutManager(mContext, I.COLUM_NUM);
        rlv.setLayoutManager(mGridLayoutManager);
        rlv.setHasFixedSize(true);
        rlv.setAdapter(mAdapter);
        rlv.addItemDecoration(new SpaceItemDecoration(12));
    }

    @Override
    protected void setListener() {
        setPullUpListener();
        setPullDownListener();
    }

    /**
     * 下拉刷新
     */
    private void setPullDownListener() {
        sfl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                sfl.setRefreshing(true);
                tvRefresh.setVisibility(View.VISIBLE);
                pageId = 1;
                downLoadCategoryGoods(I.ACTION_PULL_DOWN);
                L.e("ok");
            }
        });
    }

    /**
     * 上拉刷新
     */
    private void setPullUpListener() {
        rlv.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int lastPosition = mGridLayoutManager.findLastVisibleItemPosition();
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastPosition == mAdapter.getItemCount() - 1
                        && mAdapter.isMore()) {
                    pageId++;
                    downLoadCategoryGoods(I.ACTION_PULL_UP);
                    L.e("ok");
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int firstPosition = mGridLayoutManager.findFirstVisibleItemPosition();
                sfl.setEnabled(firstPosition == 0);
            }
        });
    }

    @Override
    protected void initData() {
        downLoadCategoryGoods(I.ACTION_DOWNLOAD);
    }

    private void downLoadCategoryGoods(final int action) {
        NetDao.downloadCategoryGoods(mContext, catId, pageId, new OkHttpUtils.OnCompleteListener<NewGoodsBean[]>() {
            @Override
            public void onSuccess(NewGoodsBean[] result) {
                sfl.setRefreshing(false);
                tvRefresh.setVisibility(View.GONE);
                mAdapter.setMore(true);
                if (result != null && result.length > 0) {
                    ArrayList<NewGoodsBean> list = ConvertUtils.array2List(result);
                    if (action == I.ACTION_DOWNLOAD || action == I.ACTION_PULL_DOWN) {
                        mAdapter.initData(list);
                    } else {
                        mAdapter.addData(list);
                    }

                    if (list.size() < I.PAGE_SIZE_DEFAULT) {
                        mAdapter.setMore(false);
                    } else
                        mAdapter.setMore(true);
                }
            }

            @Override
            public void onError(String error) {
                sfl.setRefreshing(false);
                tvRefresh.setVisibility(View.GONE);
                CommonUtils.showLongToast(error);
                L.e("error: " + error);

            }
        });
    }


    @OnClick(R.id.backClickArea)
    public void onClick() {
        MFGT.finish(this);
    }

    @OnClick({R.id.btn_sort_price, R.id.btn_sort_addtime})
    public void onClick(View view) {
        Drawable right;
        switch (view.getId()) {
            case R.id.btn_sort_price:
                if (priceAsc) {
                    sortBy = I.SORT_BY_PRICE_ASC;
                    right = getResources().getDrawable(R.mipmap.arrow_order_up);
                } else {
                    sortBy = I.SORT_BY_PRICE_DESC;
                    right = getResources().getDrawable(R.mipmap.arrow_order_down);
                }
                right.setBounds(0,0,right.getIntrinsicWidth(),right.getIntrinsicHeight());
                btnSortPrice.setCompoundDrawablesRelativeWithIntrinsicBounds(null,null,right,null);
                break;
            case R.id.btn_sort_addtime:
                if (addTimeAsc){
                    sortBy=I.SORT_BY_ADDTIME_ASC;
                    right = getResources().getDrawable(R.mipmap.arrow_order_up);
                }else {
                    sortBy=I.SORT_BY_ADDTIME_DESC;
                    right = getResources().getDrawable(R.mipmap.arrow_order_down);
                }
                right.setBounds(0,0,right.getIntrinsicWidth(),right.getIntrinsicHeight());
                btnSortAddtime.setCompoundDrawablesRelativeWithIntrinsicBounds(null,null,right,null);
                addTimeAsc=!addTimeAsc;
                break;
        }
        mAdapter.setSortBy(sortBy);
    }
}

package com.example.a94896.fulicenter.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.a94896.fulicenter.I;
import com.example.a94896.fulicenter.R;
import com.example.a94896.fulicenter.Views.SpaceItemDecoration;
import com.example.a94896.fulicenter.adapter.GoodsAdapter;
import com.example.a94896.fulicenter.bean.BoutiqueBean;
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

import static com.example.a94896.fulicenter.R.id.srl;

/**
 * Created by 94896 on 2016/10/19.
 */

public class BoutiqueChildActivity extends BaseActivity {
    @BindView(R.id.tv_common_title)
    TextView tvCommonTitle;
    @BindView(R.id.tv_refresh)
    TextView tvRefresh;
    @BindView(R.id.rvNewGoods)
    RecyclerView rlv;
    @BindView(srl)
    SwipeRefreshLayout sfl;
    BoutiqueChildActivity mContext;
    GoodsAdapter mAdapter;
    ArrayList<NewGoodsBean>mList;
    int pageId=1;
    GridLayoutManager mGridLayoutManager;
    BoutiqueBean boutique;
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_boutique_child);
        ButterKnife.bind(this);
        boutique= (BoutiqueBean) getIntent().getSerializableExtra(I.Boutique.CAT_ID);
        if (boutique==null){
            finish();
        }
        mContext=this;
        mList=new ArrayList<>();
        mAdapter=new GoodsAdapter(mList,mContext);
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
        tvCommonTitle.setText(boutique.getTitle());
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
                pageId=1;
                downLoadData(I.ACTION_PULL_DOWN);
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
                if(newState==RecyclerView.SCROLL_STATE_IDLE
                        && lastPosition==mAdapter.getItemCount()-1
                        && mAdapter.isMore()){
                    pageId++;
                    downLoadData(I.ACTION_PULL_UP);
                    L.e("ok");
                }
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int firstPosition = mGridLayoutManager.findFirstVisibleItemPosition();
                sfl.setEnabled(firstPosition==0);
            }
        });
    }

    @Override
    protected void initData() {
        downLoadData(I.ACTION_DOWNLOAD);
    }

    private void downLoadData(final int action) {
        NetDao.downloadGoods(mContext,boutique.getId(),pageId, new OkHttpUtils.OnCompleteListener<NewGoodsBean[]>() {
            @Override
            public void onSuccess(NewGoodsBean[] result) {
                sfl.setRefreshing(false);
                tvRefresh.setVisibility(View.GONE);
                mAdapter.setMore(true);
                if(result!=null && result.length>0){
                    ArrayList<NewGoodsBean> list= ConvertUtils.array2List(result);
                    if( action == I.ACTION_DOWNLOAD ||action==I.ACTION_PULL_DOWN){
                        mAdapter.initData(list);
                    }else {
                        mAdapter.addData(list);
                    }

                    if(list.size()< I.PAGE_SIZE_DEFAULT){
                        mAdapter.setMore(false);
                    }else
                        mAdapter.setMore(true);
                }
            }
            @Override
            public void onError(String error) {
                sfl.setRefreshing(false);
                tvRefresh.setVisibility(View.GONE);
                CommonUtils.showLongToast(error);
                L.e("error: "+error);

            }
        });
    }





    @OnClick(R.id.backClickArea)
    public void onClick() {
        MFGT.finish(this);
    }
}

package com.example.a94896.fulicenter.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a94896.fulicenter.R;
import com.example.a94896.fulicenter.Views.SpaceItemDecoration;
import com.example.a94896.fulicenter.activity.MainActivity;
import com.example.a94896.fulicenter.adapter.BoutiqueAdapter;
import com.example.a94896.fulicenter.bean.BoutiqueBean;
import com.example.a94896.fulicenter.net.NetDao;
import com.example.a94896.fulicenter.net.OkHttpUtils;
import com.example.a94896.fulicenter.utils.CommonUtils;
import com.example.a94896.fulicenter.utils.ConvertUtils;
import com.example.a94896.fulicenter.utils.L;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Administrator on 2016/10/19.
 */
public class BoutiqueFragment extends BaseFragment {
    @BindView(R.id.rlv)
    TextView tvRefresh;
    @BindView(R.id.rvNewGoods)
    RecyclerView rlv;
    @BindView(R.id.srl)
    SwipeRefreshLayout sfl;
    LinearLayoutManager mLinearLayoutManager;
    MainActivity mContext;
    BoutiqueAdapter mAdapter;
    ArrayList<BoutiqueBean> mList;
    int pageId=1;
    public BoutiqueFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_new_goods, container, false);
        ButterKnife.bind(this, layout);
        mContext = (MainActivity) getContext();
        mList=new ArrayList<>();
        mAdapter=new BoutiqueAdapter(mContext,mList);
//        initView();
//        initData();
//        setListener();
        super.onCreateView(inflater,container,savedInstanceState);
        return layout;
    }

    /**
     * 创建一个监听器
     */
    @Override
    protected void setListener() {

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
                downLoadData();
            }
        });
    }

    @Override
    protected void initData() {
        downLoadData();
    }

    private void downLoadData() {
        NetDao.downloadBoutique(mContext, new OkHttpUtils.OnCompleteListener<BoutiqueBean[]>() {
            @Override
            public void onSuccess(BoutiqueBean[] result) {
                sfl.setRefreshing(false);//设置是否刷新
                tvRefresh.setVisibility(View.GONE);//隐藏刷新提示
                if(result != null && result.length>0){
                    ArrayList<BoutiqueBean> list = ConvertUtils.array2List(result);
                    mAdapter.initData(list);
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

    @Override
    protected void initView() {
        sfl.setColorSchemeColors(getResources().getColor(R.color.google_blue),
                getResources().getColor(R.color.google_yellow),
                getResources().getColor(R.color.google_red),
                getResources().getColor(R.color.google_green));
        mLinearLayoutManager = new LinearLayoutManager(mContext);
        rlv.setLayoutManager(mLinearLayoutManager);
        rlv.setHasFixedSize(true);
        rlv.setAdapter(mAdapter);
        rlv.addItemDecoration(new SpaceItemDecoration(16));

    }
}

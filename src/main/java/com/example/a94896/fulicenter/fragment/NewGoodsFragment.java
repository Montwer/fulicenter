package com.example.a94896.fulicenter.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a94896.fulicenter.I;
import com.example.a94896.fulicenter.R;
import com.example.a94896.fulicenter.activity.MainActivity;
import com.example.a94896.fulicenter.adapter.GoodsAdapter;
import com.example.a94896.fulicenter.bean.NewGoodsBean;
import com.example.a94896.fulicenter.net.NetDao;
import com.example.a94896.fulicenter.net.OkHttpUtils;
import com.example.a94896.fulicenter.utils.ConvertUtils;
import com.example.a94896.fulicenter.utils.L;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewGoodsFragment extends Fragment {


    @BindView(R.id.tv_refresh)
    TextView tvRefresh;
    @BindView(R.id.rvNewGoods)
    RecyclerView rvNewGoods;
    @BindView(R.id.srl)
    SwipeRefreshLayout srl;
    MainActivity mContext;
    GoodsAdapter mAdapter;
    ArrayList<NewGoodsBean>mList;
    int pageId=1;
    public NewGoodsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_new_goods, container, false);
        ButterKnife.bind(this, layout);
        mContext= (MainActivity) getContext();
        mList=new ArrayList<>();
        mAdapter=new GoodsAdapter(mList,mContext);
        initView();
        initData();
        return layout;
    }

    private void initData() {
        NetDao.downloadGoodsList(mContext, pageId, new OkHttpUtils.OnCompleteListener<NewGoodsBean[]>() {
            @Override
            public void onSuccess(NewGoodsBean[] result) {
                if(result!=null && result.length>0){
                    ArrayList<NewGoodsBean> list= ConvertUtils.array2List(result);
                    mAdapter.initData(list);
                }
            }

            @Override
            public void onError(String error) {
                L.e("error: "+error);

            }
        });
    }

    private void initView() {
        srl.setColorSchemeColors(getResources().getColor(R.color.google_blue),
                getResources().getColor(R.color.google_yellow),
                getResources().getColor(R.color.google_red),
                getResources().getColor(R.color.google_green));
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(mContext, I.COLUM_NUM);
        rvNewGoods.setLayoutManager(mGridLayoutManager);
        rvNewGoods.setHasFixedSize(true);
        rvNewGoods.setAdapter(mAdapter);
    }

}

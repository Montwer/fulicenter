package com.example.a94896.fulicenter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.a94896.fulicenter.R;
import com.example.a94896.fulicenter.bean.BoutiqueBean;
import com.example.a94896.fulicenter.utils.ImageLoader;
import com.example.a94896.fulicenter.utils.MFGT;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Administrator on 2016/10/19.
 */
public class BoutiqueAdapter extends Adapter<BoutiqueAdapter.BoutiqueViewHolder> {
    Context mContext;
    ArrayList<BoutiqueBean> mList;

    public BoutiqueAdapter(Context context, ArrayList<BoutiqueBean> list) {
        mContext = context;
        mList = new ArrayList<>();
        mList.addAll(list);
    }

    @Override
    public BoutiqueViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BoutiqueViewHolder
            holder = new BoutiqueViewHolder(View.inflate(mContext, R.layout.item_boutique, null));
        return holder;
    }

    @Override
    public void onBindViewHolder(BoutiqueViewHolder holder, int position) {
            BoutiqueViewHolder bh = (BoutiqueViewHolder) holder;
            BoutiqueBean mBoutiqueBean = mList.get(position);
            ImageLoader.downloadImg(mContext,bh.ivBoutiqueImage,mBoutiqueBean.getImageurl());
            bh.tvBoutiqueTitle.setText(mBoutiqueBean.getTitle());
            bh.tvBoutiqueName.setText(mBoutiqueBean.getName());
            bh.tvBoutiqueDescription.setText(mBoutiqueBean.getDescription());
            bh.mLayoutBoutiqueItem.setTag(mBoutiqueBean);
        }


    @Override
    public int getItemCount() {
        return mList != null?mList.size():0;
    }
    public void initData(ArrayList<BoutiqueBean>list) {
        if(mList!=null){
            mList.clear();
        }
        mList.addAll(list);
        notifyDataSetChanged();
    }
    class BoutiqueViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.ivBoutiqueImg)
        ImageView ivBoutiqueImage;
        @BindView(R.id.tvBoutiqueTitle)
        TextView tvBoutiqueTitle;
        @BindView(R.id.tvBoutiqueName)
        TextView tvBoutiqueName;
        @BindView(R.id.tvBoutiqueDescription)
        TextView tvBoutiqueDescription;
        @BindView(R.id.layout_Boutique)
        RelativeLayout mLayoutBoutiqueItem;

        BoutiqueViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
        @OnClick(R.id.layout_Boutique)
        public void onBoutiqueClick(){
            BoutiqueBean bean =(BoutiqueBean)mLayoutBoutiqueItem.getTag();
            MFGT.gotoBoutiqueChildActivity(mContext,bean);
        }
    }
}

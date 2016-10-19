package com.example.a94896.fulicenter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a94896.fulicenter.I;
import com.example.a94896.fulicenter.R;
import com.example.a94896.fulicenter.Views.FooterViewHolder;
import com.example.a94896.fulicenter.bean.BoutiqueBean;
import com.example.a94896.fulicenter.utils.ImageLoader;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Administrator on 2016/10/19.
 */
public class BoutiqueAdapter extends RecyclerView.Adapter {
    Context mContext;
    ArrayList<BoutiqueBean> mList;
    boolean isMore;

    public BoutiqueAdapter(Context context, ArrayList<BoutiqueBean> list) {
        mContext = context;
        mList = new ArrayList<>();
        mList.addAll(list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        if (viewType == I.TYPE_FOOTER) {
            holder = new FooterViewHolder(View.inflate(mContext, R.layout.item_footer, null));
        } else
            holder = new BoutiqueViewHolder(View.inflate(mContext, R.layout.fragment_boutique, null));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position) == I.TYPE_FOOTER){
           FooterViewHolder fh = (FooterViewHolder) holder;
            fh.tvFooter.setText(goFootString());

        }else {
            BoutiqueViewHolder bh = (BoutiqueViewHolder) holder;
            BoutiqueBean mBoutiqueBean = mList.get(position);
            ImageLoader.downloadImg(mContext,bh.ivBoutiqueImage,mBoutiqueBean.getImageurl());
            bh.tvBoutiqueTitle.setText(mBoutiqueBean.getTitle());
            bh.tvBoutiqueName.setText(mBoutiqueBean.getName());
            bh.tvBoutiqueDescription.setText(mBoutiqueBean.getDescription());
        }
    }

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean more) {
        isMore = more;
        notifyDataSetChanged();
    }

    private int goFootString() {
        return  isMore?R.string.load_more:R.string.no_more;
    }

    @Override
    public int getItemCount() {
        return mList != null?mList.size() + 1:1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return I.TYPE_FOOTER;
        } else
            return I.TYPE_ITEM;
    }

    public void initData(ArrayList<BoutiqueBean> list) {
        if(mList!=null){
            mList.clear();
        }
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(ArrayList<BoutiqueBean> list) {
        mList.addAll(list);
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

        BoutiqueViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}

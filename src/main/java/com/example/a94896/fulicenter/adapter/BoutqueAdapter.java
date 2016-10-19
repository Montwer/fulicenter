package com.example.a94896.fulicenter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.a94896.fulicenter.I;
import com.example.a94896.fulicenter.R;
import com.example.a94896.fulicenter.bean.BoutiqueBean;
import com.example.a94896.fulicenter.utils.ImageLoader;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 94896 on 2016/10/19.
 */

public class BoutqueAdapter extends Adapter {
    Context mContext;
    ArrayList<BoutiqueBean> mList;
    boolean isMore;



    public BoutqueAdapter(Context context, ArrayList<BoutiqueBean> list) {
        mContext = context;
        mList = new ArrayList<>();
        mList.addAll(list);
    }
    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean more) {
        isMore = more;
        notifyDataSetChanged();
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = null;
        if (viewType == I.TYPE_FOOTER) {
            holder = new GoodsAdapter.FooterViewHolder(LayoutInflater.from(mContext)
                    .inflate(R.layout.item_footer, parent, false));
        } else {
            holder = new ButiueViewHolder(LayoutInflater.from(mContext)
                    .inflate(R.layout.item_boutique, parent, false));
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof GoodsAdapter.FooterViewHolder){
            ((GoodsAdapter.FooterViewHolder) holder).tvFooter.setText(getFooterString());
        }
        if (holder instanceof ButiueViewHolder){
            BoutiqueBean boutiqueBean=mList.get(position);
            ImageLoader.downloadImg(mContext,((ButiueViewHolder) holder).ivBoutiqueImg,boutiqueBean.getImageurl());
            ((ButiueViewHolder)holder).tvBoutiqueTitle.setText(boutiqueBean.getTitle());
            ((ButiueViewHolder)holder).tvBoutiqueName.setText(boutiqueBean.getName());
            ((ButiueViewHolder)holder).tvBoutiqueDescription.setText(boutiqueBean.getDescription());
        }
    }
private  int getFooterString(){
    return isMore?R.string.load_more:R.string.no_more;
}
    @Override
    public int getItemCount() {
        return mList != null ? mList.size() + 1 : 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return I.TYPE_FOOTER;
        }
        return I.TYPE_ITEM;
    }

     class ButiueViewHolder extends ViewHolder {
        @BindView(R.id.ivBoutiqueImg)
        ImageView ivBoutiqueImg;
        @BindView(R.id.tvBoutiqueTitle)
        TextView tvBoutiqueTitle;
        @BindView(R.id.tvBoutiqueName)
        TextView tvBoutiqueName;
        @BindView(R.id.tvBoutiqueDescription)
        TextView tvBoutiqueDescription;
        @BindView(R.id.layout_boutique_item)
        RelativeLayout layoutBoutiqueItem;

         ButiueViewHolder(View view) {
             super(view);
             ButterKnife.bind(this, view);
        }
    }
}

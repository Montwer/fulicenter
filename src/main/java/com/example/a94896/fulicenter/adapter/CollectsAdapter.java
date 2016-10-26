package com.example.a94896.fulicenter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.a94896.fulicenter.I;
import com.example.a94896.fulicenter.R;
import com.example.a94896.fulicenter.Views.FooterViewHolder;
import com.example.a94896.fulicenter.bean.CollectBean;
import com.example.a94896.fulicenter.utils.ImageLoader;
import com.example.a94896.fulicenter.utils.MFGT;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Administrator on 2016/10/17.
 */
public class CollectsAdapter extends Adapter {
    Context mContext;
    ArrayList<CollectBean> mList;
    boolean isMore;
    int sortBy=I.SORT_BY_ADDTIME_DESC;


    public CollectsAdapter(Context context, ArrayList<CollectBean> list) {
        mList=new ArrayList<>();
        mList.addAll(list);
        mContext = context;
    }
    public void setSortBy(int sortBy) {
        this.sortBy = sortBy;
        notifyDataSetChanged();
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
            holder = new FooterViewHolder(View.inflate(mContext, R.layout.item_footer, null));
        } else {
            holder = new ColectsViewHolder(View.inflate(mContext, R.layout.item_collects, null));
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(getItemViewType(position)==I.TYPE_FOOTER){
            FooterViewHolder mGoodsViewHolder= (FooterViewHolder) holder;
            mGoodsViewHolder.tvFooter.setText(getFootString());
        }else {
            ColectsViewHolder mGoodsViewHolder= (ColectsViewHolder) holder;
            CollectBean mCollectBean=mList.get(position);
            ImageLoader.downloadImg(mContext,mGoodsViewHolder.ivGoodsThumb,mCollectBean.getGoodsThumb());
            mGoodsViewHolder.tvGoodsName.setText(mCollectBean.getGoodsName());
            mGoodsViewHolder.layoutGoods.setTag(mCollectBean.getGoodsId());

        }
    }
    public int getFootString() {

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

    public void initData(ArrayList<CollectBean> list) {
        if(mList!=null){
            mList.clear();
        }
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(ArrayList<CollectBean> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    class ColectsViewHolder extends ViewHolder{
        @BindView(R.id.ivGoodsThumb)
        ImageView ivGoodsThumb;
        @BindView(R.id.tvGoodsName)
        TextView tvGoodsName;
        @BindView(R.id.iv_collect_del)
        ImageView mIvCollectDel;
        @BindView(R.id.layout_goods)
        RelativeLayout layoutGoods;


        ColectsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
        @OnClick(R.id.layout_goods)
        public void onGoodsItemClick(){
            int goodsId=(int)layoutGoods.getTag();
            MFGT.gotoGoodsDetailActivity(mContext,goodsId);
        }
    }

}

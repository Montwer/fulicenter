package com.example.a94896.fulicenter.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.a94896.fulicenter.R;
import com.example.a94896.fulicenter.bean.CategoryChildBean;
import com.example.a94896.fulicenter.bean.CategoryGroupBean;
import com.example.a94896.fulicenter.utils.ImageLoader;
import com.example.a94896.fulicenter.utils.MFGT;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CategoryAdapter extends BaseExpandableListAdapter {
    Context mContext;
    List<CategoryGroupBean> mGroupList;
    List<ArrayList<CategoryChildBean>> mChildList;

    public CategoryAdapter(Context context, List<CategoryGroupBean> groupList,
                           List<ArrayList<CategoryChildBean>> childList) {
        mContext = context;
        mGroupList = new ArrayList<>();
        mGroupList.addAll(groupList);
        mChildList = new ArrayList<>();
        mChildList.addAll(childList);
    }
    @Override
    public int getGroupCount() {
        return mGroupList != null ? mGroupList.size() : 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mChildList != null && mChildList.get(groupPosition) != null ?
                mChildList.get(groupPosition).size() : 0;
    }

    @Override
    public CategoryGroupBean getGroup(int groupPosition) {
        return mGroupList != null ? mGroupList.get(groupPosition) : null;

    }

    @Override
    public CategoryChildBean getChild(int groupPosition, int childPosition) {
        if (mChildList.get(groupPosition) != null
                && mChildList.get(groupPosition).get(childPosition) != null)
            return mChildList.get(groupPosition).get(childPosition);
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View view, ViewGroup parent) {
        GroupViewHolder holder;
        if (view == null) {
            view = View.inflate(mContext, R.layout.item_category_group, null);
            holder = new GroupViewHolder(view);
            holder.ivGroupThumb = (ImageView) view.findViewById(R.id.iv_group_thumb);
            holder.tvGroupName = (TextView) view.findViewById(R.id.tv_group_name);
            holder.ivIndicator = (ImageView) view.findViewById(R.id.iv_indicator);
            view.setTag(holder);
        } else {
            holder = (GroupViewHolder) view.getTag();
        }
        CategoryGroupBean group = getGroup(groupPosition);

        holder.tvGroupName.setText(group.getName());
        if (group != null) {
            ImageLoader.downloadImg(mContext, holder.ivGroupThumb, group.getImageUrl());
            holder.ivIndicator.setImageResource(isExpanded ? R.mipmap.expand_off : R.mipmap.expand_on);
        }
        return view;
    }

    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup parent) {
        ChildViewHolder holder;
        if (view == null) {
            view = View.inflate(mContext, R.layout.item_cateogry_child, null);
            holder = new ChildViewHolder(view);
            holder.layoutCategoryChild = (RelativeLayout) view.findViewById(R.id.layout_category_child);
            holder.ivCategoryChildThumb = (ImageView) view.findViewById(R.id.iv_category_child_thumb);
            holder.tvCategoryChildName = (TextView) view.findViewById(R.id.tv_category_child_name);
            view.setTag(holder);
        } else {
            holder = (ChildViewHolder) view.getTag();
        }
        final CategoryChildBean child = getChild(groupPosition, childPosition);
        if (child != null) {
            ImageLoader.downloadImg(mContext, holder.ivCategoryChildThumb, child.getImageUrl());
            holder.tvCategoryChildName.setText(child.getName());
            holder.layoutCategoryChild.setOnClickListener(new View.OnClickListener() {
                ArrayList<CategoryChildBean>list=mChildList.get(groupPosition);
                String groupName=mGroupList.get(groupPosition).getName();

                @Override
                public void onClick(View view) {
                    MFGT.gotoCategoryChildActivity(mContext,child.getId());
//                    mContext.startActivity(new Intent(mContext, CategoryChildActivity.class)
//                            .putExtra(I.CategoryChild.CAT_ID, child.getId())
//                            .putExtra(I.CategoryGroup.NAME, mGroupList.get(groupPosition).getName())
//                            .putExtra("childList", mChildList.get(groupPosition)));
                }
            });
        }
        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public void addAll(List<CategoryGroupBean> mGroupList, List<ArrayList<CategoryChildBean>> mChildList) {
        this.mGroupList.clear();
        this.mGroupList.addAll(mGroupList);
        this.mChildList.clear();
        this.mChildList.addAll(mChildList);
        notifyDataSetChanged();
    }

    public void initData(ArrayList<CategoryGroupBean> groupList,
                         ArrayList<ArrayList<CategoryChildBean>> childList) {
    if (mGroupList!=null){
        mGroupList.clear();
    }
        mGroupList.addAll(groupList);
        if (mChildList!=null){
            mChildList.clear();
        }
        mChildList.addAll(childList);
        notifyDataSetChanged();
    }

    class GroupViewHolder {
        @BindView(R.id.tv_group_name)
        TextView tvGroupName;
        @BindView(R.id.iv_indicator)
        ImageView ivIndicator;
        @BindView(R.id.iv_group_thumb)
        ImageView ivGroupThumb;

        GroupViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

 class ChildViewHolder {
        @BindView(R.id.iv_category_child_thumb)
        ImageView ivCategoryChildThumb;
        @BindView(R.id.tv_category_child_name)
        TextView tvCategoryChildName;
        @BindView(R.id.layout_category_child)
        RelativeLayout layoutCategoryChild;

        ChildViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

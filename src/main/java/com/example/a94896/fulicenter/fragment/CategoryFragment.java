package com.example.a94896.fulicenter.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.example.a94896.fulicenter.R;
import com.example.a94896.fulicenter.activity.MainActivity;
import com.example.a94896.fulicenter.adapter.CategoryAdapter;
import com.example.a94896.fulicenter.bean.CategoryChildBean;
import com.example.a94896.fulicenter.bean.CategoryGroupBean;
import com.example.a94896.fulicenter.net.NetDao;
import com.example.a94896.fulicenter.net.OkHttpUtils;
import com.example.a94896.fulicenter.utils.ConvertUtils;
import com.example.a94896.fulicenter.utils.L;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CategoryFragment extends BaseFragment {
    ArrayList<CategoryGroupBean> mGroupList;
    ArrayList<ArrayList<CategoryChildBean>> mChildList;
    CategoryAdapter mAdapter;
    MainActivity mContext;
    @BindView(R.id.elvCategory)
    ExpandableListView elvCategory;
    int groupCount;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_category, container,false);
        ButterKnife.bind(this, layout);
        mContext= (MainActivity) getContext();
        mChildList=new ArrayList<>();
        mGroupList=new ArrayList<>();
        mAdapter=new CategoryAdapter(mContext,mGroupList,mChildList);
        super.onCreateView(inflater, container, savedInstanceState);
        return layout;
    }

    @Override
    protected void initView() {
        elvCategory.setGroupIndicator(null);
        elvCategory.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        downloadGroup();
    }

    private void downloadGroup() {
        NetDao.downloadCategoryGroupList(mContext, new OkHttpUtils.OnCompleteListener<CategoryGroupBean[]>() {
            @Override
            public void onSuccess(CategoryGroupBean[] result) {
                L.e("downloadGroup result" +result);
                if (result != null&&result.length>0) {
                    ArrayList<CategoryGroupBean> groupList = ConvertUtils.array2List(result);
                        L.e("groupList=" + groupList.size());
                        mGroupList.addAll(groupList);
                        for (int i=0;i<groupList.size();i++){
                            mChildList.add(new ArrayList<CategoryChildBean>());
                            CategoryGroupBean g=groupList.get(i);
                            downloadChilp(g.getId(),i);
                        }
                }
            }
            @Override
            public void onError(String error) {
                L.e("error="+error);
            }
        });
    }

    @Override
    protected void setListener() {

    }

    private void downloadChilp(int id,final int index) {
        NetDao.downloadCategoryChildList(mContext,id, new OkHttpUtils.OnCompleteListener<CategoryChildBean[]>() {
            @Override
            public void onSuccess(CategoryChildBean[] result) {
                groupCount++;
                L.e("downloadChilp result" +result);
                if (result != null&&result.length>0) {
                    ArrayList<CategoryChildBean> childList = ConvertUtils.array2List(result);
                    L.e("childList=" + childList.size());
                    mChildList.set(index,childList);

                }
                if (groupCount==mGroupList.size()){
                mAdapter.initData(mGroupList,mChildList);
                 }
            }

            @Override
            public void onError(String error) {
                L.e("error="+error);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        OkHttpUtils.release();
    }
}

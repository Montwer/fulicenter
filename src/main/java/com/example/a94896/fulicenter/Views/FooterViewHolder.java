package com.example.a94896.fulicenter.Views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.a94896.fulicenter.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 94896 on 2016/10/19.
 */

public class FooterViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvFooter)
        public
        TextView tvFooter;

        public FooterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

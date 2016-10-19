package com.example.a94896.fulicenter.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.a94896.fulicenter.R;
import com.example.a94896.fulicenter.fragment.BoutiqueFragment;
import com.example.a94896.fulicenter.fragment.NewGoodsFragment;
import com.example.a94896.fulicenter.utils.L;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {

    @BindView(R.id.layout_new_good)
    RadioButton layoutNewGood;
    @BindView(R.id.layout_boutique)
    RadioButton layoutBoutique;
    @BindView(R.id.layout_category)
    RadioButton layoutCategory;
    @BindView(R.id.layout_cart)
    RadioButton layoutCart;
    @BindView(R.id.layout_personal_center)
    RadioButton layoutPersonalCenter;
    @BindView(R.id.tvCartHint)
    TextView tvCartHint;

    int index;
    int currentIndex;

    RadioButton[] radioButtons;
    Fragment[] fragments;
    NewGoodsFragment mNewGoodsFragment;
    BoutiqueFragment mBoutiqueFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        L.i("MainActivity");
        initView();
        initFragment();
    }

    private void initFragment() {
        fragments=new Fragment[5];
        mNewGoodsFragment=new NewGoodsFragment();
        mBoutiqueFragment=new BoutiqueFragment();
        fragments[0]=mNewGoodsFragment;
        fragments[1]=mBoutiqueFragment;
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container,mNewGoodsFragment)
                .add(R.id.fragment_container,mBoutiqueFragment)
                .hide(mBoutiqueFragment)
                .show(mNewGoodsFragment)
                .commit();

    }

    private void initView() {
        radioButtons=new RadioButton[5];
        radioButtons[0]=layoutNewGood;
        radioButtons[1]=layoutBoutique;
        radioButtons[2]=layoutCategory;
        radioButtons[3]=layoutCart;
        radioButtons[4]=layoutPersonalCenter;
    }

    public void onCheckedChange(View v) {
        switch (v.getId()){
            case  R.id.layout_new_good:
                index=0;
                break;
            case  R.id.layout_boutique:
                index=1;
                break;
            case  R.id.layout_category:
                index=2;
                break;
            case  R.id.layout_cart:
                index=3;
                break;
            case  R.id.layout_personal_center:
                index=4;
                break;
        }
        setFragment();

    }

    private void setFragment() {
        if (index!=currentIndex) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.hide(fragments[currentIndex]);
            if (!fragments[index].isAdded()){
                ft.add(R.id.fragment_container,fragments[index]);
            }
            ft.show(fragments[index]).commit();
        }
        setRadioButtonStatus();
        currentIndex=index;
    }

    private void setRadioButtonStatus() {
        for(int i=0;i<radioButtons.length;i++){
            if(i==index){
                radioButtons[i].setChecked(true);
            }else
                radioButtons[i].setChecked(false);

        }
    }

}

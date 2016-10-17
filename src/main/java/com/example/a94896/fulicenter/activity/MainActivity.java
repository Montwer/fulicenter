package com.example.a94896.fulicenter.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.a94896.fulicenter.R;
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
    @BindView(R.id.textview)
    TextView textview;
    @BindView(R.id.layout_personal_center)
    RadioButton layoutPersonalCenter;
    int index;
    RadioButton[]rbs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        L.i("MainActivity onCreate");
        initView();
    }
    private void initView() {
        rbs=new RadioButton[5];
        rbs[0]=layoutNewGood;
        rbs[1]=layoutBoutique;
        rbs[2]=layoutCategory;
        rbs[3]=layoutCart;
        rbs[4]=layoutPersonalCenter;
    }
    public void onCheckedChange(View v){
        switch (v.getId()){
            case R.id.layout_new_good:
                index=0;
                break;
            case R.id.layout_boutique:
                index=1;
                break;
            case R.id.layout_category:
                index=2;
                break;
            case R.id.layout_cart:
                index=3;
                break;
            case R.id.layout_personal_center:
                index=4;
                break;
        }
        setRadioButtonStatus();
    }

    private void setRadioButtonStatus() {
        L.e("index="+index);
        for (int i=0;i<rbs.length;i++){
            if (i==index){
                rbs[i].setChecked(true);
            }else {
                rbs[i].setChecked(false);
            }

        }
    }
}

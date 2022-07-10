package com.datapay.onecard.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.datapay.onecard.R;
import com.datapay.onecard.activity.OneCardActivity;
import com.datapay.onecard.activity.OneCardWebActivity;


public class OneCardNoticeFragment extends OneCardBaseFragment implements View.OnClickListener {

    public static OneCardNoticeFragment newInstance() {
        OneCardNoticeFragment fragment = new OneCardNoticeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    int getLayoutId() {
        return R.layout.fragment_one_card_notice;
    }

    @Override
    void initView() {
        View one_card_fragment_idcrad_confirm= oneCardFragmentContainer.findViewById(R.id.one_card_fragment_idcrad_confirm);
        one_card_fragment_idcrad_confirm.setOnClickListener(this::onClick);
        View one_card_setting_btn= oneCardFragmentContainer.findViewById(R.id.one_card_setting_btn);
        one_card_setting_btn.setOnClickListener(this::onClick);

        View one_card_notice_back= oneCardFragmentContainer.findViewById(R.id.one_card_notice_back);
        one_card_notice_back.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View view) {
        if(getActivity()!=null){
            int id = view.getId();
            if(id==R.id.one_card_fragment_idcrad_confirm){
                ((OneCardActivity)getActivity()).showFragment(3);
            }else if(id==R.id.one_card_setting_btn){
                ((OneCardActivity)getActivity()).showFragment(2);
//                Intent intent=new Intent(getActivity(), OneCardWebActivity.class);
//                intent.putExtra("one_card_html_url","https://m.ctrip.com/html5/");
//                startActivity(intent);
            }else if(id==R.id.one_card_notice_back){
                getActivity().onBackPressed();
            }
        }
    }
}
package com.datapay.onecard.fragment;

import android.view.View;

import com.datapay.onecard.R;
import com.datapay.onecard.activity.OneCardActivity;


public class OneCardAuthorizationFragment extends OneCardBaseFragment implements View.OnClickListener {

    public static OneCardAuthorizationFragment newInstance() {
        OneCardAuthorizationFragment fragment = new OneCardAuthorizationFragment();
        return fragment;
    }

    @Override
    int getLayoutId() {
        return R.layout.fragment_one_card_authorization;
    }

    @Override
    void initView() {
       View one_card_authorization_confirm= oneCardFragmentContainer.findViewById(R.id.one_card_authorization_confirm);
        one_card_authorization_confirm.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View view) {
        if(getActivity()!=null){
            ((OneCardActivity)getActivity()).showFragment(1);
        }
    }
}
package com.datapay.onecard.fragment;

import android.view.View;
import android.widget.Toast;
import com.datapay.onecard.R;

public class OneCardInputFragment extends OneCardBaseFragment implements View.OnClickListener {

    public static OneCardInputFragment newInstance() {
        OneCardInputFragment fragment = new OneCardInputFragment();
        return fragment;
    }

    @Override
    int getLayoutId() {
        return R.layout.fragment_one_card_input;
    }

    @Override
    void initView() {
        View one_card_fragment_go_camera= oneCardFragmentContainer.findViewById(R.id.one_card_fragment_go_camera);
        one_card_fragment_go_camera.setOnClickListener(this::onClick);
        View one_card_fragment_input_next= oneCardFragmentContainer.findViewById(R.id.one_card_fragment_input_next);
        one_card_fragment_input_next.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View view) {
        if(getActivity()!=null){
            int id = view.getId();
            if(id==R.id.one_card_fragment_go_camera){
                Toast.makeText(getActivity(),"身份证扫描",Toast.LENGTH_LONG).show();
            }else if(id==R.id.one_card_fragment_input_next){
                Toast.makeText(getActivity(),"去活体检测",Toast.LENGTH_LONG).show();
            }
        }
    }
}
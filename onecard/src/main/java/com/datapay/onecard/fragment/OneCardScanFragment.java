package com.datapay.onecard.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.datapay.onecard.R;


public class OneCardScanFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";


    public OneCardScanFragment() {
    }

    public static OneCardScanFragment newInstance() {
        OneCardScanFragment fragment = new OneCardScanFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
           String mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_one_card_authorization, container, false);
    }
}
package com.datapay.onecard.activity;

import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.datapay.onecard.R;
import com.datapay.onecard.fragment.OneCardAuthorizationFragment;
import com.datapay.onecard.fragment.OneCardInputFragment;
import com.datapay.onecard.fragment.OneCardNoticeFragment;
import com.datapay.onecard.fragment.OneCardScanFragment;
import com.datapay.onecard.fragment.OneCardCameraFragment;
import com.datapay.onecard.utils.PermissionUtil;
import com.datapay.onecard.utils.StatusBarUtil;

import java.util.List;

public class OneCardActivity extends OneCardBaseActivity {

    private OneCardAuthorizationFragment mOneCardAuthorizationFragment;
    private OneCardInputFragment mOneCardInputFragment;
    private OneCardNoticeFragment mOneCardNoticeFragment;
    private OneCardScanFragment mOneCardScanFragment;
    private OneCardCameraFragment mOneCardCameraFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();//严格模式，虚拟机政策，构建器
        StrictMode.setVmPolicy(builder.build());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//构建器SDK版本大于或等于构建器代码版本
            builder.detectFileUriExposure();//构建器就侦察出FileUriExposure
        }
    }

    @Override
    int getLayoutId() {
        return R.layout.activity_one_card;
    }

    @Override
    void initOneCard() {
        View one_card_top = findViewById(R.id.one_card_top);
        ViewGroup.LayoutParams layoutParams = one_card_top.getLayoutParams();
        layoutParams.height = StatusBarUtil.getStatusBarHeight(this);
        one_card_top.setLayoutParams(layoutParams);
        showFragment(0);
    }

    public void showFragment(int index) {
        hideFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (index == 0) {
            if(mOneCardAuthorizationFragment==null){
                mOneCardAuthorizationFragment = OneCardAuthorizationFragment.newInstance();
            }
            transaction.add(R.id.one_card_fragment_container, mOneCardAuthorizationFragment);
        } else if (index == 1) {
            if(mOneCardNoticeFragment==null){
                mOneCardNoticeFragment = OneCardNoticeFragment.newInstance();
            }
            transaction.add(R.id.one_card_fragment_container, mOneCardNoticeFragment);
        } else if (index == 2) {
            if(mOneCardCameraFragment==null){
                mOneCardCameraFragment = OneCardCameraFragment.newInstance();
            }
            transaction.add(R.id.one_card_fragment_container, mOneCardCameraFragment);
        } else if (index == 3) {
            if(mOneCardInputFragment==null){
                mOneCardInputFragment = OneCardInputFragment.newInstance();
            }
            transaction.add(R.id.one_card_fragment_container, mOneCardInputFragment);
        }else if (index == 4) {
            if(mOneCardScanFragment==null){
                mOneCardScanFragment = OneCardScanFragment.newInstance();
            }
            transaction.add(R.id.one_card_fragment_container, mOneCardScanFragment);
        }
        transaction.commitAllowingStateLoss();
    }

    private void hideFragment() {
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        List<Fragment> fragments = fragmentManager.getFragments();
        for (int i = 0; i <fragments.size(); i++) {
            Fragment fragment= fragments.get(i);
            if (fragment!=null&&!fragment.isHidden()) {
                transaction.hide(fragment);
                transaction.commitAllowingStateLoss();
            }
        }
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        List<Fragment> fragments = fragmentManager.getFragments();
        if(fragments.size()>1){
            transaction.remove(fragments.get(fragments.size()-1)).show(fragments.get(fragments.size()-2));
            transaction.commitAllowingStateLoss();
        }else {
            super.onBackPressed();
        }
    }

    private void alertPermissionsDialog(String tips) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("温馨提示")
                .setMessage(tips)
                .setNegativeButton("取消", (dialog1, which) -> dialog1.dismiss())
                .setPositiveButton("确定", (dialog12, which) -> {
                    dialog12.dismiss();
                    PermissionUtil.goToSettingPage(OneCardActivity.this);
                }).create();
        dialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == OneCardCameraFragment.STORAGE_PERMISSION_CODE) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    alertPermissionsDialog("请先授予app读写权限");
                    return;
                }
            }
        } else if (requestCode == OneCardCameraFragment.CAMERA_PERMISSION_CODE) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    alertPermissionsDialog("请先授予app拍照权限");
                    return;
                }
            }
        }
    }
}
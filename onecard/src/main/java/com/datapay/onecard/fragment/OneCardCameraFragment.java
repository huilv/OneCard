package com.datapay.onecard.fragment;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import com.datapay.onecard.R;
import com.datapay.onecard.utils.BitmapUtil;

import java.io.File;


public class OneCardCameraFragment extends OneCardBaseFragment implements View.OnClickListener {

    private static String[] PERMISSIONS_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private static String[] PERMISSIONS_CAMERA = {Manifest.permission.CAMERA};
    public static int STORAGE_PERMISSION_CODE = 1;
    public static int CAMERA_PERMISSION_CODE = 2;

    private static final int REQUEST_IMAGE_OPEN = 2;
    private static final int REQUEST_CAMERA_OPEN = 3;
    private ImageView one_card_album_pic;
    private String mFilePath;

    public static OneCardCameraFragment newInstance() {
        OneCardCameraFragment fragment = new OneCardCameraFragment();
        return fragment;
    }

    @Override
    int getLayoutId() {
        return R.layout.fragment_one_card_camera;
    }

    @Override
    void initView() {
        View one_card_setting_back = oneCardFragmentContainer.findViewById(R.id.one_card_setting_back);
        one_card_setting_back.setOnClickListener(this::onClick);
        View one_card_camera_take_pic = oneCardFragmentContainer.findViewById(R.id.one_card_camera_take_pic);
        one_card_camera_take_pic.setOnClickListener(this::onClick);
        View one_card_camera_album = oneCardFragmentContainer.findViewById(R.id.one_card_camera_album);
        one_card_camera_album.setOnClickListener(this::onClick);
        one_card_album_pic = oneCardFragmentContainer.findViewById(R.id.one_card_album_pic);
    }

    @Override
    public void onClick(View view) {
        if (getActivity() != null) {
            int id = view.getId();
            if (id == R.id.one_card_setting_back) {
//                getActivity().onBackPressed();

                ProgressDialog    dialog = ProgressDialog.show(getContext(), "", "正在加载中。。。", true);
                dialog.show();
            } else if (id == R.id.one_card_camera_take_pic) {
                startCamera();
            } else if (id == R.id.one_card_camera_album) {
                openAlbum();
            }
        }
    }

    private void openAlbum() {
        boolean canOpenAlbum=true;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                canOpenAlbum=false;
                ActivityCompat.requestPermissions(getActivity(), PERMISSIONS_STORAGE, STORAGE_PERMISSION_CODE);
            }
        }
        if(canOpenAlbum) {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            startActivityForResult(intent, REQUEST_IMAGE_OPEN);
        }
    }

    private void startCamera() {
        boolean canOpenCamera=true;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                canOpenCamera=false;
                ActivityCompat.requestPermissions(getActivity(), PERMISSIONS_STORAGE, STORAGE_PERMISSION_CODE);
            }else  if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                canOpenCamera=false;
                ActivityCompat.requestPermissions(getActivity(), PERMISSIONS_CAMERA, CAMERA_PERMISSION_CODE);
            }
        }
        if(canOpenCamera) {
            mFilePath = BitmapUtil.getInstance().getCameraPath(getContext());
            File outFile = new File(mFilePath);
            //解决Android11无法拍照存储问题
            Uri uri;
             if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
                 uri= FileProvider.getUriForFile(getActivity(), "com.datapay.onecard.fileprovider", outFile);
            } else {
              uri=  Uri.fromFile(outFile);
            }
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            startActivityForResult(intent, REQUEST_CAMERA_OPEN);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_OPEN) {
            try {
                Uri uri = data.getData();
                String path = BitmapUtil.getRealPathFromUri(getContext(), uri);
                Bitmap bitmap = BitmapFactory.decodeStream(getContext().getContentResolver().openInputStream(uri));
                one_card_album_pic.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode == REQUEST_CAMERA_OPEN) {
            final Bitmap bitmap = loadingImageBitmap(mFilePath);
            one_card_album_pic.setImageBitmap(bitmap);
        }
    }

    public Bitmap loadingImageBitmap(String imagePath) {
        final int width = getActivity().getWindowManager().getDefaultDisplay().getWidth();
        final int height = getActivity().getWindowManager().getDefaultDisplay().getHeight();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeFile(imagePath, options);
            int widthRaio = (int) Math.ceil(options.outWidth/(float)width);
            int heightRaio = (int) Math.ceil(options.outHeight/(float)height);
            if (widthRaio>1&&heightRaio>1){
                if (widthRaio>heightRaio){
                    options.inSampleSize = widthRaio;
                }else {
                    options.inSampleSize = heightRaio;
                }
            }
            options.inJustDecodeBounds = false;
            bitmap = BitmapFactory.decodeFile(imagePath, options);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
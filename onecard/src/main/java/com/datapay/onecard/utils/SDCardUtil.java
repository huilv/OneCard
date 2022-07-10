package com.datapay.onecard.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;

public class SDCardUtil {
    public static String getInnerSDCardPath(Context context) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File external = context.getExternalFilesDir(null);
            if (external != null) {
                return external.getAbsolutePath();
            }
        }
        return context.getFilesDir().getAbsolutePath();
    }
}

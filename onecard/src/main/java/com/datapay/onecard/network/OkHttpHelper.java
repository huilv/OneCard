package com.datapay.onecard.network;

import android.os.Handler;
import android.os.Looper;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpHelper {

    public final static int HTTP_SUCCESS = 1; // 成功
    public final static int HTTP_FAILURE = 0; // 失败

    private static final OkHttpClient client = new OkHttpClient();
    private Call call;
    private Request request;
    private static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");
    private Handler mDelivery;

    static {
        client.newBuilder().connectTimeout(30, TimeUnit.SECONDS);
    }

    private OkHttpHelper() {
        mDelivery = new Handler(Looper.getMainLooper());
    }

    private static OkHttpHelper instance = new OkHttpHelper();

    public static OkHttpHelper getInstance() {
        return instance;
    }

    /**
     * @param url
     * @param object
     */
    public void postAsync(String url, Object object, final IResultCallBack iResultCallBack) {
        String body = AesHelper.getEncryptBody(JsonHelper.getInstance().toJson(object));
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_MARKDOWN, body);
        request = new Request.Builder()
                .cacheControl(new CacheControl.Builder().noStore().build())
                .addHeader("x-cipher-spec", "1")
                .post(requestBody)
                .url(url)
                .build();
        call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                sendFailedStringCallback(iResultCallBack, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String data = AesHelper.getDecryptBody(response.body().string());
                sendSuccessResultCallback(iResultCallBack, data);
            }
        });

    }

    private void sendFailedStringCallback(final IResultCallBack iResultCallBack, final Exception e) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                iResultCallBack.getResult(HTTP_FAILURE, "request failure");
            }
        });
    }

    private void sendSuccessResultCallback(final IResultCallBack iResultCallBack, final Object object) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                iResultCallBack.getResult(HTTP_SUCCESS, object);
            }
        });
    }

    public interface IResultCallBack {
        void getResult(int code, Object object);
    }
}

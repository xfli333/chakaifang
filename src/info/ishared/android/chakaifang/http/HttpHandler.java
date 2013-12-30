package info.ishared.android.chakaifang.http;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import info.ishared.android.chakaifang.AppConfig;

public class HttpHandler<T> extends Handler {

    private HttpEventListener<T> mHttpEventListener;

    public HttpHandler(HttpEventListener<T> httpEventListener) {
        this.mHttpEventListener = httpEventListener;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case AbstractHttpController.MSG_NETWORK_ERROR:
                Log.e(AppConfig.TAG, "HttpHandler:network error");
                mHttpEventListener.onNetworkError();
                break;

            case AbstractHttpController.MSG_SUCCESS:
                Log.d(AppConfig.TAG, "HttpHandler:success");
                T response = (T) msg.getData().getSerializable("response");
                mHttpEventListener.onRequestFinish(response);
                break;

            case AbstractHttpController.MSG_RESPONSE_DATA_ERROR:
                Log.d(AppConfig.TAG, "HttpHandler:data error");
                mHttpEventListener.onResponseError();
                break;

            case AbstractHttpController.MSG_NETWORK_ABORT:
                Log.e(AppConfig.TAG, "HttpHandler:network abort");
                mHttpEventListener.onNetworkAbort();
                break;

        }
    }
}

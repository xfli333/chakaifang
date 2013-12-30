package info.ishared.android.chakaifang.http;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import info.ishared.android.chakaifang.AppConfig;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * ????GUI???????HTTP????HTTP?????????????T?
 *
 * @param <T>
 */
public abstract class AbstractHttpController<T extends Serializable> {


    public static final int MSG_NETWORK_ABORT = 2;
    public static final int MSG_NETWORK_ERROR = 3;
    public static final int MSG_RESPONSE_DATA_ERROR = 4;
    public static final int MSG_SUCCESS = 1;
    private Class<? extends T> clazz;
    private Handler mGuiHandler;
    private HttpConnection mHttpConnection;

    ExecutorService executorService;

    public AbstractHttpController(Class<? extends T> clazz) {
        this.clazz = clazz;
    }

    public void abortConnection() {
        mHttpConnection.abort();
    }

    /**
     */
    protected void sendPostRequest(final String url, final String encoder, final Map<String, String> paramValue, HttpEventListener<T> httpEventListener) {
        executorService = Executors.newFixedThreadPool(2);
        mHttpConnection = new HttpConnection();
        mGuiHandler = new HttpHandler<T>(httpEventListener);
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String response = mHttpConnection.post(url, paramValue, encoder);
                    sendResponseMessage(response);
                } catch (NetworkingException e) {
                    Message abortMsg = new Message();
                    abortMsg.what = MSG_NETWORK_ABORT;
                    mGuiHandler.sendMessage(abortMsg);
                } catch (NetworkingAbortException e) {
                    Message networkErrorMsg = new Message();
                    networkErrorMsg.what = MSG_NETWORK_ERROR;
                    mGuiHandler.sendMessage(networkErrorMsg);
                }
            }
        });
        executorService.shutdown();
    }

    protected void sendGetRequest(final String url, final String encoder, HttpEventListener<T> httpEventListener) {
        executorService = Executors.newFixedThreadPool(2);
        mHttpConnection = new HttpConnection();
        mGuiHandler = new HttpHandler<T>(httpEventListener);
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String response = mHttpConnection.doGet(url, encoder);
                    sendResponseMessage(response);
                } catch (NetworkingException e) {
                    Message abortMsg = new Message();
                    abortMsg.what = MSG_NETWORK_ABORT;
                    mGuiHandler.sendMessage(abortMsg);
                } catch (NetworkingAbortException e) {
                    Message networkErrorMsg = new Message();
                    networkErrorMsg.what = MSG_NETWORK_ERROR;
                    mGuiHandler.sendMessage(networkErrorMsg);
                }
            }
        });
        executorService.shutdown();
    }

    private void sendResponseMessage(String response) {
        try {
            Message successMsg = new Message();
            successMsg.what = MSG_SUCCESS;
            Bundle b = new Bundle();
            b.putSerializable("response", response);
            successMsg.setData(b);
            mGuiHandler.sendMessage(successMsg);

        } catch (Exception e) {
            Log.e(AppConfig.TAG, "AbstractHttpController:response converting error");
            e.printStackTrace();

            Message dataErrorMsg = new Message();
            dataErrorMsg.what = MSG_RESPONSE_DATA_ERROR;
            mGuiHandler.sendMessage(dataErrorMsg);
        }
    }
}

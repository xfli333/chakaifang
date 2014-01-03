package info.ishared.android.chakaifang.http;

/**
 * HTTP事件监听器。与GUI运行在同一个线程下。
 */
public interface HttpEventListener<T> {

    public void onNetworkAbort();

    public void onNetworkError();

    public void onRequestFinish(T response);

    /**
     * 响应错误。 比如响应字符串为空。
     */
    public void onResponseError();
}

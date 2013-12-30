package info.ishared.android.chakaifang.http;

/**
 * 中断网络请求引发的异常。
 */
public class NetworkingAbortException extends Exception {

    private static final long serialVersionUID = 1L;

    public NetworkingAbortException() {
        super();
    }

    public NetworkingAbortException(String detailMessage) {
        super(detailMessage);
    }

    public NetworkingAbortException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public NetworkingAbortException(Throwable throwable) {
        super(throwable);
    }
}

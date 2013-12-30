package info.ishared.android.chakaifang.http;

/**
 * 网络异常。
 */
class NetworkingException extends Exception {

    private static final long serialVersionUID = 1L;

    public NetworkingException() {
        super();
    }

    public NetworkingException(String detailMessage) {
        super(detailMessage);
    }

    public NetworkingException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public NetworkingException(Throwable throwable) {
        super(throwable);
    }

}

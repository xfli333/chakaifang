package info.ishared.android.chakaifang.http;

import android.util.Log;
import info.ishared.android.chakaifang.AppConfig;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * HTTP连接工具。应 与GUI运行在不同线程下。
 */
public class HttpConnection {

    private boolean mAbort;// 是否主动关闭连接
    private HttpClient mClient;
    private HttpPost mPost;


    public void abort() {
        mAbort = true;
        Log.e(AppConfig.TAG, "HttpConnection:abort request");
        if (mPost != null) {
            mPost.abort();
        }
        closeConnection();
    }

    /**
     * 主动关闭网络连接。
     */
    public void closeConnection() {
        if (mClient != null) {
            Log.i(AppConfig.TAG, "HttpConnection:close connection");
            mClient.getConnectionManager().shutdown();
        }
    }

    /**
     * 发起POST请求，取得响应字符串。如果HTTP StatusCode不是200，则返回null。
     *
     * @param url
     * @return
     * @throws NetworkingException
     * @throws NetworkingAbortException
     */
    public String post(String url, Map<String, String> params, String encoder) throws NetworkingException, NetworkingAbortException {
        Log.d(AppConfig.TAG, "HttpConnection:HTTP POST excuting");
        mAbort = false;
        mClient = new DefaultHttpClient();

        String resp = null;
        try {
            mPost = new HttpPost(url);
            List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
            if (params != null) {
                for (Map.Entry<String, String> item : params.entrySet()) {
                    postParameters.add(new BasicNameValuePair(item.getKey(), item.getValue()));
                }
            }
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(postParameters);
            mPost.setEntity(formEntity);
            HttpConnectionParams.setConnectionTimeout(mPost.getParams(), 5 * 1000);

            HttpResponse response = mClient.execute(mPost);

            String newLine = System.getProperty("line.separator");
            StringBuffer sb = new StringBuffer("");

            List<String> lines = IOUtils.readLines(response.getEntity().getContent(), encoder);
            for (String line : lines) {
                sb.append(line + newLine);
            }
            resp = sb.toString();
            Log.v(AppConfig.TAG, "HttpConnection:response string:" + resp);

            int statusCode = response.getStatusLine().getStatusCode();
            Log.d(AppConfig.TAG, "HttpConnection:response status code:" + statusCode);

            if (statusCode != HttpStatus.SC_OK) {
                Log.e(AppConfig.TAG, "HttpConnection:wrong response status code:" + statusCode);
                Log.d(AppConfig.TAG, "HttpConnection:HTTP POST response string:" + sb.toString());
                resp = null;
            }

        } catch (IOException e) {
            Log.e(AppConfig.TAG, "HttpConnection:is aborted?" + mAbort);
            Log.e(AppConfig.TAG, "HttpConnection:I/O exception:" + e.getStackTrace().toString());
            resp = null;
            if (!mAbort) {
                throw new NetworkingAbortException();
            } else {
                throw new NetworkingException(e);
            }
        } finally {
            try {
                if (mClient != null) {
                    mClient.getConnectionManager().shutdown();
                    Log.i(AppConfig.TAG, "HttpConnection:connection closed");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Log.d(AppConfig.TAG, "HttpConnection:HTTP POST finished");
        return resp;
    }

    public String doGet(String url, String encoder) throws NetworkingException, NetworkingAbortException {
        mClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        StringBuffer stringBuffer = new StringBuffer();
        try {
            HttpResponse response = mClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            List<String> lines = IOUtils.readLines(entity.getContent(), encoder);
            for (String line : lines) {
                stringBuffer.append(line);
            }
        } catch (IOException e) {
            Log.e(AppConfig.TAG, "HttpConnection:is aborted?" + mAbort);
            Log.e(AppConfig.TAG, "HttpConnection:I/O exception:" + e.getStackTrace().toString());
            if (!mAbort) {
                throw new NetworkingAbortException();
            } else {
                throw new NetworkingException(e);
            }
        } finally {
            try {
                if (mClient != null) {
                    mClient.getConnectionManager().shutdown();
                    Log.i(AppConfig.TAG, "HttpConnection:connection closed");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return stringBuffer.toString();
    }

}

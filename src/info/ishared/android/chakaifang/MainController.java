package info.ishared.android.chakaifang;

import android.util.Log;
import info.ishared.android.chakaifang.http.AbstractHttpController;
import info.ishared.android.chakaifang.model.UserInfo;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Seven
 * Date: 13-12-30
 * Time: PM2:48
 */
public class MainController extends AbstractHttpController {
    private MainActivity mainActivity;

    public MainController(MainActivity mainActivity) {
        super(String.class);
        this.mainActivity = mainActivity;
    }

    public String queryUserInfo(String key) throws Exception {
        List<UserInfo> userInfoList = new ArrayList<UserInfo>();
        HttpClient httpClient = new HttpClient();
        httpClient.getHostConfiguration().setHost(AppConfig.QUERY_URL, 80, "http");
        HttpMethod method = postMethod(AppConfig.QUERY_URL, key);

        httpClient.executeMethod(method);

        String response = new String(method.getResponseBodyAsString().getBytes("iso-8859-1"), "gbk");
        response = parserHtml(response);
        Log.d(AppConfig.TAG, response);
        return response;

    }

    private HttpMethod postMethod(String url, String key) throws IOException {
        PostMethod post = new PostMethod(url);
        post.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=gbk");
        NameValuePair[] param = {new NameValuePair("wd", key)};
        post.setRequestBody(param);
        post.releaseConnection();
        return post;
    }

    private String parserHtml(String html) {
        return SimpleParser.getElementsValue(html, "tr");
    }
}

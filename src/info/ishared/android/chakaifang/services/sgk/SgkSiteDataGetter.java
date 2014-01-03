package info.ishared.android.chakaifang.services.sgk;

import android.util.Log;
import info.ishared.android.chakaifang.AppConfig;
import info.ishared.android.chakaifang.services.SiteDataGetter;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.IOException;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Seven
 * Date: 13-12-30
 * Time: PM5:19
 */
public class SgkSiteDataGetter implements SiteDataGetter {
    public static final String QUERY_URL = "http://594sgk.com";
    private Map<String, String> queryKey;

    @Override
    public String getSiteData() throws Exception {
        HttpClient httpClient = new HttpClient();
        httpClient.getHostConfiguration().setHost(QUERY_URL, 80, "http");
        HttpMethod method = postMethod(QUERY_URL, this.queryKey);

        httpClient.executeMethod(method);

        String response = new String(method.getResponseBodyAsString().getBytes("iso-8859-1"), "gbk");
        Log.d(AppConfig.TAG, response);
        return response;
    }

    private HttpMethod postMethod(String url, Map<String, String> queryKey) throws IOException {
        PostMethod post = new PostMethod(url);
        post.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=gbk");
        NameValuePair[] param = {new NameValuePair("wd", queryKey.get("wd"))};
        post.setRequestBody(param);
        post.releaseConnection();
        return post;
    }

    public void setQueryKey(Map<String, String> queryKey) {
        this.queryKey = queryKey;
    }
}

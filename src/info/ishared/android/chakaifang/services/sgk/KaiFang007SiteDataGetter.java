package info.ishared.android.chakaifang.services.sgk;

import android.util.Log;
import info.ishared.android.chakaifang.AppConfig;
import info.ishared.android.chakaifang.services.SiteDataGetter;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.IOException;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Seven
 * Date: 14-1-3
 * Time: PM4:46
 */
public class KaiFang007SiteDataGetter implements SiteDataGetter {
    public static final String QUERY_URL = "http://www.kaifang007.com";
    //    http://www.kaifang007.com/?keyword=%E8%B0%A2%E7%BA%A2%E9%9C%9E&dq=&xb=&age=&page=1
    private Map<String, String> queryKey;

    @Override
    public String getSiteData() throws Exception {
        HttpClient httpClient = new HttpClient();
        httpClient.getHostConfiguration().setHost(QUERY_URL, 80, "http");
        Log.d(AppConfig.TAG, QUERY_URL);
        HttpMethod method = postMethod(QUERY_URL, this.queryKey);

        httpClient.executeMethod(method);

        String response = method.getResponseBodyAsString();

        return response;
    }


    private static HttpMethod getMethod(String url, String param) throws IOException {
        GetMethod get = new GetMethod(url + "?" + param);
        return get;
    }

    private HttpMethod postMethod(String url, Map<String, String> queryKeyMap) throws IOException {
        PostMethod post = new PostMethod(url);
        post.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        NameValuePair[] param = {
                new NameValuePair("keyword", queryKeyMap.get("keyword")),
                new NameValuePair("page", queryKeyMap.get("page")),
                new NameValuePair("dq", queryKeyMap.get("dq")),
                new NameValuePair("xb", queryKeyMap.get("xb"))
        };
        post.setRequestBody(param);
        post.releaseConnection();
        return post;
    }

    @Override
    public void setQueryKey(Map<String, String> queryKey) {
        this.queryKey = queryKey;
    }
}

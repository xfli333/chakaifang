package info.ishared.android.chakaifang.services.sgk;

import info.ishared.android.chakaifang.AppConfig;
import info.ishared.android.chakaifang.services.SiteDateGetter;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Seven
 * Date: 13-12-30
 * Time: PM5:19
 */
public class SgkSiteDataGetter implements SiteDateGetter {

    private String queryKey;

    @Override
    public String getSiteDate() throws Exception {
        HttpClient httpClient = new HttpClient();
        httpClient.getHostConfiguration().setHost(AppConfig.QUERY_URL, 80, "http");
        HttpMethod method = postMethod(AppConfig.QUERY_URL, this.queryKey);

        httpClient.executeMethod(method);

        String response = new String(method.getResponseBodyAsString().getBytes("iso-8859-1"), "gbk");
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

    public void setQueryKey(String queryKey) {
        this.queryKey = queryKey;
    }
}

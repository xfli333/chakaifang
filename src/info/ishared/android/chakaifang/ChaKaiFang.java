package info.ishared.android.chakaifang;

import info.ishared.android.chakaifang.services.SiteDataParser;
import info.ishared.android.chakaifang.services.sgk.SgkParser;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.IOException;
import java.net.URLEncoder;

/**
 * Created by Seven on 13-12-27.
 */
public class ChaKaiFang {
    private static String VOTE_URL = "http://594sgk.com/";

    public static void main(String[] args) throws Exception {

        String url = "http://594sgk.com/";
        String host = "594sgk.com";
        String param = "wd=" + URLEncoder.encode("510104199001171461", "gbk");
        HttpClient httpClient = new HttpClient();
        httpClient.getHostConfiguration().setHost(host, 80, "http");

//        HttpMethod method = getMethod(url, param);
        HttpMethod method = postMethod(url);

        httpClient.executeMethod(method);

//        String response = method.getResponseBodyAsString();
        String response = new String(method.getResponseBodyAsString().getBytes("iso-8859-1"), "gbk");
        SiteDataParser parser = new SgkParser();
        parser.parserString(response);
//        System.out.println(SimpleParser.getElementsValue(response, "a"));
    }

    private static HttpMethod getMethod(String url, String param) throws IOException {
        GetMethod get = new GetMethod(url + "?" + param);
//        get.releaseConnection();
        return get;
    }

    private static HttpMethod postMethod(String url) throws IOException {
        PostMethod post = new PostMethod(url);
        post.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=gbk");
        NameValuePair[] param = {new NameValuePair("wd", "杨阳")};
        post.setRequestBody(param);
        post.releaseConnection();
        return post;
    }
}

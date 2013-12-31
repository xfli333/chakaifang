package info.ishared.android.chakaifang;

import android.util.Log;
import info.ishared.android.chakaifang.http.AbstractHttpController;
import info.ishared.android.chakaifang.model.UserInfo;
import info.ishared.android.chakaifang.services.SiteDateGetter;
import info.ishared.android.chakaifang.services.sgk.SgkParser;
import info.ishared.android.chakaifang.services.SiteDataParser;
import info.ishared.android.chakaifang.services.sgk.SgkSiteDataGetter;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Seven
 * Date: 13-12-30
 * Time: PM2:48
 */
public class MainController extends AbstractHttpController {
    private MainActivity mainActivity;
    private SiteDataParser parser;
    private SiteDateGetter siteDateGetter;

    public MainController(MainActivity mainActivity) {
        super(String.class);
        this.mainActivity = mainActivity;
        parser = new SgkParser();


    }

    public void queryUserInfo(String key) throws Exception {
        siteDateGetter = new SgkSiteDataGetter();
        siteDateGetter.setQueryKey(key);
        Log.d(AppConfig.TAG, key);
        String dataContent = siteDateGetter.getSiteDate();
        Log.d(AppConfig.TAG, dataContent);
        List<UserInfo> userInfoList = parser.parserString(dataContent);
        mainActivity.updateQueryListData(userInfoList);
    }


}

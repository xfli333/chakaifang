package info.ishared.android.chakaifang;

import android.util.Log;
import info.ishared.android.chakaifang.http.AbstractHttpController;
import info.ishared.android.chakaifang.model.UserInfo;
import info.ishared.android.chakaifang.services.SiteDataGetter;
import info.ishared.android.chakaifang.services.sgk.KaiFang007Parser;
import info.ishared.android.chakaifang.services.sgk.KaiFang007SiteDataGetter;
import info.ishared.android.chakaifang.services.sgk.SgkParser;
import info.ishared.android.chakaifang.services.SiteDataParser;
import info.ishared.android.chakaifang.services.sgk.SgkSiteDataGetter;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Seven
 * Date: 13-12-30
 * Time: PM2:48
 */
public class MainController extends AbstractHttpController {
    private MainActivity mainActivity;
    private SiteDataParser parser;
    private SiteDataGetter siteDataGetter;

    public MainController(MainActivity mainActivity) {
        super(String.class);
        this.mainActivity = mainActivity;
        parser = new KaiFang007Parser();


    }

    public void queryUserInfo(Map<String, String> parameters) throws Exception {

        siteDataGetter = new KaiFang007SiteDataGetter();
        siteDataGetter.setQueryKey(parameters);

        String dataContent = siteDataGetter.getSiteData();
        Log.d(AppConfig.TAG, dataContent);
        List<UserInfo> userInfoList = parser.parserString(dataContent);
        Log.d(AppConfig.TAG, userInfoList.size() + "");
        mainActivity.updateQueryListData(userInfoList);
    }


}

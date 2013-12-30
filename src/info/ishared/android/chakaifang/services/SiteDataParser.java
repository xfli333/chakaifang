package info.ishared.android.chakaifang.services;

import info.ishared.android.chakaifang.model.UserInfo;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Seven
 * Date: 13-12-30
 * Time: PM4:26
 */
public interface SiteDataParser {
    List<UserInfo> parserString(String content);
}

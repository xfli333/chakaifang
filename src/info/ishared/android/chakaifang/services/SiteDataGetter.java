package info.ishared.android.chakaifang.services;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Seven
 * Date: 13-12-30
 * Time: PM5:18
 */
public interface SiteDataGetter {
    String getSiteData() throws Exception;

    void setQueryKey(Map<String, String> queryKey);
}

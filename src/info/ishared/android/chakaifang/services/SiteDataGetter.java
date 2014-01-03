package info.ishared.android.chakaifang.services;

/**
 * Created with IntelliJ IDEA.
 * User: Seven
 * Date: 13-12-30
 * Time: PM5:18
 */
public interface SiteDataGetter {
    String getSiteData() throws Exception;

    void setQueryKey(String queryKey);
}

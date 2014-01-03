package info.ishared.android.chakaifang.services.sgk;

import info.ishared.android.chakaifang.model.UserInfo;
import info.ishared.android.chakaifang.services.SiteDataParser;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableRow;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Seven
 * Date: 13-12-30
 * Time: PM4:31
 */
public class SgkParser implements SiteDataParser {
    @Override
    public List<UserInfo> parserString(String content) {
        List<UserInfo> userInfoList = new ArrayList<UserInfo>();
        List<String> userContents = this.getUserContents(content, "tr");
        for (String str : userContents) {
            userInfoList.add(createUserFromTrContent(str));
        }
        return userInfoList;
    }


    private UserInfo createUserFromTrContent(String trContent) {
        UserInfo userInfo = new UserInfo();
        List<String> userField = this.getUserContents(trContent, "td");
        userInfo.setUserName(userField.get(0).substring(4, userField.get(0).length() - 5));
        userInfo.setIdCardNo(userField.get(1).substring(4, userField.get(1).length() - 5));
        userInfo.setBirthDay(userField.get(2).substring(4, userField.get(2).length() - 5));
        userInfo.setPhoneNumber(userField.get(3).substring(4, userField.get(3).length() - 5));
        userInfo.setCellPhone(userField.get(4).substring(4, userField.get(4).length() - 5));
        userInfo.setAddress(userField.get(5).substring(4, userField.get(5).length() - 5));
        userInfo.setCheckInDate(userField.get(6).substring(4, userField.get(6).length() - 5));
        return userInfo;
    }


    private List<String> getUserContents(String html, String tag) {
        List<String> userContents = new ArrayList<String>();

        Parser parser = Parser.createParser(html, "utf-8");
        NodeFilter divFilter = new NodeClassFilter(TableRow.class);
        if ("td".equals(tag)) {
            divFilter = new NodeClassFilter(TableColumn.class);
        }

        OrFilter lastFilter = new OrFilter();
        lastFilter.setPredicates(new org.htmlparser.NodeFilter[]{divFilter});
        String content = null;
        try {
            org.htmlparser.util.NodeList nodeList = parser.extractAllNodesThatMatch(lastFilter);
            for (org.htmlparser.Node node : nodeList.toNodeArray()) {
                String divInfo = node.getText();
                if (divInfo.equals(tag)) {
                    content = node.toHtml();
                    userContents.add(content);
                }
            }
        } catch (org.htmlparser.util.ParserException e) {
            e.printStackTrace();
        }
        return userContents;
    }
}

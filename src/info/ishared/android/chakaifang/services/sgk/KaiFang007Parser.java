package info.ishared.android.chakaifang.services.sgk;

import android.util.Log;
import info.ishared.android.chakaifang.AppConfig;
import info.ishared.android.chakaifang.model.UserInfo;
import info.ishared.android.chakaifang.services.SiteDataParser;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableRow;
import org.htmlparser.tags.TableTag;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Seven
 * Date: 14-1-3
 * Time: PM4:57
 */
public class KaiFang007Parser implements SiteDataParser {
    @Override
    public List<UserInfo> parserString(String content) {
        List<UserInfo> userInfoList = new ArrayList<UserInfo>();
        String data = this.getDataGridContents(content);
        List<String> userContents = this.getUserContents(data, "tr");
        for (String str : userContents) {
            userInfoList.add(createUserFromTrContent(str));
        }
        return userInfoList;
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
                if ("tr".equals(tag) && (divInfo.equals("tr class=\"Even\"") || divInfo.equals("tr class=\"Odd\""))) {
                    content = node.toHtml();
                    userContents.add(content);
                } else if (divInfo.startsWith("td")) {
                    content = node.toHtml();
                    userContents.add(content);
                }
            }
        } catch (org.htmlparser.util.ParserException e) {
            e.printStackTrace();
        }
        return userContents;
    }

    private UserInfo createUserFromTrContent(String trContent) {
        UserInfo userInfo = new UserInfo();
        List<String> userField = this.getUserContents(trContent, "td");
        userInfo.setUserName(userField.get(0).substring(4, userField.get(0).length() - 5));
        userInfo.setSex(userField.get(1).indexOf("female.png") != -1 ? "女" : "男");
        userInfo.setIdCardNo(userField.get(5).substring(4, userField.get(5).length() - 5));
        userInfo.setBirthDay(userField.get(3).substring(19, userField.get(3).length() - 5));
        userInfo.setPhoneNumber(userField.get(2).substring(19, userField.get(2).length() - 5));
        userInfo.setCellPhone(userField.get(6).substring(4, userField.get(6).length() - 5));
        userInfo.setEmail(userField.get(7).substring(4, userField.get(7).length() - 5));
        userInfo.setAddress(userField.get(8).substring(4, userField.get(8).length() - 5).replaceAll("&nbsp;", ""));
        userInfo.setCheckInDate(userField.get(9).substring(19, userField.get(9).length() - 5));
        return userInfo;
    }


    private String getDataGridContents(String html) {

        Parser parser = Parser.createParser(html, "utf-8");
        NodeFilter divFilter = new NodeClassFilter(TableTag.class);

        OrFilter lastFilter = new OrFilter();
        lastFilter.setPredicates(new org.htmlparser.NodeFilter[]{divFilter});
        String content = null;
        try {
            org.htmlparser.util.NodeList nodeList = parser.extractAllNodesThatMatch(lastFilter);
            for (org.htmlparser.Node node : nodeList.toNodeArray()) {
                String divInfo = node.getText();
                Log.d(AppConfig.TAG, divInfo);
                if (divInfo.indexOf("DataGrid") != -1) {
                    content = node.toHtml();
                    Log.d(AppConfig.TAG, content);
                }
            }
        } catch (org.htmlparser.util.ParserException e) {
            e.printStackTrace();
        }
        return content;
    }

}



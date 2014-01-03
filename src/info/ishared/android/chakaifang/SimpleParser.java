package info.ishared.android.chakaifang;

import java.util.ArrayList;
import java.util.List;
import java.util.SimpleTimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: Lee
 * Date: 11-12-16
 * Time: 下午10:38
 */
public class SimpleParser {
    public static String getElementsValue(String xml, String elementName) {
        StringBuilder stringBuilder = new StringBuilder();
//        Pattern pattern2 = Pattern.compile("<" + elementName + ">(.*?)</" + elementName + ">");
        Pattern pattern2 = Pattern.compile("<table[^>]*>[\\s\\S]*</table>");
        Matcher matcher2 = pattern2.matcher(xml);
        while (matcher2.find()) {
            String data1 = matcher2.group();
            stringBuilder.append(data1);
        }
        return stringBuilder.toString();
    }
}

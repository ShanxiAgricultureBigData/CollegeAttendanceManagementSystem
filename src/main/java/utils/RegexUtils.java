package utils;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式工具类
 */
public class RegexUtils {


    public static boolean matchClassroom(String info){
        String regex ="([0-9]{1}[AB]{1}-[0-9]{3})";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(info);
        if (matcher.find()) {
            return true;
        } else {
            System.out.println("没有匹配的结果。");
        }
        return false;
    }

    /**
     * 匹配软件学院应到班级
     * @return 应到班级
     */
    public static String[] matchSoftCollege(String info){
        String[] res = new String[5];
        Arrays.fill(res,"");
        int count = 0;

        String regex = "([软件]+[0-9]+[Z]*[班]|[大数据]+[0-9]+[班]|[智能]+[0-9]+[班]|[数媒]+[0-9]+[班])";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(info);
        if (matcher.matches()){
            res[count++] = matcher.group(0);
        }
        return res;
    }
}

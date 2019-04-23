package win.hgfdodo.hspring.utils;

public class StringUtils {
    /**
     * 字符串是否为空
     * @param str
     * @return true：为空；false 不为空
     */
    public static boolean isEmpty(String str) {
        if (str == null || str.equals("")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isNotEmpty(String str){
        if(isEmpty(str)){
            return false;
        }else {
            return true;
        }
    }

    /**
     * 首字母大写
     *
     * @param name
     * @return
     */
    public static String upperFirstChar(String name) {
        if (name != null && name.length() > 0 && Character.isLowerCase(name.charAt(0))) {
            name = Character.toUpperCase(name.charAt(0)) + name.substring(1, name.length());
        }
        return name;
    }

    /**
     * 首字母小写
     *
     * @param name
     * @return
     */
    public static String lowerFirstChar(String name) {
        if (name != null && name.length() > 0 && Character.isUpperCase(name.charAt(0))) {
            name = Character.toLowerCase(name.charAt(0)) + name.substring(1, name.length());
        }
        return name;
    }
}

package win.hgfdodo.hspring.utils;

import java.util.Map;

public class MapUtils {
    public static boolean isEmpty(Map map) {
        return map == null || map.size() == 0 ? true : false;
    }

    public static boolean isNotEmpty(Map map) {
        return !isEmpty(map);
    }
}

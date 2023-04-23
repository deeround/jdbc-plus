package com.github.deeround.jdbc.plus.util;

import java.util.Collection;

/**
 * @author wanghao 913351190@qq.com
 * @create 2023/4/18 16:01
 */
public class CollectionUtils {
    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }
}

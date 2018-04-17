package com.light.framework.util;

import org.apache.commons.lang3.ArrayUtils;

/**
 * 数组工具类
 * Created by wanganyu on 2018/03/28.
 */
public class ArrayUtil {

    /**
     * 判断数组是否为空
     * @param array
     * @return
     */
    public static boolean isEmpty(Object[] array){
        return ArrayUtils.isEmpty(array);
    }

    /**
     * 判断数组是否为非空
     * @param array
     * @return
     */
 public static boolean isNotEmpty(Object[] array){
     return !ArrayUtils.isEmpty(array);
 }

}

package com.light.framework;

import com.light.framework.helper.*;
import com.light.framework.util.ClassUtil;

/**
 * Created by wanganyu on 2018/03/28.
 */
public class HelperLoader {
    public static void init(){
        Class<?>[] classList={
                ClassHelper.class,
                BeanHelper.class,
                AopHelper.class,
                IocHelper.class,
                ControllerHelper.class
        };
        for(Class<?> cls:classList){
            ClassUtil.loadClass(cls.getName());
        }
    }
}

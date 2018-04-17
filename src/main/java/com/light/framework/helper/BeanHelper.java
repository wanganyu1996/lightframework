package com.light.framework.helper;

import com.light.framework.util.ReflectionUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Bean 助手类
 * Created by wanganyu on 2018/03/27.
 */
public class BeanHelper {
   private static final Map<Class<?>,Object> BEAN_MAP=new HashMap<Class<?>,Object>();
   static {
       Set<Class<?>> beanClassSet=ClassHelper.getBeanClassSet();
       for(Class<?> beanClass:beanClassSet){
           Object obj= ReflectionUtil.newInstance(beanClass);
           BEAN_MAP.put(beanClass,obj);
       }
   }

    /**
     * 获取Bean映射
     * @return
     */
   public static Map<Class<?>,Object> getBeanMap(){
       return BEAN_MAP;
   }

   public static void setBean(Class<?> cls,Object obj){
       BEAN_MAP.put(cls,obj);
   }
    /**
     * 获取Bean实例
     * @param cls
     * @param <T>
     * @return
     */
   @SuppressWarnings("unchecked")
  public static <T> T getBean(Class<T> cls){
     if(!BEAN_MAP.containsKey(cls)){
         throw new RuntimeException("can not get bean by class:"+cls);
     }
     return (T)BEAN_MAP.get(cls);
  }
}

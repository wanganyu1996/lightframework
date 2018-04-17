package com.light.framework.helper;

import com.light.framework.annotation.Inject;
import com.light.framework.util.ArrayUtil;
import com.light.framework.util.CollectionUtil;
import com.light.framework.util.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.Map;

/**
 *依赖注入助手类
 * Created by wanganyu on 2018/03/28.
 */
public final class IocHelper {
  static{
      //获取所有的Bean类与Bean实例之间的映射关系（简称 Bean Map）
      Map<Class<?>,Object> beanMap=BeanHelper.getBeanMap();
      if(CollectionUtil.isNotEmpty(beanMap)){
          //遍历Bean Map
          for(Map.Entry<Class<?>,Object> beanEntry:beanMap.entrySet()){
              //从BeanMap 中获取Bean类与Bean实例
              Class<?> beanClass=beanEntry.getKey();
              Object beanInstance=beanEntry.getValue();
              //获取Bean 类定义的所有成员变量（简称 Bean Field）
              Field[] beanFields=beanClass.getDeclaredFields();
              if(ArrayUtil.isNotEmpty(beanFields)){
                  //遍历 Bean Field
                  for(Field beanField:beanFields){
                      //判断当前 Bean Field是否带有Inject注解
                      if(beanField.isAnnotationPresent(Inject.class)){
                       //在 Bean Map中获取Bean Field对应的实例
                          Class<?> beanFieldClass=beanField.getType();
                          Object beanFieldInstance=beanMap.get(beanFieldClass);
                          if(beanFieldInstance!=null){
                              //通过反射初始化BeanField的值
                              ReflectionUtil.setField(beanInstance,beanField,beanFieldInstance);
                          }
                      }
                  }

              }
          }
      }
  }

}

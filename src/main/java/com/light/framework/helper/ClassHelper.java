package com.light.framework.helper;

import com.light.framework.annotation.Controller;
import com.light.framework.annotation.Service;
import com.light.framework.util.ClassUtil;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by wanganyu on 2018/03/27.
 */
public class ClassHelper {

    /**
     * 定义类集合（用于存放所加载的类）
     */
    private static final Set<Class<?>> CLASS_SET;
    static{
        String backPackage=ConfigHelper.getAppBasePackage();
        CLASS_SET= ClassUtil.getClassSet(backPackage);
    }

    /**
     * 获取应用包下所有的类
     * @return
     */
    public static Set<Class<?>> getClassSet(){
        return CLASS_SET;
    }

    /**
     * 获取应用包名下的所有Service类
     * @return
     */
    public static Set<Class<?>> getServiceClassSet(){
        Set<Class<?>> classSet=new HashSet<Class<?>>();
        for(Class<?> cls:CLASS_SET){
           if(cls.isAnnotationPresent(Service.class)) {
               classSet.add(cls);
           }
        }
        return classSet;
    }
    /**
     * 获取应用包名下的所有Controller类
     * @return
     */
    public static Set<Class<?>> getControllerClassSet(){
        Set<Class<?>> classSet=new HashSet<Class<?>>();
        for(Class<?> cls:CLASS_SET){
            if(cls.isAnnotationPresent(Controller.class)) {
                classSet.add(cls);
            }
        }
        return classSet;
    }
    /**
     * 获取应用包名下的所有Bean类(包括：Service/Controller等)
     * @return
     */
    public static Set<Class<?>> getBeanClassSet(){
        Set<Class<?>> beanClassSet=new HashSet<Class<?>>();
        beanClassSet.addAll(getServiceClassSet());
        beanClassSet.addAll(getControllerClassSet());
        return beanClassSet;
    }
    /**
     * 获取应用包名下某父类（或接口）的所有子类（或实现类）
     */
    public static Set<Class<?>> getClassSetBySuper(Class <?> superClass){
        Set<Class<?>> classSet=new HashSet<Class<?>>();
        for(Class<?> cls:CLASS_SET){
           if(superClass.isAssignableFrom(cls)&&!superClass.equals(cls)){
               classSet.add(cls);
           }
        }
        return classSet;
    }
    /**
     * 获取应用包名下带有某注解的所有类
     */
    public static Set<Class<?>> getClassSetByAnnotaion(Class<? extends  Annotation> annotationClass){
       Set<Class<?>>  classSet=new HashSet<Class<?>>();
       for(Class<?> cls:CLASS_SET){
           if(cls.isAnnotationPresent(annotationClass)){
               classSet.add(cls);
           }
       }
       return classSet;
    }


}

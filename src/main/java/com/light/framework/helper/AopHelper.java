package com.light.framework.helper;

import com.light.framework.annotation.Aspect;
import com.light.framework.annotation.Service;
import com.light.framework.proxy.AspectProxy;
import com.light.framework.proxy.Proxy;
import com.light.framework.proxy.ProxyManager;
import com.light.framework.proxy.TransactionProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.*;


/**
 * Created by wanganyu on 2018/04/14.
 */
public class AopHelper {
    private static final Logger LOGGER= LoggerFactory.getLogger(AopHelper.class);
    static{
        try {
            Map<Class<?>,Set<Class<?>>> proxyMap=createProxyMap();
            Map<Class<?>,List<Proxy>> targetMap=createTargetMap(proxyMap);
            for(Map.Entry<Class<?>,List<Proxy>> targetEntry:targetMap.entrySet()){
              Class<?> targetClass=targetEntry.getKey();
              List<Proxy> proxyList=targetEntry.getValue();
              Object proxy= ProxyManager.creaateProxy(targetClass,proxyList);
              BeanHelper.setBean(targetClass,proxy);
            }
        } catch (Exception e) {
            LOGGER.error("aop failure", e);
        }
   }
   private static Map<Class<?>,Set<Class<?>>> createProxyMap() throws Exception{
       Map<Class<?>,Set<Class<?>>> proxyMap=new HashMap<Class<?>, Set<Class<?>>>();
       addAspectProxy(proxyMap);
       addTransactionProxy(proxyMap);
       return proxyMap;
   }
   public static void addAspectProxy(Map<Class<?>,Set<Class<?>>> proxyMap) throws Exception{
       Set<Class<?>> proxyClassSet=ClassHelper.getClassSetBySuper(AspectProxy.class);
       for(Class<?> proxyClass:proxyClassSet){
           if(proxyClass.isAnnotationPresent(Aspect.class)){
               Aspect aspect=proxyClass.getAnnotation(Aspect.class);
               Set<Class<?>> targetClassSet=createTargetClassSet(aspect);
               proxyMap.put(proxyClass,targetClassSet);
           }
       }
   }
   private static void addTransactionProxy(Map<Class<?>,Set<Class<?>>> proxyMap){
      Set<Class<?>> serviceClassSet=ClassHelper.getClassSetByAnnotaion(Service.class);
      proxyMap.put(TransactionProxy.class,serviceClassSet);
   }
   private static Set<Class<?>> createTargetClassSet(Aspect aspect) throws Exception{
        Set<Class<?>> targetClassSet=new HashSet<Class<?>>();
        Class<? extends Annotation> annotation=aspect.value();
        if(annotation!=null&&!annotation.equals(Aspect.class)){
            targetClassSet.addAll(ClassHelper.getClassSetByAnnotaion(annotation));
        }
        return targetClassSet;
    }
   private static Map<Class<?>,List<Proxy>> createTargetMap(Map<Class<?>,Set<Class<?>>> proxyMap) throws Exception {
        Map<Class<?>,List<Proxy>> targetMap=new HashMap<Class<?>, List<Proxy>>();
        for(Map.Entry<Class<?>,Set<Class<?>>> proxyEntry:proxyMap.entrySet()){
            Class<?> proxyClass=proxyEntry.getKey();
            Set<Class<?>> targetClassSet=proxyEntry.getValue();
            for(Class<?> targetClass:targetClassSet){
                Proxy proxy=(Proxy) proxyClass.newInstance();
                if(targetMap.containsKey(targetClass)){
                    targetMap.get(targetClass).add(proxy);
                }else{
                    List<Proxy> proxyList=new ArrayList<Proxy>();
                    proxyList.add(proxy);
                    targetMap.put(targetClass,proxyList);
                }
            }

        }
     return targetMap;
   }
}

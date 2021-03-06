package com.light.framework.helper;

import com.light.framework.annotation.Action;
import com.light.framework.bean.Handler;
import com.light.framework.bean.Request;
import com.light.framework.util.ArrayUtil;
import com.light.framework.util.CollectionUtil;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by wanganyu on 2018/03/28.
 */
public final class ControllerHelper {
    private static final Map<Request,Handler> ACTION_MAP=new HashMap<Request, Handler>();
    static{
        try {
            //获取所有的Controller类
            Set<Class<?>> controllerClassSet=ClassHelper.getControllerClassSet();
            if(CollectionUtil.isNotEmpty(controllerClassSet)){
                //遍历这些Controller类
                for(Class<?> controllerClass:controllerClassSet){
                    //获取Controller类中定义的方法
                    Method[] methods=controllerClass.getDeclaredMethods();
                    if(ArrayUtil.isNotEmpty(methods)){
                        //遍历这些Controller类中的方法
                        for(Method method:methods){
                            //判断当前方法是否带有Action注解
                            if(method.isAnnotationPresent(Action.class)){
                                //从Action注解中获取URL映射规则
                                Action action=method.getAnnotation(Action.class);
                                String mapping=action.value();
                                //验证URL映射规则
                                if(mapping.matches("\\w+:/\\w*")){
                                    String[] array=mapping.split(":");
                                    if(ArrayUtil.isNotEmpty(array)&&array.length==2){
                                        //获取请求方法与请求路径
                                        String requestMethod=array[0];
                                        String requestPath=array[1];
                                        Request request=new Request(requestMethod,requestPath);
                                        Handler handler=new Handler(controllerClass,method);
                                        //初始化Action Map
                                        ACTION_MAP.put(request,handler);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取Handler
     * @return
     */
    public static Handler getHandler(String requestMethod,String requestPath){
        Request request=new Request(requestMethod,requestPath);
        return ACTION_MAP.get(request);
    }
}

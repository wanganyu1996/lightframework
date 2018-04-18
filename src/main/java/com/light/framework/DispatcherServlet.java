package com.light.framework;

import com.light.framework.bean.Data;
import com.light.framework.bean.Handler;
import com.light.framework.bean.Param;
import com.light.framework.bean.View;
import com.light.framework.helper.*;
import com.light.framework.util.JsonUtil;
import com.light.framework.util.ReflectionUtil;
import com.light.framework.util.StringUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * 请求转发器
 * Created by wanganyu on 2018/03/28.
 */
@WebServlet(urlPatterns = "/*",loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet{
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
       //初始化相关Helper类
        HelperLoader.init();
        //获取ServletContext对象（用于注册Servlet）
        ServletContext servletContext=servletConfig.getServletContext();
        ServletRegistration jspServlet=servletContext.getServletRegistration("jsp");
        jspServlet.addMapping(ConfigHelper.getAppJspPath()+"*");
        //注册处理静态资源的默认Servlet
        ServletRegistration defaultServlet=servletContext.getServletRegistration("default");
        defaultServlet.addMapping(ConfigHelper.getAppAssetPath()+"*");

        UploadHelper.init(servletContext);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestMethod=req.getMethod().toLowerCase();//请求方法
        String requestPath=req.getPathInfo();//请求路径
        if(requestPath.equals("/favicon.ico")){
            return;
        }
        //获取Action处理器
        Handler handler= ControllerHelper.getHandler(requestMethod,requestPath);
        if(handler!=null){
            //获取Controller类及其Bean实例
            Class<?> controllerClass=handler.getControllerClass();
            Object controllerBean= BeanHelper.getBean(controllerClass);

            Param param;
            if(UploadHelper.isMultipart(req)){
                param=UploadHelper.createParam(req);
            }else{
                param= RequestHelper.createParam(req);
            }
            //调用Action方法
            Object result;
            Method actionMethod=handler.getActionMethod();
            if(param.isEmpty()){
                result=ReflectionUtil.invokeMethod(controllerBean,actionMethod);
            }else{
                result=ReflectionUtil.invokeMethod(controllerBean,actionMethod,param);
            }
            //处理Action方法返回值
            if(result instanceof View){
               handleViewResult((View) result,req,resp);
            }else if(result instanceof Data){
               handleDataResult((Data) result,resp);
            }
        }
    }
    private void handleViewResult(View view,HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        String path = view.getPath();
        if (StringUtil.isNotEmpty(path)) {
            if (path.startsWith("/")) {
                response.sendRedirect(request.getContextPath() + path);
            } else {
                Map<String, Object> model = view.getModel();
                for (Map.Entry<String, Object> entry : model.entrySet()) {
                    request.setAttribute(entry.getKey(), entry.getValue());
                }
                request.getRequestDispatcher(ConfigHelper.getAppJspPath() + path).forward(request, response);
            }
        }
    }
    public void handleDataResult(Data data,HttpServletResponse response) throws IOException {
        Object model=data.getModel();
        if(model!=null){
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter writer=response.getWriter();
            String json=JsonUtil.toJson(model);
            writer.write(json);
            writer.flush();
            writer.close();
        }
    }
}

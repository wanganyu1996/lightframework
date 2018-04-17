package com.light.framework.bean;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * 封装请求信息
 * Created by wanganyu on 2018/03/28.
 */
public class Request {
    /**
     * 请求方法
      */
  private String requestMethod;
    /**
     * 请求路径
     */
  private  String requestPath;

    public Request(String requestMethod, String requestPath) {
        this.requestMethod = requestMethod;
        this.requestPath = requestPath;
    }
   //hashCode取决于该class的所有filed时需要使用反射机制来产生一个hashCode。
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
    //如果两个对象相等当且仅当每个属性值都相等
    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this,obj);
    }
}

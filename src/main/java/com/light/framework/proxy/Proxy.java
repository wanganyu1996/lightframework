package com.light.framework.proxy;

/**
 * Created by wanganyu on 2018/04/13.
 */
public interface Proxy {
    /**
     * 执行链式处理
     * @param proxyChain
     * @return
     * @throws Throwable
     */
    Object doProxy(ProxyChain proxyChain) throws Throwable;
}

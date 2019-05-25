package com.lmmmowi.mowikit.proxy;

/**
 * @Author: mowi
 * @Date: 2019-05-25
 * @Description:
 */
public interface ProxyFactory {

    <T> T getProxy(Invoker<T> invoker, Class[] interfaces);

}

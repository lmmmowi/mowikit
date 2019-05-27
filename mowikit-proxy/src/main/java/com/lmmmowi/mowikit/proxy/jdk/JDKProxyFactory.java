package com.lmmmowi.mowikit.proxy.jdk;

import com.lmmmowi.mowikit.proxy.AbstractProxyFactory;
import com.lmmmowi.mowikit.proxy.Invoker;

import java.lang.reflect.Proxy;

/**
 * @Author: mowi
 * @Date: 2019-05-27
 * @Description:
 */
public class JDKProxyFactory extends AbstractProxyFactory {

    @Override
    protected <T> T getProxy(Invoker<T> invoker, Class invokerGenericClass, Class[] interfaces) {
        if (!invokerGenericClass.isInterface()) {
            throw new IllegalArgumentException("JDK proxy only support interface");
        }

        return (T) Proxy.newProxyInstance(invokerGenericClass.getClassLoader(), new Class[]{invokerGenericClass}, new JDKProxy(invoker));
    }
}

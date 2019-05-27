package com.lmmmowi.mowikit.proxy.cglib;

import com.lmmmowi.mowikit.proxy.AbstractProxyFactory;
import com.lmmmowi.mowikit.proxy.Invoker;
import net.sf.cglib.proxy.Enhancer;

/**
 * @Author: mowi
 * @Date: 2019-05-27
 * @Description:
 */
public class CglibProxyFactory extends AbstractProxyFactory {

    @Override
    protected <T> T getProxy(Invoker<T> invoker, Class invokerGenericClass, Class[] interfaces) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(invokerGenericClass);
        enhancer.setCallback(new CglibProxy(invoker));
        return (T) enhancer.create();
    }
}

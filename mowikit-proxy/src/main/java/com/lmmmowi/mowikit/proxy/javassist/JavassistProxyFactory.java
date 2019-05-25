package com.lmmmowi.mowikit.proxy.javassist;

import com.lmmmowi.mowikit.proxy.AbstractProxyFactory;
import com.lmmmowi.mowikit.proxy.Invoker;

/**
 * @Author: mowi
 * @Date: 2019-05-25
 * @Description:
 */
public class JavassistProxyFactory extends AbstractProxyFactory {

    @Override
    public <T> T getProxy(Invoker<T> invoker, Class[] interfaces) {
        return (T) JavassistProxy.getProxy(interfaces).newInstance(invoker);
    }
}

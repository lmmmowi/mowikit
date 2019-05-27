package com.lmmmowi.mowikit.proxy.jdk;

import com.lmmmowi.mowikit.proxy.Invoker;
import com.lmmmowi.mowikit.proxy.util.ReflectUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @Author: mowi
 * @Date: 2019-05-27
 * @Description:
 */
class JDKProxy implements InvocationHandler {

    private Invoker invoker;

    public JDKProxy(Invoker invoker) {
        this.invoker = invoker;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (ReflectUtils.isObjectMethod(method)) {
            return method.invoke(invoker.getProxy(), args);
        }

        return invoker.invoke(invoker, method, args);
    }
}

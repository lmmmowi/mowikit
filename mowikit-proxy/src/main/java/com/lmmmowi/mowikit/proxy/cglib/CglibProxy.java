package com.lmmmowi.mowikit.proxy.cglib;

import com.lmmmowi.mowikit.proxy.Invoker;
import com.lmmmowi.mowikit.proxy.util.ReflectUtils;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @Author: mowi
 * @Date: 2019-05-27
 * @Description:
 */
class CglibProxy implements MethodInterceptor {

    private Invoker invoker;

    public CglibProxy(Invoker invoker) {
        this.invoker = invoker;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        if (ReflectUtils.isObjectMethod(method)) {
            return method.invoke(invoker.getProxy(), args);
        }

        return invoker.invoke(invoker, method, args);
    }
}

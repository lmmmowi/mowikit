package com.lmmmowi.mowikit.proxy;

import com.lmmmowi.mowikit.proxy.util.ReflectUtils;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author: mowi
 * @Date: 2019-05-25
 * @Description:
 */
public abstract class AbstractProxyFactory implements ProxyFactory {

    @Override
    public <T> T getProxy(Invoker<T> invoker) {
        return getProxy(invoker, null);
    }

    @Override
    public <T> T getProxy(Invoker<T> invoker, Class[] interfaces) {
        Set<Class> itfSet = new HashSet<>();
        if (interfaces != null) {
            for (Class interfaceClass : interfaces) {
                if (!interfaceClass.isInterface()) {
                    throw new RuntimeException(interfaceClass.getName() + " is not a interface.");
                }

                itfSet.add(interfaceClass);
            }
        }

        Class invokerGenericClass = getInvokerGenericClass(invoker);
        if (invokerGenericClass.isInterface()) {
            itfSet.add(invokerGenericClass);
        }

        return getProxy(invoker, invokerGenericClass, itfSet.toArray(new Class[0]));
    }

    abstract protected <T> T getProxy(Invoker<T> invoker, Class invokerGenericClass, Class[] interfaces);

    private Class getInvokerGenericClass(Invoker invoker) {
        Type[] types = ReflectUtils.getGenericTypes(invoker.getClass());
        if (types == null || types.length == 0) {
            throw new IllegalArgumentException("generic type of invoker must be specified");
        }

        Type genericType = types[0];
        try {
            return Class.forName(genericType.getTypeName());
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("class of generic type " + genericType.getTypeName() + " can not be loaded", e);
        }
    }
}

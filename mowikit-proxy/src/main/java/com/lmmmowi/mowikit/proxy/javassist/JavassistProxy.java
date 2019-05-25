package com.lmmmowi.mowikit.proxy.javassist;

import com.lmmmowi.mowikit.proxy.Invoker;

/**
 * @Author: mowi
 * @Date: 2019-05-25
 * @Description:
 */
public abstract class JavassistProxy {

    static JavassistProxy getProxy(Class[] interfaces) {
        ClassLoader classLoader = JavassistProxy.class.getClassLoader();
        return getProxy(classLoader, interfaces);
    }

    private static JavassistProxy getProxy(ClassLoader classLoader, Class[] interfaces) {
        Class type = interfaces[0];

        JavassistProxy proxy = null;

        try {
            Class clazz = new ClassMaker(classLoader).makeClass(type);
            proxy = (JavassistProxy) clazz.newInstance();
        } catch (Throwable e) {
            e.printStackTrace();
        }

        return proxy;
    }


    abstract public Object newInstance(Invoker invoker);

}

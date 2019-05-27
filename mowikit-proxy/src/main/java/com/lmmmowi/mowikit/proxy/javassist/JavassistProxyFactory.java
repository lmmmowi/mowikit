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
    protected <T> T getProxy(Invoker<T> invoker, Class invokerGenericClass, Class[] interfaces) {
        return (T) getProxy(invokerGenericClass, interfaces).newInstance(invoker);
    }

    private static JavassistProxy getProxy(Class proxyClass, Class[] interfaces) {
        ClassLoader classLoader = JavassistProxy.class.getClassLoader();

        JavassistProxy proxy = null;
        try {
            ClassMaker classMaker = new ClassMaker(classLoader);

            classMaker.setProxyClass(proxyClass);

            for (Class itf : interfaces) {
                classMaker.addInterface(itf);
            }

            Class clazz = classMaker.makeClass();
            proxy = (JavassistProxy) clazz.newInstance();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return proxy;
    }
}

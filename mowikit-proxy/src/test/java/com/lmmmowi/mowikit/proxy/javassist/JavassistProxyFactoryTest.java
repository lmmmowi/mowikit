package com.lmmmowi.mowikit.proxy.javassist;

import com.lmmmowi.mowikit.proxy.Invoker;
import com.lmmmowi.mowikit.proxy.ProxyFactory;
import foo.Foo;
import foo.FooInterface;
import org.junit.Test;

import java.lang.reflect.Method;

/**
 * @Author: mowi
 * @Date: 2019-05-25
 * @Description:
 */
public class JavassistProxyFactoryTest {

    @Test
    public void test_getProxy() {
        Foo foo = new Foo();

        Invoker<FooInterface> invoker = new Invoker<FooInterface>(foo) {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("before " + method.getName());
                try {
                    return method.invoke(object, args);
                } finally {
                    System.out.println("after " + method.getName());
                }
            }
        };

        ProxyFactory proxyFactory = new JavassistProxyFactory();
        FooInterface fooInterface = proxyFactory.getProxy(invoker);

        fooInterface.hello();

        System.out.println(fooInterface.hashCode());
        System.out.println(fooInterface.toString());
    }
}

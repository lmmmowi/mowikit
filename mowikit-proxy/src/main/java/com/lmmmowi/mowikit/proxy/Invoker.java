package com.lmmmowi.mowikit.proxy;

import java.lang.reflect.InvocationHandler;

/**
 * @Author: mowi
 * @Date: 2019-05-25
 * @Description:
 */
public abstract class Invoker<T> implements InvocationHandler {

    protected T object;

    public Invoker(T object) {
        this.object = object;
    }
}

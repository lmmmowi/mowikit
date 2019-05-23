package com.lmmmowi.mowikit.spi;

/**
 * @Author: mowi
 * @Date: 2019-05-23
 * @Description:
 */
public class Holder<T> {

    private volatile T object;

    public T get() {
        return object;
    }

    public void set(T object) {
        this.object = object;
    }
}

package com.lmmmowi.mowikit.proxy.foo;

/**
 * @Author: mowi
 * @Date: 2019-05-25
 * @Description:
 */
public class Foo implements FooInterface {

    @Override
    public void hello() {
        System.out.println("aa");
    }

    @Override
    public String hehe(int a) {
        return "hehe " + a;
    }
}

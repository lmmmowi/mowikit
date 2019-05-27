package com.lmmmowi.mowikit.proxy.javassist;

import com.lmmmowi.mowikit.proxy.Invoker;

/**
 * @Author: mowi
 * @Date: 2019-05-25
 * @Description:
 */
public interface JavassistProxy {

    Object newInstance(Invoker invoker);

}

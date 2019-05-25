package com.lmmmowi.mowikit.spi;

import com.lmmmowi.mowikit.spi.foo.FooInterface;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Method;

/**
 * @Author: mowi
 * @Date: 2019-05-23
 * @Description:
 */
public class ExtensionLoaderTest {

    @Test
    public void test_getExtensionLoader() {
        ExtensionLoader extensionLoader = ExtensionLoader.getExtensionLoader(FooInterface.class);
        Assert.assertEquals(FooInterface.class, extensionLoader.getInterfaceType());
    }

    @Test
    public void test_getExtension() {
        ExtensionLoader<FooInterface> extensionLoader = ExtensionLoader.getExtensionLoader(FooInterface.class);
        FooInterface fooInterface = extensionLoader.getExtension("a2");
        fooInterface.hello();
    }

    @Test
    public void test_loadExtensionClasses() {
        ExtensionLoader<FooInterface> extensionLoader = ExtensionLoader.getExtensionLoader(FooInterface.class);

        try {
            Method method = ExtensionLoader.class.getDeclaredMethod("loadExtensionClasses");
            method.setAccessible(true);
            method.invoke(extensionLoader);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}

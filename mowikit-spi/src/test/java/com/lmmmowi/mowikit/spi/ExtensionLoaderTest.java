package com.lmmmowi.mowikit.spi;

import com.lmmmowi.mowikit.spi.foo.FooInterface;
import org.junit.Assert;
import org.junit.Test;

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
        FooInterface fooInterface = extensionLoader.getExtension();
        fooInterface.hello();
    }
}

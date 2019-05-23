package com.lmmmowi.mowikit.spi;

import com.lmmmowi.mowikit.log.Logger;
import com.lmmmowi.mowikit.log.LoggerFactory;
import com.lmmmowi.mowikit.spi.foo.Foo;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @Author: mowi
 * @Date: 2019-05-23
 * @Description:
 */
public class ExtensionLoader<T> {

    private static final Logger logger = LoggerFactory.getLogger(ExtensionLoader.class);

    private static final ConcurrentMap<Class<?>, Object> EXTENSION_INSTANCES = new ConcurrentHashMap<Class<?>, Object>();

    //___________________________________________________

    private final ConcurrentMap<String, Holder<T>> cachedInstances = new ConcurrentHashMap<String, Holder<T>>();

    private final Holder<Map<String, Class<?>>> cachedClasses = new Holder<Map<String, Class<?>>>();

    private Class<?> interfaceType;

    private ExtensionLoader(Class<T> interfaceType) {
        this.interfaceType = interfaceType;
    }

    @SuppressWarnings("unchecked")
    public static <M> ExtensionLoader<M> getExtensionLoader(Class<M> interfaceType) {
        return new ExtensionLoader(interfaceType);
    }

    public Class<?> getInterfaceType() {
        return interfaceType;
    }

    public T getExtension() {
        return this.getExtension("default");
    }

    public T getExtension(String name) {
        if (name == null || name.length() == 0) {
            throw new IllegalArgumentException("Extension name == null");
        }

        Holder<T> holder = cachedInstances.get(name);
        if (holder == null) {
            holder = new Holder<T>();
            cachedInstances.putIfAbsent(name, holder);
        }

        T instance = holder.get();
        if (instance == null) {
            synchronized (holder) {
                instance = holder.get();
                if (instance == null) {
                    instance = createExtensionInstance(name);
                    holder.set(instance);
                }
            }
        }

        return instance;
    }

    @SuppressWarnings("unchecked")
    private T createExtensionInstance(String name) {
        Class<?> extensionClass = this.getExtensionClass(name);
        if (extensionClass == null) {
            throw new IllegalStateException("no instance class of type " + interfaceType.getName() + " found for name " + name);
        }

        try {
            Object extensionInstance = EXTENSION_INSTANCES.get(extensionClass);
            if (extensionInstance == null) {
                extensionInstance = extensionClass.newInstance();
                EXTENSION_INSTANCES.putIfAbsent(extensionClass, extensionInstance);
            }
            return (T) extensionInstance;
        } catch (Throwable e) {
            throw new IllegalStateException("Extension instance (name: " + name + ", class: " +
                    interfaceType + ") couldn't be instantiated: " + e.getMessage(), e);
        }
    }

    private Class<?> getExtensionClass(String name) {
        return getExtensionClasses().get(name);
    }

    private Map<String, Class<?>> getExtensionClasses() {
        Map<String, Class<?>> classes = cachedClasses.get();
        if (classes == null) {
            synchronized (cachedClasses) {
                classes = cachedClasses.get();
                if (classes == null) {
                    classes = this.loadExtensionClasses();
                    cachedClasses.set(classes);
                }
            }
        }
        return classes;
    }

    private synchronized Map<String, Class<?>> loadExtensionClasses() {
        Map<String, Class<?>> map = new HashMap<String, Class<?>>();
        map.put("default", Foo.class);
        return map;
    }
}

package com.lmmmowi.mowikit.proxy.util;

/**
 * @Author: mowi
 * @Date: 2019-05-25
 * @Description:
 */
public class ReflectUtils {

    public static String getName(Class<?> c) {
        if (c.isArray()) {
            StringBuilder sb = new StringBuilder();
            do {
                sb.append("[]");
                c = c.getComponentType();
            }
            while (c.isArray());

            return c.getName() + sb.toString();
        }
        return c.getName();
    }

}

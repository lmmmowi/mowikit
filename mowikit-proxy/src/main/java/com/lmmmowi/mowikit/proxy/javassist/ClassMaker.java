package com.lmmmowi.mowikit.proxy.javassist;

import com.lmmmowi.mowikit.proxy.Invoker;
import com.lmmmowi.mowikit.proxy.util.ReflectUtils;
import javassist.*;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: mowi
 * @Date: 2019-05-25
 * @Description:
 */
public class ClassMaker {

    private ClassLoader classLoader;

    public ClassMaker(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public Class makeClass(Class type) throws Exception {
        ClassPool classPool = ClassPool.getDefault();

        String cn = type.getName() + ".proxy.proxy0";
        CtClass ctClass = classPool.makeClass(cn);

        ctClass.addField(CtField.make("private " + Invoker.class.getName() + " invoker;", ctClass));

        ctClass.addConstructor(CtNewConstructor.make(getConstructorString(cn), ctClass));
        ctClass.addConstructor(CtNewConstructor.defaultConstructor(ctClass));

        ctClass.addInterface(classPool.get(type.getName()));
        List<Method> methods = new ArrayList<>();
        for (Method method : type.getMethods()) {
            if (method.getDeclaringClass() != Object.class) {
                methods.add(method);
            }
        }
        ctClass.addField(CtField.make("public static java.lang.reflect.Method[] methods;", ctClass));
        for (int i = 0; i < methods.size(); i++) {
            ctClass.addMethod(CtMethod.make(getMethodString(methods.get(i), i), ctClass));
        }

        ctClass.setSuperclass(classPool.get(JavassistProxy.class.getName()));
        ctClass.addMethod(CtNewMethod.make(getNewInstanceString(cn), ctClass));

        Class clazz = ctClass.toClass(classLoader, getClass().getProtectionDomain());
        clazz.getField("methods").set(null, methods.toArray(new Method[0]));

        return clazz;
    }

    private String getConstructorString(String cn) {
        StringBuilder sb = new StringBuilder();
        sb.append("public ")
                .append(cn.substring(cn.lastIndexOf(".") + 1))
                .append("(")
                .append(Invoker.class.getName()).append(" invoker")
                .append("){this.invoker = invoker;}");
        System.out.println(sb.toString());
        return sb.toString();
    }

    private String getNewInstanceString(String cn) {
        StringBuilder sb = new StringBuilder();
        sb.append("public Object newInstance(" + Invoker.class.getName() + " invoker) { ")
                .append("return new " + cn + "(invoker);")
                .append("}");
        System.out.println(sb.toString());
        return sb.toString();
    }

    private String getMethodString(Method method, int index) {
        Class<?> rt = method.getReturnType();
        Class<?>[] pts = method.getParameterTypes();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(modifier(method.getModifiers()))
                .append(" ")
                .append(ReflectUtils.getName(rt))
                .append(" ")
                .append(method.getName())
                .append("(");

        for (int i = 0; i < pts.length; i++) {
            if (i > 0) {
                stringBuilder.append(',');
            }
            stringBuilder.append(ReflectUtils.getName(pts[i]));
            stringBuilder.append(" arg").append(i);
        }

        stringBuilder.append("){");

        StringBuilder code = new StringBuilder("Object[] args = new Object[").append(pts.length).append("];");
        for (int j = 0; j < pts.length; j++) {
            code.append(" args[").append(j).append("] = ($w)$").append(j + 1).append(";");
        }
        stringBuilder.append(code);


        stringBuilder.append("return ");
        if (!"void".equals(rt.getName())) {
            stringBuilder.append("(").append(ReflectUtils.getName(rt)).append(")");
        }
        stringBuilder.append("invoker.invoke(invoker, methods[" + index + "], args); }");

        System.out.println(stringBuilder.toString());
        return stringBuilder.toString();
    }

    private static String modifier(int mod) {
        StringBuilder modifier = new StringBuilder();
        if (java.lang.reflect.Modifier.isPublic(mod)) {
            modifier.append("public");
        }
        if (java.lang.reflect.Modifier.isProtected(mod)) {
            modifier.append("protected");
        }
        if (java.lang.reflect.Modifier.isPrivate(mod)) {
            modifier.append("private");
        }

        if (java.lang.reflect.Modifier.isStatic(mod)) {
            modifier.append(" static");
        }
        if (Modifier.isVolatile(mod)) {
            modifier.append(" volatile");
        }

        return modifier.toString();
    }
}

package com.lmmmowi.mowikit.proxy.javassist;

import com.lmmmowi.mowikit.log.Logger;
import com.lmmmowi.mowikit.log.LoggerFactory;
import com.lmmmowi.mowikit.proxy.Invoker;
import com.lmmmowi.mowikit.proxy.util.ReflectUtils;
import javassist.*;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author: mowi
 * @Date: 2019-05-25
 * @Description:
 */
class ClassMaker {

    private static final Logger logger = LoggerFactory.getLogger(ClassMaker.class);

    private ClassLoader classLoader;

    private Class proxyClass;

    private Set<Class> interfaceClassSet = new HashSet<>();

    private List<Method> methods = new ArrayList<>();

    private Set<String> worked = new HashSet<>();

    public ClassMaker(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public void setProxyClass(Class proxyClass) {
        if (this.proxyClass == null) {
            this.proxyClass = proxyClass;

            if (!proxyClass.isInterface()) {
                this.loadClassMethods(proxyClass);
            }
        }
    }

    public void addInterface(Class itf) {
        if (itf.isInterface() && !interfaceClassSet.contains(itf)) {
            interfaceClassSet.add(itf);
            this.loadClassMethods(itf);
        }
    }

    private void loadClassMethods(Class clazz) {
        for (Method method : clazz.getMethods()) {
            if (method.getDeclaringClass() != Object.class) {
                String desc = ReflectUtils.getDesc(method);
                if (!worked.contains(desc)) {
                    methods.add(method);
                    worked.add(desc);
                }
            }
        }
    }

    public Class makeClass() throws Exception {
        Class[] interfaces = interfaceClassSet.toArray(new Class[0]);

        ClassPool classPool = ClassPool.getDefault();

        String cn = proxyClass.getName() + ".proxy.proxy0";
        CtClass ctClass = classPool.makeClass(cn);

        // 如果被代理类不是接口，则继承被代理类
        Class superClass = proxyClass.isInterface() ? null : proxyClass;
        if (superClass != null) {
            ctClass.setSuperclass(classPool.get(superClass.getName()));
        }

        // 实现的接口
        for (Class itf : interfaces) {
            ctClass.addInterface(classPool.get(itf.getName()));
        }

        // 静态字段用来持有被代理类的所有方法
        ctClass.addField(CtField.make("public static java.lang.reflect.Method[] methods;", ctClass));

        // invoker字段
        ctClass.addField(CtField.make("private " + Invoker.class.getName() + " invoker;", ctClass));

        // 默认构造函数
        ctClass.addConstructor(CtNewConstructor.defaultConstructor(ctClass));

        // 传入invoker的构造函数
        ctClass.addConstructor(CtNewConstructor.make(getConstructorString(cn), ctClass));

        // 添加方法
        for (int i = 0; i < methods.size(); i++) {
            ctClass.addMethod(CtMethod.make(getMethodString(methods.get(i), i), ctClass));
        }

        // 实现JavassistProxy接口，添加创建代理的方法
        ctClass.addInterface(classPool.get(JavassistProxy.class.getName()));
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

        String s = sb.toString();
        if (logger.isDebugEnabled()) {
            logger.debug(s);
        }
        return s;
    }

    private String getNewInstanceString(String cn) {
        StringBuilder sb = new StringBuilder();
        sb.append("public Object newInstance(" + Invoker.class.getName() + " invoker) { ")
                .append("return new " + cn + "(invoker);")
                .append("}");

        String s = sb.toString();
        if (logger.isDebugEnabled()) {
            logger.debug(s);
        }
        return s;
    }

    private String getMethodString(Method method, int index) {
        Class<?> rt = method.getReturnType();
        Class<?>[] pts = method.getParameterTypes();

        StringBuilder sb = new StringBuilder();
        sb.append(modifier(method.getModifiers()))
                .append(" ")
                .append(ReflectUtils.getName(rt))
                .append(" ")
                .append(method.getName())
                .append("(");

        for (int i = 0; i < pts.length; i++) {
            if (i > 0) {
                sb.append(',');
            }
            sb.append(ReflectUtils.getName(pts[i]));
            sb.append(" arg").append(i);
        }

        sb.append("){");

        StringBuilder code = new StringBuilder("Object[] args = new Object[").append(pts.length).append("];");
        for (int j = 0; j < pts.length; j++) {
            code.append(" args[").append(j).append("] = ($w)$").append(j + 1).append(";");
        }
        sb.append(code);


        sb.append("return ");
        if (!"void".equals(rt.getName())) {
            sb.append("(").append(ReflectUtils.getName(rt)).append(")");
        }
        sb.append("invoker.invoke(invoker, methods[" + index + "], args); }");

        String s = sb.toString();
        if (logger.isDebugEnabled()) {
            logger.debug(s);
        }
        return s;
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

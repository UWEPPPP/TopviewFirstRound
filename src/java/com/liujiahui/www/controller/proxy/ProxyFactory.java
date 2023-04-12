package com.liujiahui.www.controller.proxy;

import java.lang.reflect.Proxy;
import java.util.logging.Logger;

/**
 * @author 刘家辉
 * @date 2023/04/13
 */
public class ProxyFactory {
    private static final Logger LOGGER = Logger.getLogger("ProxyController");

    /**
     * 创建代理
     *
     * @param target 目标
     * @return {@link Object}
     */
    public static Object createProxy(Object target) {
        ClassLoader classLoader = target.getClass().getClassLoader();
        Class<?>[] interfaces = target.getClass().getInterfaces();
        return Proxy.newProxyInstance(classLoader, interfaces, (proxy, method, args) -> {
            try {
                return method.invoke(target, args);
            } catch (RuntimeException e) {
                LOGGER.log(java.util.logging.Level.WARNING, "服务层抛出异常", e);
                return null;
            }
        });
    }
}

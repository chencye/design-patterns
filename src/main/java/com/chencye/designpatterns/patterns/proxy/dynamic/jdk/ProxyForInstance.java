package com.chencye.designpatterns.patterns.proxy.dynamic.jdk;

import com.chencye.designpatterns.patterns.common.KeyGenerator;
import com.chencye.designpatterns.patterns.proxy.LoginServer;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * 只有接口，生成其代理实例
 *
 * @author chencye
 */
@Slf4j
public class ProxyForInstance implements InvocationHandler {

    private Object instance;

    public ProxyForInstance(Object instance) {
        this.instance = instance;
    }

    public static Object newInstance(Object instance) {
        return Proxy.newProxyInstance(instance.getClass().getClassLoader(), instance.getClass().getInterfaces(), new ProxyForInstance(instance));
    }

    public static void main(String[] args) {
        LoginServer defaultLoginServer = (username, password) -> {
            log.info("myLoginServer: username={}, password={}", username, password);
            return KeyGenerator.gen(username);
        };
        LoginServer loginServer = (LoginServer) ProxyForInstance.newInstance(defaultLoginServer);
        String ticketId = loginServer.login("username1", "pwd1");
        log.info("ticketId={}", ticketId);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.info("---------------proxy before start--------------------");
        log.info("proxy instance: {}", proxy.getClass().getName());
        log.info("origin interface: {}", Arrays.toString(proxy.getClass().getInterfaces()));
        log.info("method: {}", method.getName());
        log.info("args: {}", Arrays.toString(args));
        log.info("---------------proxy before end--------------------");
        Object result = method.invoke(instance, args);
        log.info("---------------proxy after start--------------------");
        log.info("result={}", result);
        log.info("---------------proxy after end--------------------");
        return result;
    }
}

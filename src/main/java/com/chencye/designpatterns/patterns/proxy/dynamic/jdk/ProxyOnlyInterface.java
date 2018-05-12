package com.chencye.designpatterns.patterns.proxy.dynamic.jdk;

import com.chencye.designpatterns.patterns.common.KeyGenerator;
import com.chencye.designpatterns.patterns.proxy.LoginServer;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 只有接口，生成其代理实例
 *
 * @author chencye
 */
@Slf4j
public class ProxyOnlyInterface implements InvocationHandler {

    public ProxyOnlyInterface() {
    }

    public static Object newInstance(Class clzz) {
        return java.lang.reflect.Proxy.newProxyInstance(clzz.getClassLoader(), new Class[]{clzz}, new ProxyOnlyInterface());
    }

    public static void main(String[] args) {
        LoginServer loginServer = (LoginServer) ProxyOnlyInterface.newInstance(LoginServer.class);
        String ticketId = loginServer.login("username1", "pwd1");
        log.info("ticketId={}", ticketId);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.info("proxy instance: {}", proxy.getClass().getName());
        log.info("origin interface: {}", Arrays.toString(proxy.getClass().getInterfaces()));
        log.info("method: {}", method.getName());
        log.info("args: {}", Arrays.toString(args));

        // dosomething
        String result = KeyGenerator.gen(String.valueOf(args[0]));
        log.info("result={}", result);

        return result;
    }
}

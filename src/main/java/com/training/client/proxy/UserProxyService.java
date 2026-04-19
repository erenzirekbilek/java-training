package com.training.client.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class UserProxyService {
    
    public interface UserInterface {
        String getUsername();
        void setUsername(String username);
    }
    
    public static class UserImpl implements UserInterface {
        private String username;
        
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
    }
    
    public UserInterface createProxy(UserInterface target) {
        return (UserInterface) Proxy.newProxyInstance(
            target.getClass().getClassLoader(),
            target.getClass().getInterfaces(),
            (proxy, method, args) -> {
                System.out.println("Before method: " + method.getName());
                Object result = method.invoke(target, args);
                System.out.println("After method: " + method.getName());
                return result;
            }
        );
    }
}
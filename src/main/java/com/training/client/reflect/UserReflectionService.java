package com.training.client.reflect;

import java.lang.reflect.*;

public class UserReflectionService {
    
    public void printClassInfo(Class<?> clazz) {
        System.out.println("Class: " + clazz.getName());
        for (Field field : clazz.getDeclaredFields()) {
            System.out.println("Field: " + field.getName() + " - " + field.getType());
        }
        for (Method method : clazz.getDeclaredMethods()) {
            System.out.println("Method: " + method.getName());
        }
    }

    public Object createInstance(Class<?> clazz) throws Exception {
        return clazz.getDeclaredConstructor().newInstance();
    }

    public Object invokeMethod(Object obj, String methodName, Object... args) throws Exception {
        Method method = obj.getClass().getMethod(methodName);
        return method.invoke(obj, args);
    }

    public Object getFieldValue(Object obj, String fieldName) throws Exception {
        Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(obj);
    }
}
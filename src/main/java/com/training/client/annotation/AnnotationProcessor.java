package com.training.client.annotation;

import java.lang.reflect.Method;

public class AnnotationProcessor {
    
    public void processAnnotations(Object obj) throws Exception {
        Class<?> clazz = obj.getClass();
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(LogExecution.class)) {
                LogExecution annotation = method.getAnnotation(LogExecution.class);
                System.out.println("Processing method: " + method.getName() + " - " + annotation.value());
            }
        }
    }
}
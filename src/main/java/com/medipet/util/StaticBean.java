package com.medipet.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class StaticBean implements ApplicationContextAware {

    private static ApplicationContext context;
    private static Map map = new HashMap();

    public static void setContext(ApplicationContext context) {
        StaticBean.context = context;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public static <T> T getBean(Class<T> clazz) {
        T t = (T) map.get(clazz);
        if (t != null) {
            return t;
        } else {
            T bean = context.getBean(clazz);
            map.put(clazz, bean);
            return bean;
        }
    }

}

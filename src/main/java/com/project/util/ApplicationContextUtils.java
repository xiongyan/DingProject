package com.project.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

/**
 * Created by laishun on 2018/3/16.
 */
public class ApplicationContextUtils {
    private static ApplicationContext applicationContext;

    public static void setApplicationContext(ApplicationContext app) throws BeansException {
        if(applicationContext == null){
            ApplicationContextUtils.applicationContext = app;
        }
    }

    public static ApplicationContext getApplicationContext() {
        return ApplicationContextUtils.applicationContext;
    }

    public static <T> T getBean(Class<T> t) {
        return ApplicationContextUtils.applicationContext.getBean(t);
    }
}

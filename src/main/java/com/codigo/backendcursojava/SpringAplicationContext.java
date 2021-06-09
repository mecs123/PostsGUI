package com.codigo.backendcursojava;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
//Esta clase es para acceder al contexto de la aplicacion

public class SpringAplicationContext implements ApplicationContextAware {
    private static ApplicationContext CONTEXT;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        CONTEXT = applicationContext;
    }

    public static Object getBean(String beanName){
        return CONTEXT.getBean(beanName);
    }
}

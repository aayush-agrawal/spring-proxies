package com.aayush.proxies;

import com.aayush.proxies.annotation.MyTransactional;
import com.aayush.proxies.utils.Utility;
import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class MyTransactionalBeanPostProcessor implements BeanPostProcessor {

  @Override
  public Object postProcessAfterInitialization(Object target, String beanName) throws BeansException {
//    System.out.println("Bean post processor bean name: " + beanName);
    if (Utility.transactional(target)) {
      var pf = new ProxyFactory();
      pf.setTarget(target);
      pf.setInterfaces( target.getClass().getInterfaces() );
      MethodInterceptor interceptor = invocation -> {
        var method = invocation.getMethod();
        var arguments = invocation.getArguments();
        try {
          if (method.getAnnotation(MyTransactional.class) != null) {
            System.out.println("Transaction started");
          }
          return method.invoke(target, arguments);
        } finally {
          if (method.getAnnotation(MyTransactional.class) != null) {
            System.out.println("Transaction committed");
          }
        }
      };
      pf.addAdvice(interceptor);
      return pf.getProxy();
    }
    return BeanPostProcessor.super.postProcessAfterInitialization(target, beanName);
  }

}

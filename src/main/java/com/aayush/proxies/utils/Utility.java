package com.aayush.proxies.utils;

import com.aayush.proxies.annotation.MyTransactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicBoolean;
import org.springframework.util.ReflectionUtils;

public class Utility {

  public static boolean transactional(Object o) {
    var hasTransactional = new AtomicBoolean(false);
    var classes = new ArrayList<Class<?>>();
    classes.add(o.getClass());
    Collections.addAll(classes, o.getClass().getInterfaces());
    classes.forEach( clazz -> {
      ReflectionUtils.doWithMethods(clazz, method -> {
        if (method.getAnnotation(MyTransactional.class) != null) {
          hasTransactional.set(true);
        }
      });
    });
    return hasTransactional.get();
  }

}

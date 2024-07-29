package com.aayush.proxies.service;

import com.aayush.proxies.annotation.MyTransactional;

public interface CustomerService {

  @MyTransactional
  void create();

}

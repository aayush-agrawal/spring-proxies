package com.aayush.proxies.service;

import org.springframework.stereotype.Service;

@Service
public class DefaultCustomerService implements CustomerService {

  @Override
  public void create() {
    System.out.println("Creating a customer");
  }

}

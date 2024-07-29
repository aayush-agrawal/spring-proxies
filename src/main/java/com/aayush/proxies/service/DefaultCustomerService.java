package com.aayush.proxies.service;

public class DefaultCustomerService implements CustomerService {

  @Override
  public void create() {
    System.out.println("Creating a customer");
  }

}

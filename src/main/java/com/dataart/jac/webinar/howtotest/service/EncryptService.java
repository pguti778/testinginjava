package com.dataart.jac.webinar.howtotest.service;

public class EncryptService {

  final static String KEY = "SECRET";

  public String encryptPassword(String password) {
    return password+KEY;
  }

  public String decryptPassword(String mangled) {
    return mangled.substring(0, mangled.length()-KEY.length());
  }

}

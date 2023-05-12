package com.dataart.jac.webinar.howtotest.service;

import com.dataart.jac.webinar.howtotest.gateway.RemoteMD5Client;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ManglingService {

  static final String SALT = "SALTKEY";

  // Injected bean
  RemoteMD5Client remoteMD5Client;

  public String saltedMD5(String value) {
    if(value != null && !("".equals(value))) {
      return remoteMD5Client.md5sum(value + SALT);
    } else {
      return remoteMD5Client.md5sum(value);
    }
  }

}

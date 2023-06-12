package com.dataart.jac.webinar.howtotest.service.d.springboot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EncryptController {



  @GetMapping("/do-a-test")
  public String encrypt() {
    return "This is a test";
  }

  @PostMapping(value = "/encrypt")
  public String encrypt(String palinText) {
    return "encrypt";

  }

}

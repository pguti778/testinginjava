package com.dataart.jac.webinar.howtotest.gateway;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Component
@FeignClient(name = "jsontest", url = "http://md5.jsontest.com/")
public interface RemoteMD5Client {

  @RequestMapping(method = RequestMethod.GET, value = "?text={text}")
  String md5sum(@PathVariable("text") final String text);

}

package com.dataart.jac.webinar.howtotest.service.a.donts;

import java.math.BigDecimal;
import java.math.RoundingMode;
import org.springframework.stereotype.Service;

/**
 * You can also test in MAINs, but there are several cons and should be avoided.
 */
@Service
public class MainService {

  public static void main(String[] args) {
    var b1 = new BigDecimal("0.2");
    var b2 = new BigDecimal(0.2);
    var b3 = BigDecimal.valueOf(1/5f);

    System.out.println(b1);
    System.out.println(b2);
    System.out.println(b3);

    System.out.println(b1.equals(b2));
    System.out.println(b1.equals(b3));
    System.out.println(b1.equals(b3.setScale(2, RoundingMode.HALF_DOWN)));
  }

}

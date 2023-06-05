package com.dataart.jac.webinar.howtotest.utils;

import java.util.Objects;
import java.util.Random;
import java.util.random.RandomGenerator;

public class CommonUtils {

  static String EmptyOrRandom(String value) {
    if ("".equals(Objects.toString(value, ""))) {
      return Long.toString(Random.from(RandomGenerator.getDefault()).nextLong());
    }
    return value;
  }

}

package com.dataart.jac.webinar.howtotest;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HowtotestApplicationTests {

	@Test
	void contextLoads() {
		List<String> list = new ArrayList<String>();

		list.get(0);
	}

}

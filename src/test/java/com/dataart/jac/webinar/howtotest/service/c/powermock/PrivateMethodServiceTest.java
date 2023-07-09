package com.dataart.jac.webinar.howtotest.service.c.powermock;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = PrivateMethodService.class)
class PrivateMethodServiceTest {

    @Autowired
    PrivateMethodService privateMethodService;

    @Test
    void testSomething() {
        Boolean b = ReflectionTestUtils.invokeMethod(privateMethodService, "evaluateApproved", 50);
        System.out.println(b);
    }

}
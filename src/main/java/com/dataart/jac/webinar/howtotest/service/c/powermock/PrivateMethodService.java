package com.dataart.jac.webinar.howtotest.service.c.powermock;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PrivateMethodService {

    public boolean allApproved(List<Integer> grades) {
        for(Integer grade : grades) {
            if(evaluateApproved(grade))
                return false;
        }
        return true;
    }

    private boolean evaluateApproved(final Integer grade) {
        return grade > 70;
    }


}

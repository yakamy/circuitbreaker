package com.yakamy.circuitbreaker;

import com.yakamy.circuitbreaker.service.CallService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CircuitbreakerApplicationTests {


    @Autowired
    private CallService callService;

    @Test
    void contextLoads() {

    }

}

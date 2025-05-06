package com.fiveup.core;


import com.fiveup.core.management.controller.TopologyController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TopologyTest {
    @Autowired
    private TopologyController controller;
    @Test
    void test(){
        controller.getTopology(1L);
    }
}

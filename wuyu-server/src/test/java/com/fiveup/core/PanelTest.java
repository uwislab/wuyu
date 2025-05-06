package com.fiveup.core;


import com.fiveup.core.management.controller.PanelController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PanelTest {
    @Autowired
    private PanelController controller;

    @Test
    void test() {
        controller.getPanelData(1);
    }
}

package com.tabak.test.task2.aligntest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.tabak.test.task2.aligntest")
public class AlignTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlignTestApplication.class, args);
    }
}

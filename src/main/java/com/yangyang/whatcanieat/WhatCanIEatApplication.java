package com.yangyang.whatcanieat;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.yangyang.whatcanieat.mapper")
public class WhatCanIEatApplication {

    public static void main(String[] args) {
        SpringApplication.run(WhatCanIEatApplication.class, args);
    }

}

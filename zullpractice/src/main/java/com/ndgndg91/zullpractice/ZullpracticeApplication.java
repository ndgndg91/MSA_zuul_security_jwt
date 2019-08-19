package com.ndgndg91.zullpractice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@SpringBootApplication
public class ZullpracticeApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(ZullpracticeApplication.class, args);
    }

}

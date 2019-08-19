package com.ndgndg91.foos.zuuldemofoos.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ndgndg91.foos.zuuldemofoos.domain.Foo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Random;


@RestController
public class FooController {

    private static final Logger log = LoggerFactory.getLogger(FooController.class);

    @GetMapping(value = "/foos/{id}", produces = "application/json; charset=utf8")
    public String findById(@PathVariable long id, HttpServletRequest req, HttpServletResponse res) throws JsonProcessingException {
        if (req.getHeader("ndgndg91") != null) {
            log.info("zuul proxy를 타고 들어왔다.");
            res.addHeader("ndgndg91", req.getHeader("ndgndg91"));
        }
        return new ObjectMapper().writeValueAsString(new Foo(new Random(id).nextLong(), "동길"));
    }
}

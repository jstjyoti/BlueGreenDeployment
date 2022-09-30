package com.jstjyoti.springboot;

import java.util.logging.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SpringBootDemo {

  private static final Logger LOGGER = Logger.getLogger(SpringBootDemo.class.getName());

  @RequestMapping("/version")
  public String home() {
    LOGGER.info("Serving request from version 1");
    return "{\"id\":1,\"content\":\"current\"}";
  }

  public static void main(String[] args) {
    SpringApplication.run(SpringBootDemo.class, args);
  }
}
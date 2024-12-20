package com.cosmo.cats.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class CosmoCatsApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(CosmoCatsApiApplication.class, args);
  }

}

package in.zeta.aether.spring.initializr.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SimpleServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(SimpleServiceApplication.class, args);
  }
}

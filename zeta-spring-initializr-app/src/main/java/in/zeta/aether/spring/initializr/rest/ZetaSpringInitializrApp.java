package in.zeta.aether.spring.initializr.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ZetaSpringInitializrApp {

  public static void main(String[] args) {
    SpringApplication.run(ZetaSpringInitializrApp.class, args);
  }
}

package in.zeta.spring.initializr.app;

import org.springframework.beans.factory.annotation.Lookup;
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

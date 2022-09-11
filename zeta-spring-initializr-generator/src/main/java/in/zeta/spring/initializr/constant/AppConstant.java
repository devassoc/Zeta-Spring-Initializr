package in.zeta.spring.initializr.constant;

public class AppConstant {

  // TODO: Read from config
  public static final String ZETA_MAVEN_PARENT_GROUP_ID = "in.zeta";
  public static final String ZETA_MAVEN_PARENT_ARTIFACT_ID = "zeta-spring-boot-pom";
  public static final String ZETA_MAVEN_PARENT_VERSION = "1.1.2";

  // TODO: Read from config
  public static final String ZETA_MAVEN_COMMONS_GROUP_ID = "in.zeta";
  public static final String ZETA_MAVEN_COMMONS_ARTIFACT_ID = "spring-boot-commons";
  public static final String ZETA_MAVEN_COMMONS_VERSION = "2.7.3";

  // TODO: Read from config
  public static final String ZETA_MAVEN_H2_GROUP_ID = "com.h2database";
  public static final String ZETA_MAVEN_H2_ARTIFACT_ID = "h2";
  public static final String ZETA_MAVEN_H2_VERSION = "2.0.202";

  public static final String REST_CONTROLLER_ANNOTATION =
      "org.springframework.web.bind.annotation.RestController";
  public static final String REQUEST_MAPPING_ANNOTATION =
      "org.springframework.web.bind.annotation.RequestMapping";
  public static final String GET_MAPPING_ANNOTATION =
      "org.springframework.web.bind.annotation.GetMapping";
  public static final String CROSS_ORIGIN_ANNOTATION =
      "org.springframework.web.bind.annotation.CrossOrigin";
  public static final String REPOSITORY_ANNOTATION = "org.springframework.stereotype.Repository";

  public static final String SERVICE_ANNOTATION = "org.springframework.stereotype.Service";

  public static final String CLOCK_ANNOTATION = "in.zeta.springframework.boot.commons.annotations.Clocked";

  public static final String STRING_TYPE = "java.lang.String";

  public static final String ZETA_SPRING_COMMON_BASE_PACKAGE =
      "in.zeta.springframework.boot.commons";
}

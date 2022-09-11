package in.zeta.spring.initializr.contributor;

import in.zeta.spring.initializr.constant.AppConstant;
import io.spring.initializr.generator.language.Annotation;
import io.spring.initializr.generator.language.TypeDeclaration;
import io.spring.initializr.generator.project.ProjectDescription;
import io.spring.initializr.generator.project.ProjectGenerationConfiguration;
import io.spring.initializr.generator.spring.code.MainApplicationTypeCustomizer;
import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Bean;

@ProjectGenerationConfiguration
public class MainClassProjectGenerationConfiguration {

  private final ProjectDescription description;

  List<String> mainApplicationAnnotations = new ArrayList<>();

  public MainClassProjectGenerationConfiguration(ProjectDescription description) {
    this.description = description;
    mainApplicationAnnotations.add(AppConstant.ZETA_SPRING_COMMON_BASE_PACKAGE);
    mainApplicationAnnotations.add(description.getPackageName());
  }

  @Bean
  public MainApplicationTypeCustomizer<TypeDeclaration> mainApplicationAnnotations() {
    return (typeDeclaration) -> {
      Annotation annotations =
          Annotation.name(
              "org.springframework.context.annotation.ComponentScan",
              (builder) ->
                  builder.attribute(
                      "basePackages",
                      String.class,
                      mainApplicationAnnotations.toArray(new String[0])));
      typeDeclaration.annotate(annotations);
    };
  }
}

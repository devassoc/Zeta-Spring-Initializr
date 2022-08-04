package in.zeta.aether.spring.initializr.contributor;

import io.spring.initializr.generator.language.Annotation;
import io.spring.initializr.generator.language.TypeDeclaration;
import io.spring.initializr.generator.project.ProjectGenerationConfiguration;
import io.spring.initializr.generator.spring.code.MainApplicationTypeCustomizer;
import java.util.Arrays;
import java.util.List;
import org.springframework.context.annotation.Bean;

@ProjectGenerationConfiguration
public class MainClassProjectGenerationConfiguration {

  List<String> mainApplicationAnnotations = Arrays.asList(
      "in.zeta.gateway.common", "in.zeta.springframework.boot.commons", "in.zeta.openapi"
  );

  @Bean
	public MainApplicationTypeCustomizer<TypeDeclaration> mainApplicationAnnotations() {
		return (typeDeclaration) -> {
      Annotation requestMappingAnnotation = Annotation.name("org.springframework.context.annotation.ComponentScan", (builder) -> builder.attribute("basePackages", String.class,
          mainApplicationAnnotations.toArray(new String[0])));
			typeDeclaration.annotate(requestMappingAnnotation);
		};
	}
}

package in.zeta.spring.initializr.contributor.code;

import io.spring.initializr.generator.io.IndentingWriterFactory;
import io.spring.initializr.generator.language.java.JavaCompilationUnit;
import io.spring.initializr.generator.language.java.JavaSourceCode;
import io.spring.initializr.generator.language.java.JavaSourceCodeWriter;
import io.spring.initializr.generator.language.java.JavaTypeDeclaration;
import io.spring.initializr.generator.project.ProjectDescription;
import io.spring.initializr.generator.project.ProjectGenerationConfiguration;
import org.springframework.context.annotation.Bean;

@ProjectGenerationConfiguration
public class ControllerProjectGenerationConfiguration {

  private final ProjectDescription description;
  private final IndentingWriterFactory indentingWriterFactory;

  public ControllerProjectGenerationConfiguration(
      ProjectDescription description, IndentingWriterFactory indentingWriterFactory) {
    this.description = description;
    this.indentingWriterFactory = indentingWriterFactory;
  }

  @Bean
  public ControllerContributor<JavaTypeDeclaration, JavaCompilationUnit, JavaSourceCode>
      controllerContributor() {
    return new ControllerContributor(
        this.description,
        JavaSourceCode::new,
        new JavaSourceCodeWriter(this.indentingWriterFactory));
  }
}

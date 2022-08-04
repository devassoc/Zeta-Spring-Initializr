package in.zeta.aether.spring.initializr.contributor;

import io.spring.initializr.generator.language.Annotation;
import io.spring.initializr.generator.language.Annotation.Attribute;
import io.spring.initializr.generator.language.CompilationUnit;
import io.spring.initializr.generator.language.Parameter;
import io.spring.initializr.generator.language.SourceCode;
import io.spring.initializr.generator.language.SourceCodeWriter;
import io.spring.initializr.generator.language.TypeDeclaration;
import io.spring.initializr.generator.language.java.JavaExpressionStatement;
import io.spring.initializr.generator.language.java.JavaMethodDeclaration;
import io.spring.initializr.generator.language.java.JavaMethodInvocation;
import io.spring.initializr.generator.language.java.JavaReturnStatement;
import io.spring.initializr.generator.language.java.JavaStatement;
import io.spring.initializr.generator.language.java.JavaTypeDeclaration;
import io.spring.initializr.generator.project.ProjectDescription;
import io.spring.initializr.generator.project.contributor.ProjectContributor;
import io.spring.initializr.generator.spring.code.MainApplicationTypeCustomizer;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.function.Supplier;

public class ControllerContributor<T extends TypeDeclaration, C extends CompilationUnit<T>, S extends SourceCode<T, C>> implements ProjectContributor {

  private final ProjectDescription projectDescription;

  private final Supplier<S> sourceFactory;

  private final SourceCodeWriter<S> sourceWriter;


  ControllerContributor(ProjectDescription projectDescription, Supplier<S> sourceFactory, SourceCodeWriter<S> sourceWriter){
    this.projectDescription = projectDescription;
    this.sourceFactory = sourceFactory;
    this.sourceWriter = sourceWriter;
  }

  @Override
  public void contribute(Path projectRoot){

    S sourceCode = this.sourceFactory.get();
    String className = "AetherController";
    C compilationUnit = sourceCode.createCompilationUnit(this.projectDescription.getPackageName()+".controller", className);
    T mainApplicationType = compilationUnit.createTypeDeclaration(className);

    createRestController(mainApplicationType);
    try {
      this.sourceWriter.writeTo(
          this.projectDescription.getBuildSystem().getMainSource(projectRoot, this.projectDescription.getLanguage()),
          sourceCode);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  void createRestController(T javaTypeDeclaration) {
    JavaTypeDeclaration restController = (JavaTypeDeclaration) javaTypeDeclaration;
    restController.modifiers(Modifier.PUBLIC);
    restController.annotate(Annotation.name("org.springframework.web.bind.annotation.RestController"));

    Annotation requestMappingAnnotation = Annotation.name("org.springframework.web.bind.annotation.RequestMapping", (builder) -> builder.attribute("value", String.class,
        "api/v1"));
    restController.annotate(requestMappingAnnotation);


    JavaMethodDeclaration getApiMethod = JavaMethodDeclaration.method("getName").modifiers(Modifier.PUBLIC).returning("String")
        .parameters(new Parameter("java.lang.String", "name"))
        .body(
            new JavaReturnStatement(new JavaMethodInvocation("name", "trim"))
        );

    Annotation getMappingAnnotation = Annotation.name("org.springframework.web.bind.annotation.GetMapping");
    getApiMethod.annotate(getMappingAnnotation);
    getApiMethod.annotate(Annotation.name("org.springframework.web.bind.annotation.CrossOrigin"));

    restController.addMethodDeclaration(
        getApiMethod
    );

  }
}

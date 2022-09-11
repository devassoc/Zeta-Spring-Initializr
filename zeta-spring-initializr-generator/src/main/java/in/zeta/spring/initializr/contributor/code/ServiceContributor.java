package in.zeta.spring.initializr.contributor.code;

import in.zeta.spring.initializr.constant.AppConstant;
import io.spring.initializr.generator.language.Annotation;
import io.spring.initializr.generator.language.CompilationUnit;
import io.spring.initializr.generator.language.Parameter;
import io.spring.initializr.generator.language.SourceCode;
import io.spring.initializr.generator.language.SourceCodeWriter;
import io.spring.initializr.generator.language.TypeDeclaration;
import io.spring.initializr.generator.language.java.JavaMethodDeclaration;
import io.spring.initializr.generator.language.java.JavaMethodInvocation;
import io.spring.initializr.generator.language.java.JavaReturnStatement;
import io.spring.initializr.generator.language.java.JavaTypeDeclaration;
import io.spring.initializr.generator.project.ProjectDescription;
import io.spring.initializr.generator.project.contributor.ProjectContributor;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.nio.file.Path;
import java.util.function.Supplier;

public class ServiceContributor<
        T extends TypeDeclaration, C extends CompilationUnit<T>, S extends SourceCode<T, C>>
    implements ProjectContributor {

  private final ProjectDescription projectDescription;

  private final Supplier<S> sourceFactory;

  private final SourceCodeWriter<S> sourceWriter;

  ServiceContributor(
      ProjectDescription projectDescription,
      Supplier<S> sourceFactory,
      SourceCodeWriter<S> sourceWriter) {
    this.projectDescription = projectDescription;
    this.sourceFactory = sourceFactory;
    this.sourceWriter = sourceWriter;
  }

  @Override
  public void contribute(Path projectRoot) {

    S sourceCode = this.sourceFactory.get();
    String className =
        projectDescription.getApplicationName().replaceAll("Application", "") + "Service";
    ;
    C compilationUnit =
        sourceCode.createCompilationUnit(
            this.projectDescription.getPackageName() + ".service", className);
    T mainApplicationType = compilationUnit.createTypeDeclaration(className);

    createService(mainApplicationType);
    try {
      this.sourceWriter.writeTo(
          this.projectDescription
              .getBuildSystem()
              .getMainSource(projectRoot, this.projectDescription.getLanguage()),
          sourceCode);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  void createService(T javaTypeDeclaration) {
    JavaTypeDeclaration restController = (JavaTypeDeclaration) javaTypeDeclaration;
    restController.modifiers(Modifier.PUBLIC);
    restController.annotate(Annotation.name(AppConstant.SERVICE_ANNOTATION));

    JavaMethodDeclaration getApiMethod =
        JavaMethodDeclaration.method("getByName")
            .modifiers(Modifier.PUBLIC)
            .returning("String")
            .parameters(new Parameter(AppConstant.STRING_TYPE, "name"))
            .body(new JavaReturnStatement(new JavaMethodInvocation("name", "trim")));
    getApiMethod.annotate(Annotation.name(AppConstant.CLOCK_ANNOTATION));

    restController.addMethodDeclaration(getApiMethod);
  }
}

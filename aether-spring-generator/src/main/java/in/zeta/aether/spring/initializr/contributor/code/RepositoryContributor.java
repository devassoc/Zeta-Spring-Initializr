package in.zeta.aether.spring.initializr.contributor.code;

import static in.zeta.aether.spring.initializr.constant.AppConstant.REPOSITORY_ANNOTATION;
import static in.zeta.aether.spring.initializr.constant.AppConstant.STRING_TYPE;

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

public class RepositoryContributor<
        T extends TypeDeclaration, C extends CompilationUnit<T>, S extends SourceCode<T, C>>
    implements ProjectContributor {

  private final ProjectDescription projectDescription;

  private final Supplier<S> sourceFactory;

  private final SourceCodeWriter<S> sourceWriter;

  RepositoryContributor(
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
        projectDescription.getApplicationName().replaceAll("Application", "") + "Repository";
    C compilationUnit =
        sourceCode.createCompilationUnit(
            this.projectDescription.getPackageName() + ".repository", className);
    T mainApplicationType = compilationUnit.createTypeDeclaration(className);

    createRepository(mainApplicationType);
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

  void createRepository(T javaTypeDeclaration) {
    JavaTypeDeclaration restController = (JavaTypeDeclaration) javaTypeDeclaration;
    restController.modifiers(Modifier.PUBLIC);
    restController.annotate(Annotation.name(REPOSITORY_ANNOTATION));

    JavaMethodDeclaration getApiMethod =
        JavaMethodDeclaration.method("findByName")
            .modifiers(Modifier.PUBLIC)
            .returning("String")
            .parameters(new Parameter(STRING_TYPE, "name"))
            .body(new JavaReturnStatement(new JavaMethodInvocation("name", "trim")));

    restController.addMethodDeclaration(getApiMethod);
  }
}

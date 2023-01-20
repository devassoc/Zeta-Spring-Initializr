package in.zeta.spring.initializr.contributor.code;

import in.zeta.spring.initializr.constant.AppConstant;
import io.spring.initializr.generator.language.Annotation;
import io.spring.initializr.generator.language.CompilationUnit;
import io.spring.initializr.generator.language.Parameter;
import io.spring.initializr.generator.language.SourceCode;
import io.spring.initializr.generator.language.SourceCodeWriter;
import io.spring.initializr.generator.language.TypeDeclaration;
import io.spring.initializr.generator.language.java.*;
import io.spring.initializr.generator.project.ProjectDescription;
import io.spring.initializr.generator.project.contributor.ProjectContributor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.function.Supplier;

public class ControllerContributor<
        T extends TypeDeclaration, C extends CompilationUnit<T>, S extends SourceCode<T, C>>
    implements ProjectContributor {

  private final ProjectDescription projectDescription;

  private final Supplier<S> sourceFactory;

  private final SourceCodeWriter<S> sourceWriter;

  ControllerContributor(
      ProjectDescription projectDescription,
      Supplier<S> sourceFactory,
      SourceCodeWriter<S> sourceWriter) {
    this.projectDescription = projectDescription;
    this.sourceFactory = sourceFactory;
    this.sourceWriter = sourceWriter;
  }

  @Override
  public void contribute(Path projectRoot) throws FileNotFoundException {


    S sourceCode = this.sourceFactory.get();
    String className =
        projectDescription.getApplicationName().replaceAll("Application", "") + "Controller";
    C compilationUnit =
        sourceCode.createCompilationUnit(
            this.projectDescription.getPackageName() + ".controller", className);
    T mainApplicationType = compilationUnit.createTypeDeclaration(className);

    createRestController(mainApplicationType);

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

  void createRestController(T javaTypeDeclaration) {
    JavaTypeDeclaration restController = (JavaTypeDeclaration) javaTypeDeclaration;
    restController.modifiers(Modifier.PUBLIC);
    restController.annotate(Annotation.name(AppConstant.REST_CONTROLLER_ANNOTATION));

    Annotation requestMappingAnnotation =
        Annotation.name(
            AppConstant.REQUEST_MAPPING_ANNOTATION,
            (builder) -> builder.attribute("value", String.class, "api/v1"));
    restController.annotate(requestMappingAnnotation);

  //  Annotation val1=Annotation.name(AppConstant.SPRING_VALUE,(builder)->builder.attribute(AppConstant.zone_list, List.class));

    Annotation val1=Annotation.name(AppConstant.SPRING_VALUE,(builder)->builder.attribute(AppConstant.zone_list, List.class));
    Annotation val2=Annotation.name(AppConstant.SPRING_VALUE,(builder)->builder.attribute(AppConstant.jenkins_list, List.class));
    Annotation val3=Annotation.name(AppConstant.SPRING_VALUE,(builder)->builder.attribute(AppConstant.bitbucketid, List.class));
    Annotation val4=Annotation.name(AppConstant.SPRING_VALUE,(builder)->builder.attribute(AppConstant.jenkins_url_list, List.class));
    Annotation val5=Annotation.name(AppConstant.SPRING_VALUE,(builder)->builder.attribute(AppConstant.containerimage, List.class));
    Annotation val6=Annotation.name(AppConstant.SPRING_VALUE,(builder)->builder.attribute(AppConstant.credentiallist, List.class));
    Annotation val7=Annotation.name(AppConstant.SPRING_VALUE,(builder)->builder.attribute(AppConstant.tenantId, String.class));
    Annotation val8=Annotation.name(AppConstant.SPRING_VALUE,(builder)->builder.attribute(AppConstant.tenantname, String.class));
    Annotation val9=Annotation.name(AppConstant.SPRING_VALUE,(builder)->builder.attribute(AppConstant.modulename, String.class));
    Annotation val10=Annotation.name(AppConstant.SPRING_VALUE,(builder)->builder.attribute(AppConstant.clusterid, String.class));

    JavaFieldDeclaration f1= JavaFieldDeclaration.field("zonelist").returning("List<String>");
    f1.annotate(val1);
    JavaFieldDeclaration f12= JavaFieldDeclaration.field("jenkinslist").returning("List<String>");
    f12.annotate(val2);
    JavaFieldDeclaration f13= JavaFieldDeclaration.field("bitbucketid").returning("List<String>");
    f13.annotate(val3);
    JavaFieldDeclaration f14= JavaFieldDeclaration.field("jenkinsurl").returning("List<String>");
    f14.annotate(val4);
    JavaFieldDeclaration f15= JavaFieldDeclaration.field("containerlist").returning("List<String>");
    f15.annotate(val5);
    JavaFieldDeclaration f16= JavaFieldDeclaration.field("credentials").returning("List<String>");
    f16.annotate(val6);
    JavaFieldDeclaration f17= JavaFieldDeclaration.field("tenantid").returning("String");
    f17.annotate(val7);
    JavaFieldDeclaration f18= JavaFieldDeclaration.field("tenantname").returning("String");
    f18.annotate(val8);
    JavaFieldDeclaration f19= JavaFieldDeclaration.field("modulename").returning("String");
    f19.annotate(val9);
    JavaFieldDeclaration f10= JavaFieldDeclaration.field("clusterid").returning("String");
    f10.annotate(val10);
    JavaFieldDeclaration hm= JavaFieldDeclaration.field("mapzone").returning("HashMap<String,String[]>");
    JavaFieldDeclaration hm1= JavaFieldDeclaration.field("maptenant").returning("HashMap<String,String>");

    JavaFieldDeclaration hm2= JavaFieldDeclaration.field("mapcluster").returning("HashMap<String,String>");

    Annotation configa=Annotation.name(AppConstant.GET_MAPPING_ANNOTATION,builder -> builder.attribute("value",String.class,"/elnchos/config/{zone}/{tenant}/{cluster}"));
    JavaMethodDeclaration getconfig=JavaMethodDeclaration.method("getconfig").modifiers(Modifier.PUBLIC)
            .returning("ResponseEntity<String>")
            .parameters(new Parameter[]{new Parameter(AppConstant.STRING_TYPE,"zone"),new Parameter(AppConstant.STRING_TYPE,"tenant"),new Parameter(AppConstant.STRING_TYPE,"cluster")})
            .body(new JavaReturnStatement(new JavaMethodInvocation("mapzone","PUT_IN_MAP_AND_DISPLAY_VALUES_OF_CONFIGS")))
            ;
    getconfig.annotate(configa);

    JavaMethodDeclaration getApiMethod =
        JavaMethodDeclaration.method("getName")
            .modifiers(Modifier.PUBLIC)
            .returning("String")
            .parameters(new Parameter(AppConstant.STRING_TYPE, "name"))
            .body(new JavaReturnStatement(new JavaMethodInvocation("name", "trim")));

    Annotation getMappingAnnotation = Annotation.name(AppConstant.GET_MAPPING_ANNOTATION);
    getApiMethod.annotate(getMappingAnnotation);
    getApiMethod.annotate(Annotation.name(AppConstant.CROSS_ORIGIN_ANNOTATION));

    restController.addFieldDeclaration(f1);
    restController.addFieldDeclaration(f12);
    restController.addFieldDeclaration(f13);
    restController.addFieldDeclaration(f14);
    restController.addFieldDeclaration(f15);
    restController.addFieldDeclaration(f16);
    restController.addFieldDeclaration(f17);
    restController.addFieldDeclaration(f18);
    restController.addFieldDeclaration(f19);
    restController.addFieldDeclaration(f10);
    restController.addFieldDeclaration(hm);
    restController.addFieldDeclaration(hm1);
    restController.addFieldDeclaration(hm2);

    restController.addMethodDeclaration(getconfig);
    restController.addMethodDeclaration(getApiMethod);
  }
}

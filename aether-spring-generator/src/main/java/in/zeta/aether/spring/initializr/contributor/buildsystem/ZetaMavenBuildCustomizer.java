package in.zeta.aether.spring.initializr.contributor.buildsystem;

import static in.zeta.aether.spring.initializr.constant.AppConstant.ZETA_MAVEN_COMMONS_ARTIFACT_ID;
import static in.zeta.aether.spring.initializr.constant.AppConstant.ZETA_MAVEN_COMMONS_GROUP_ID;
import static in.zeta.aether.spring.initializr.constant.AppConstant.ZETA_MAVEN_COMMONS_VERSION;
import static in.zeta.aether.spring.initializr.constant.AppConstant.ZETA_MAVEN_H2_ARTIFACT_ID;
import static in.zeta.aether.spring.initializr.constant.AppConstant.ZETA_MAVEN_H2_GROUP_ID;
import static in.zeta.aether.spring.initializr.constant.AppConstant.ZETA_MAVEN_H2_VERSION;
import static in.zeta.aether.spring.initializr.constant.AppConstant.ZETA_MAVEN_PARENT_ARTIFACT_ID;
import static in.zeta.aether.spring.initializr.constant.AppConstant.ZETA_MAVEN_PARENT_GROUP_ID;
import static in.zeta.aether.spring.initializr.constant.AppConstant.ZETA_MAVEN_PARENT_VERSION;

import io.spring.initializr.generator.buildsystem.Dependency;
import io.spring.initializr.generator.buildsystem.maven.MavenBuild;
import io.spring.initializr.generator.spring.build.BuildCustomizer;
import io.spring.initializr.generator.version.VersionReference;
import io.spring.initializr.metadata.InitializrConfiguration.Env.Maven;
import io.spring.initializr.metadata.support.MetadataBuildItemMapper;

/**
 * The default {@link Maven} {@link BuildCustomizer}.
 *
 * @author Stephane Nicoll
 */
public class ZetaMavenBuildCustomizer implements BuildCustomizer<MavenBuild> {

  public ZetaMavenBuildCustomizer() {}

  @Override
  public void customize(MavenBuild build) {
    build
        .settings()
        .parent(
            ZETA_MAVEN_PARENT_GROUP_ID, ZETA_MAVEN_PARENT_ARTIFACT_ID, ZETA_MAVEN_PARENT_VERSION);

    // Exclude Logging Library So that App can use Olmypus Library
    Dependency.Exclusion logBackExclusion =
        new Dependency.Exclusion("ch.qos.logback", "logback-classic");
    Dependency.Exclusion springBootStarterLogging =
        new Dependency.Exclusion("org.springframework.boot", "spring-boot-starter-logging");

    // Adding Zeta Spring Boot Commons
    io.spring.initializr.metadata.Dependency springZetaCommonDependency =
        io.spring.initializr.metadata.Dependency.create(
            ZETA_MAVEN_COMMONS_GROUP_ID,
            ZETA_MAVEN_COMMONS_ARTIFACT_ID,
            ZETA_MAVEN_COMMONS_VERSION,
            "compile");
    springZetaCommonDependency.setId(ZETA_MAVEN_COMMONS_ARTIFACT_ID);
    springZetaCommonDependency.setStarter(true);
    build
        .dependencies()
        .add(
            ZETA_MAVEN_COMMONS_ARTIFACT_ID,
            MetadataBuildItemMapper.toDependency(springZetaCommonDependency));

    build.dependencies().get(ZETA_MAVEN_COMMONS_ARTIFACT_ID).getExclusions().add(logBackExclusion);

    build
        .dependencies()
        .get(ZETA_MAVEN_COMMONS_ARTIFACT_ID)
        .getExclusions()
        .add(springBootStarterLogging);

    // Exclude spring boot logging library from spring boot starter test
    build.dependencies().get("test").getExclusions().add(springBootStarterLogging);

    // Adding h2 dependency
    Dependency.Builder h2Dependency =
        new Dependency.Builder(ZETA_MAVEN_H2_GROUP_ID, ZETA_MAVEN_H2_ARTIFACT_ID) {};
    Dependency dependency2 =
        h2Dependency.version(VersionReference.ofValue(ZETA_MAVEN_H2_VERSION)).build();
    build.dependencies().add(ZETA_MAVEN_H2_ARTIFACT_ID, dependency2);
  }

  @Override
  public int getOrder() {
    return Integer.MAX_VALUE;
  }
}

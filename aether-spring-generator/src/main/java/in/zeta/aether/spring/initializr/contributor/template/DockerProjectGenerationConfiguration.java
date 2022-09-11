package in.zeta.aether.spring.initializr.contributor.template;

import io.spring.initializr.generator.buildsystem.Build;
import io.spring.initializr.generator.project.ProjectGenerationConfiguration;
import io.spring.initializr.generator.spring.build.BuildCustomizer;
import org.springframework.context.annotation.Bean;

@ProjectGenerationConfiguration
public class DockerProjectGenerationConfiguration {

  @Bean
  public DockerContributor dockerContributor() {
    return new DockerContributor();
  }

  @Bean
  public BuildCustomizer<Build> sampleBuildCustomizer() {
    return (build) -> build.properties().version("example.version", "1.0.0.RELEASE");
  }
}

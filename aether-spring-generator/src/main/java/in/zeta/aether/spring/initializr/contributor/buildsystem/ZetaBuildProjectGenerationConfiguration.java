package in.zeta.aether.spring.initializr.contributor.buildsystem;

import io.spring.initializr.generator.buildsystem.maven.MavenBuildSystem;
import io.spring.initializr.generator.condition.ConditionalOnBuildSystem;
import io.spring.initializr.generator.project.ProjectGenerationConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * Project generation configuration for projects using any build system.
 *
 * @author Andy Wilkinson
 * @author Jean-Baptiste Nizet
 */
@ProjectGenerationConfiguration
@ConditionalOnBuildSystem(MavenBuildSystem.ID)
public class ZetaBuildProjectGenerationConfiguration {

	@Bean
	public ZetaMavenBuildCustomizer zetaMavenBuildCustomizer() {
		return new ZetaMavenBuildCustomizer();
	}


}

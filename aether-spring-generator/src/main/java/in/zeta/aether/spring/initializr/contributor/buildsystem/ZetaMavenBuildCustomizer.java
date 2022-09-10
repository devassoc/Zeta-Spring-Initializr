package in.zeta.aether.spring.initializr.contributor.buildsystem;

import static in.zeta.aether.spring.initializr.constant.AppConstant.ZETA_MAVEN_COMMONS_ARTIFACT_ID;
import static in.zeta.aether.spring.initializr.constant.AppConstant.ZETA_MAVEN_COMMONS_GROUP_ID;
import static in.zeta.aether.spring.initializr.constant.AppConstant.ZETA_MAVEN_COMMONS_VERSION;
import static in.zeta.aether.spring.initializr.constant.AppConstant.ZETA_MAVEN_PARENT_ARTIFACT_ID;
import static in.zeta.aether.spring.initializr.constant.AppConstant.ZETA_MAVEN_PARENT_GROUP_ID;
import static in.zeta.aether.spring.initializr.constant.AppConstant.ZETA_MAVEN_PARENT_VERSION;

import io.spring.initializr.generator.buildsystem.Dependency;
import io.spring.initializr.generator.buildsystem.maven.MavenBuild;
import io.spring.initializr.generator.spring.build.BuildCustomizer;
import io.spring.initializr.generator.version.VersionReference;
import io.spring.initializr.metadata.InitializrConfiguration.Env.Maven;

/**
 * The default {@link Maven} {@link BuildCustomizer}.
 *
 * @author Stephane Nicoll
 */
public class ZetaMavenBuildCustomizer implements BuildCustomizer<MavenBuild> {



	public ZetaMavenBuildCustomizer() {
	}

	@Override
	public void customize(MavenBuild build) {
		build.settings().parent(ZETA_MAVEN_PARENT_GROUP_ID, ZETA_MAVEN_PARENT_ARTIFACT_ID, ZETA_MAVEN_PARENT_VERSION);

		Dependency.Builder zetaCommonsDependency = new Dependency.Builder(ZETA_MAVEN_COMMONS_GROUP_ID, ZETA_MAVEN_COMMONS_ARTIFACT_ID){};
		Dependency dependency = zetaCommonsDependency.version(VersionReference.ofValue(ZETA_MAVEN_COMMONS_VERSION)).build();
		build.dependencies().add(ZETA_MAVEN_COMMONS_ARTIFACT_ID, dependency);
	}

	@Override
	public int getOrder() {
		return Integer.MAX_VALUE;
	}

}

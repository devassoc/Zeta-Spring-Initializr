/*
 * Copyright 2012-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package in.zeta.aether.spring.initializr.contributor;

import io.spring.initializr.generator.buildsystem.BillOfMaterials;
import io.spring.initializr.generator.buildsystem.Dependency;
import io.spring.initializr.generator.buildsystem.Dependency.Builder;
import io.spring.initializr.generator.buildsystem.DependencyScope;
import io.spring.initializr.generator.buildsystem.maven.MavenBuild;
import io.spring.initializr.generator.project.ProjectDescription;
import io.spring.initializr.generator.spring.build.BuildCustomizer;
import io.spring.initializr.generator.version.VersionProperty;
import io.spring.initializr.generator.version.VersionReference;
import io.spring.initializr.metadata.InitializrConfiguration.Env.Maven;
import io.spring.initializr.metadata.InitializrConfiguration.Env.Maven.ParentPom;
import io.spring.initializr.metadata.InitializrMetadata;
import io.spring.initializr.metadata.support.MetadataBuildItemMapper;

/**
 * The default {@link Maven} {@link BuildCustomizer}.
 *
 * @author Stephane Nicoll
 */
public class ZetaMavenBuildCustomizer implements BuildCustomizer<MavenBuild> {

	private static final String ZETA_MAVEN_PARENT_GROUP_ID = "in.zeta";
	private static final String ZETA_MAVEN_PARENT_ARTIFACT_ID = "zeta-spring-boot-pom";
	private static final String ZETA_MAVEN_PARENT_VERSION = "1.1.2";

	private static final String ZETA_MAVEN_COMMONS_GROUP_ID = "in.zeta";
	private static final String ZETA_MAVEN_COMMONS_ARTIFACT_ID = "spring-boot-commons";
	private static final String ZETA_MAVEN_COMMONS_VERSION = "2.7.3";

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

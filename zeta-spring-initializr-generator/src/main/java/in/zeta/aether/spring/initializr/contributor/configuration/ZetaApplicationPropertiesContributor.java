/*
 * Copyright 2012-2019 the original author or authors.
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

package in.zeta.aether.spring.initializr.contributor.configuration;

import io.spring.initializr.generator.project.contributor.SingleResourceProjectContributor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import org.springframework.util.FileCopyUtils;

/**
 * A {@link SingleResourceProjectContributor} that contributes a {@code application.properties} file
 * to a project.
 *
 * @author Stephane Nicoll
 */
public class ZetaApplicationPropertiesContributor extends SingleResourceProjectContributor {

  public ZetaApplicationPropertiesContributor() {
    this("classpath:configuration/application.properties");
  }

  public ZetaApplicationPropertiesContributor(String resourcePattern) {
    super("src/main/resources/application.properties", resourcePattern);
  }

  @Override
  public void contribute(Path projectRoot) throws IOException {
    copyFile(projectRoot, "src/main/resources/application.properties", "zeta-app-properties.txt");
  }

  @Override
  public int getOrder() {
    return Integer.MAX_VALUE;
  }

  private void copyFile(Path projectRoot, String destFileName, String sourceFileName) {
    ClassLoader classLoader = getClass().getClassLoader();
    Path sourcePath = Paths.get(classLoader.getResource(sourceFileName).getPath());

    try {
      InputStream sourceStream = Files.newInputStream(sourcePath);
      OutputStream destinationStream =
          Files.newOutputStream(projectRoot.resolve(destFileName), StandardOpenOption.APPEND);
      FileCopyUtils.copy(sourceStream, destinationStream);
    } catch (Exception e) {
      System.out.println("Exception occurred:" + e);
    }
  }
}

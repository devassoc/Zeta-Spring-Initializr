package in.zeta.spring.initializr.contributor.template;

import io.spring.initializr.generator.project.contributor.ProjectContributor;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class DockerContributor implements ProjectContributor {

  public void contribute(Path projectRoot) {

    copyFile(projectRoot, "Dockerfile", "Dockerfile");

    copyFile(projectRoot, "Jenkinsfile", "Jenkinsfile");

    copyFile(projectRoot, "log4Olympus2.xml", "log4Olympus2.xml");
  }

  private void copyFile(Path projectRoot, String destFileName, String sourceFilePath) {

    ClassLoader classLoader = getClass().getClassLoader();
    File source = new File(classLoader.getResource(sourceFilePath).getFile());
    File dest = null;

    try {
      dest = Files.createFile(projectRoot.resolve(destFileName)).toFile();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    try (InputStream is = new FileInputStream(source);
        OutputStream os = new FileOutputStream(dest)) {
      byte[] buffer = new byte[1024];
      int length;
      while ((length = is.read(buffer)) > 0) {
        os.write(buffer, 0, length);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}

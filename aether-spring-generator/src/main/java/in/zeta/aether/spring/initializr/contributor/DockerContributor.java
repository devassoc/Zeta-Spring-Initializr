package in.zeta.aether.spring.initializr.contributor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;

import io.spring.initializr.generator.project.contributor.ProjectContributor;

public class DockerContributor implements ProjectContributor {

	public void contribute(Path projectRoot){
		//TODO: Remove this hard coding of file name
		copyFile(
				projectRoot,
				"Dockerfile",
				"/Users/hirenodedara/Projects/initializr/initializr-generator-spring/src/main/resources/Dockerfile"
				);

		copyFile(
				projectRoot,
				"entryPoint.sh",
				"/Users/hirenodedara/Projects/initializr/initializr-generator-spring/src/main/resources/entryPoint.sh"
				);

		copyFile(
				projectRoot,
				"Jenkinsfile",
				"/Users/hirenodedara/Projects/initializr/initializr-generator-spring/src/main/resources/Jenkinsfile"
				);

	}

	private void copyFile(Path projectRoot, String destFileName, String sourceFilePath){

		File source = new File(sourceFilePath);
		File dest = null;

		//Create a file
		try {
			dest = Files.createFile(projectRoot.resolve(destFileName)).toFile();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		try(InputStream is = new FileInputStream(source);OutputStream os = new FileOutputStream(dest)) {
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

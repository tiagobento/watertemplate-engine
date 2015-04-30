package org.watertemplate.i18n;

import org.watertemplate.i18n.developer.DirectoryWatcher;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

@Mojo(name = "generate", defaultPhase = LifecyclePhase.PROCESS_RESOURCES)
public class GenerationMojo extends AbstractMojo {

    @Parameter(name = "baseDir", required = true)
    private String baseDir;

    @Parameter(name = "destinationDir", required = true)
    private String destinationDir;

    @Parameter(name = "bundlesDir", required = true)
    private String bundlesDir;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            Internationalization internationalization = new Internationalization(baseDir, destinationDir, bundlesDir);
            internationalization.parse();

            if (System.getProperty("dev-mode") != null) {
                DirectoryWatcher directoryWatcher = new DirectoryWatcher(baseDir, bundlesDir);
                directoryWatcher.watchRunning(internationalization::parse);
            }

        } catch (Exception e) {
            throw new MojoExecutionException("Error in i18n plugin.", e);
        }
    }
}

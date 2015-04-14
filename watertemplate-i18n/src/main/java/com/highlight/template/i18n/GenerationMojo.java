package com.highlight.template.i18n;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

@Mojo(name = "generate", defaultPhase = LifecyclePhase.PROCESS_RESOURCES)
public class GenerationMojo extends AbstractMojo {

    @Parameter(name = "baseDir")
    private String baseDir;

    @Parameter(name = "destinationDir")
    private String destinationDir;

    @Parameter(name = "bundlesDir")
    private String bundlesDir;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            if (parametersAreValid()) new Internationalization(baseDir, destinationDir, bundlesDir).parse();
            else throw new MojoFailureException("Please configure baseDir, destinationDir and bundlesDir.");
        } catch (Exception e) {
            throw new MojoExecutionException("Error in i18n plugin.", e);
        }
    }

    public boolean parametersAreValid() {
        return baseDir != null && destinationDir != null && bundlesDir != null;
    }
}

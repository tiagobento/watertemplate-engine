package com.highlight.template.i18n;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * @goal generate
 * @phase process-resources
 */
public class GenerationMojo extends AbstractMojo {

    /* //////////////////////////////////////////////////////////////////////////////////// */
    /* Do not remove these Javadocs. Maven uses them to inject values and detect properties *
    /* //////////////////////////////////////////////////////////////////////////////////// */

    /**
     * @parameter
     */
    private String baseDir;

    /**
     * @parameter
     */
    private String destinationDir;

    /**
     * @parameter
     */
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

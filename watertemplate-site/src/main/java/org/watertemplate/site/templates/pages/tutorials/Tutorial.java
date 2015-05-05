package org.watertemplate.site.templates.pages.tutorials;

import org.watertemplate.Template;
import org.watertemplate.TemplateMap;

abstract class Tutorial extends Template {

    private final String stepsDirectoryName;
    private final int numberOfSteps;

    Tutorial(final String stepsDirectoryName, final int numberOfSteps) {
        this.stepsDirectoryName = stepsDirectoryName;
        this.numberOfSteps = numberOfSteps;
    }

    @Override
    protected Template getMasterTemplate() {
        return new Master(getHeader());
    }

    @Override
    protected void addSubTemplates(TemplateMap.SubTemplates subTemplates) {
        addTutorialSteps(subTemplates);
    }

    private void addTutorialSteps(TemplateMap.SubTemplates subTemplates) {
        for (int i = 1; i <= numberOfSteps; i++) {
            final String filePath = "pages/tutorials/" + stepsDirectoryName + "/" + stepsDirectoryName + "-step" + i + ".html";
            subTemplates.add("tutorial_step" + i, new Template() {
                @Override
                protected String getFilePath() {
                    return filePath;
                }
            });
        }
    }

    public abstract Master.Header getHeader();
}

package org.watertemplate;

public class Fixture {
    public static class MasterTemplate extends Template {
        protected String getFilePath() {
            return "_master_template.st";
        }
    }

    public static class SubTemplate extends Template {
        protected String getFilePath() {
            return "_sub_template.st";
        }
    }

    @org.watertemplate.StaticTemplate
    public static class StaticTemplate extends Fixture.SubTemplate {
    }
}


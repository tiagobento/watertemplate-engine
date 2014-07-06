package org.watertemplate;

public class Fixture {
    public static class MasterTemplate extends Template {
        protected String getFilePath() {
            return "masterTemplate.html";
        }
    }

    public static class SubTemplate extends Template {
        protected String getFilePath() {
            return "subTemplate.html";
        }
    }
}


package org.watertemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CommandTemplatesFixture {

    public static class TemplateWithFor extends Template {
        TemplateWithFor(Integer... integers) {
            List<String> map = Arrays.asList(integers).stream()
                    .map(Object::toString)
                    .collect(Collectors.toList());

            addCollection("items", map, (a, b) -> {});
        }

        @Override
        protected String getFilePath() {
            return "commands/templateWithFor.html";
        }
    }

    public static class TemplateWithIf extends Template {

        public TemplateWithIf(final Boolean condition) {
            add("condition", condition);
        }

        @Override
        protected String getFilePath() {
            return "commands/templateWithIf.html";
        }
    }
}

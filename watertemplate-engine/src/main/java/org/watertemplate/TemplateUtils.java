package org.watertemplate;

import java.util.function.Supplier;
import java.util.stream.Stream;

public class TemplateUtils {
    public static String buildString(final Stream<Supplier<String>> supplierStream) {
        return supplierStream
                .map(Supplier::get)
                .reduce(String::concat)
                .orElse("");
    }
}

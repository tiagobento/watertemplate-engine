package org.watertemplate;

import java.util.function.Supplier;
import java.util.stream.Stream;

public class TemplateUtils {
    public static String buildString(final Stream<Supplier<String>> supplierStream) {
        return supplierStream
                .parallel()
                .reduce("", (s, supplier) -> s + supplier.get(), String::concat);
    }
}

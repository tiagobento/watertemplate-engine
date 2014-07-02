package org.watertemplate.parser.consumer;

import org.watertemplate.parser.Parser;

import java.util.Map;
import java.util.function.Consumer;

public class WaterConsumer {

    private final StringBuilder resultStringBuilder;
    private final Map<String, Object> arguments;

    private Consumer<Character> currentConsumer;
    private StringBuilder currentArgumentKeyBuilder;

    public WaterConsumer(Map<String, Object> args) {
        arguments = args;
        resultStringBuilder = new StringBuilder();
        resetCurrentCommandAndConsumer();
    }

    public void accept(final Character character) {
        currentConsumer.accept(character);
    }

    //

    private void commandConsumer(final Character character) {
        if (character == Parser.DELIMITER) {
            putArgumentValue();
            resetCurrentCommandAndConsumer();
        } else {
            currentArgumentKeyBuilder.append(character);
        }
    }

    private void defaultConsumer(final Character character) {
        if (character == Parser.DELIMITER) {
            currentConsumer = this::commandConsumer;
        } else {
            resultStringBuilder.append(character);
        }
    }

    //

    private void putArgumentValue() {
        final String key = currentArgumentKeyBuilder.toString();

        if (arguments.containsKey(key)) {
            resultStringBuilder.append(arguments.get(key).toString());
        } else {
            resultStringBuilder.append("[MISSING: ").append(key).append("]");
        }
    }

    private void resetCurrentCommandAndConsumer() {
        currentArgumentKeyBuilder = new StringBuilder();
        currentConsumer = this::defaultConsumer;
    }

    public String getResultString() {
        return resultStringBuilder.toString();
    }
}

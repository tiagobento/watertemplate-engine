package org.watertemplate.interpreter.lexer;

import org.watertemplate.interpreter.Interpreter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WaterLexer {

    private final Map<String, Object> arguments;

    private String currentToken;
    private List<String> tokens;
    private String resultString;


    public WaterLexer(final Map<String, Object> arguments) {
        this.arguments = arguments;
        this.tokens = new ArrayList<>();
        this.currentToken = "";
    }

    public void lex(final Character character) {
        switch (character) {
            case '\t':
            case '\r':
            case '\n':
                break;
            case Interpreter.GENERAL_DELIMITER:
            case Interpreter.COMMAND_DELIMITER:

                if (!currentToken.trim().isEmpty()) {
                    addToken();
                }

                tokens.add(character.toString());
                currentToken = "";
                break;
            case '\0':
                addToken();
                break;
            default:
                currentToken += character;
        }

        resultString += character;
    }

    private boolean addToken() {
        return tokens.add(currentToken.trim());
    }

    public String[] getTokens() {
        return tokens.toArray(new String[tokens.size()]);
    }

    public String getResultString() {
        return resultString;
    }








    //////







    private final static class WaterCommands {

        private WaterCommands() {
        }

        public static WaterCommand newCommand(final String command) {

            if (command.startsWith("~if ")) {
                System.out.println("if");
                return new WaterIfCommand();
            }

            if (command.startsWith("~for ")) {
                System.out.println("for");
                return new WaterForCommand();
            }

            if (command.startsWith("~")) {
                System.out.println("attribute");
                return new WaterAttributeCommand();
            }

            System.out.println("html");
            return new WaterHtmlCommand();
        }

        private static class WaterIfCommand extends WaterCommand {
        }

        private static class WaterForCommand extends WaterCommand {
        }

        private static class WaterAttributeCommand extends WaterCommand {
        }

        private static class WaterHtmlCommand extends WaterCommand {
        }

        public static abstract class WaterCommand {
            private WaterCommand left;
            private WaterCommand right;
            private WaterCommand parent;

            void addLeft(final WaterCommand command) {
                left = command;
            }

            void addRight(final WaterCommand command) {
                right = command;
            }
        }

    }
}

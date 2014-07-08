package org.watertemplate.interpreter.lexer;

import java.util.List;
import java.util.Stack;
import java.util.function.Consumer;

public class WaterLexer {
    private final Stack<Character> stack = new Stack<>();
    private final Tokens tokens = new Tokens();

    private Consumer<Character> consumer = this::ordinaryText;

    public void lex(final Character character) {
        consumer.accept(character);
    }

    private void ordinaryText(final Character character) {
        switch (character) {
            case '~': // start of command
                tokens.accept(Token.Clazz.TEXT);
                stack.push(character);
                consumer = this::command;
                break;
            case ':': // start of end of command
                tokens.accept(Token.Clazz.TEXT);
                stack.pop();
                consumer = this::command;
                break;
            case '\0': // end of input
                testStack();
                tokens.accept(Token.Clazz.TEXT);
                break;
            default:
                tokens.add(character);
                break;
        }
    }

    private void command(final Character character) {
        switch (character) {
            case ':':  // end of command
                tokens.accept();
                stack.push(character);
                consumer = this::ordinaryText;
                break;
            case '~': // end of property evaluation
                tokens.accept();
                stack.pop();
                consumer = this::ordinaryText;
                break;
            case ' ': // separator
                tokens.accept();
                break;
            case '.': // accessor
                tokens.accept();
                tokens.add(character);
                tokens.accept(Token.Clazz.ACCESSOR);
                break;
            case '\0':
                testStack();
                break;
            default:
                tokens.add(character);
                break;
        }
    }

    private void testStack() {
        if (!stack.empty()) {
            throw new RuntimeException("Unclosed something");
        }
    }

    public List<String> getTokens() {
        return tokens.all();
    }

    public String getResultString() {
        return "";
    }
}

package org.watertemplate.interpreter.lexer;

import java.util.List;
import java.util.function.Consumer;

public class WaterLexer {
    private final Tokens tokens = new Tokens();

    private Consumer<Character> readMode = this::ordinaryText;

    public void lex(final Character character) {
        readMode.accept(character);
    }


    /* default */
    private void ordinaryText(final Character character) {
        switch (character) {
            case '~': // start of command
                tokens.accept(TokenClass.TEXT);
                readMode = this::command;
                break;
            case ':': // start of end of command
                tokens.accept(TokenClass.TEXT);
                readMode = this::closingCommand;
                break;
            case '\0': // end of input
                tokens.accept(TokenClass.TEXT);
                break;
            default:
                tokens.add(character);
                break;
        }
    }

    private void command(final Character character) {
        switch (character) {
            case ':':  // end of command
                tokens.accept(TokenClass.ID);
                readMode = this::ordinaryText;
                break;
            case '~': // end of property evaluation
                tokens.accept(TokenClass.ID);
                readMode = this::ordinaryText;
                break;
            case '\t':
            case '\n':
            case ' ': // separators
                tokens.accept();
                readMode = this::whiteSpace;
                break;
            case '.': // accessor
                tokens.accept(TokenClass.ID);
                tokens.add(character);
                tokens.accept(TokenClass.ACCESSOR);
                break;
            case '\0':
                throw new RuntimeException("Unfinished command.");
            default:
                tokens.add(character);
                break;
        }
    }

    private void closingCommand(final Character character) {
        switch (character) {
            case ':':  // else
                tokens.accept(TokenClass.KEYWORD);
                readMode = this::ordinaryText;
                break;
            case '~': // end of block
                tokens.accept(TokenClass.END_OF_BLOCK);
                readMode = this::ordinaryText;
                break;
            default:
                tokens.add(character);
                break;
        }

    }

    private void whiteSpace(final Character character) {
        switch (character) {
            case '\t':
            case '\n':
            case ' ':
                break;
            default:
                command(character);
                readMode = this::command;
                break;
        }
    }

    List<String> getTokenValues() {
        return tokens.values();
    }

    public List<Token> getTokens() {
        return tokens.all();
    }

    public String getResultString() {
        return "";
    }
}

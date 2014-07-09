package org.watertemplate.interpreter.lexer;

import org.watertemplate.interpreter.lexer.exception.IncompleteCommandException;

import java.util.List;
import java.util.function.Consumer;

public class WaterLexer {

    private final Tokens tokens = new Tokens();
    private Consumer<Character> readMode = this::ordinaryText;

    int i = 0;
    int j = 0;

    public void lex(final Character character) {
        switch (character) {
            case '\n':
                i++;
                j = 0;
            default:
                readMode.accept(character);
                j++;
        }
    }

    /* default */
    private void ordinaryText(final Character character) {
        switch (character) {
            case '~': // start of command
                tokens.accept(TokenClass.TEXT, i, j);
                readMode = this::command;
                break;
            case ':': // start of end of command
                tokens.accept(TokenClass.TEXT, i, j);
                readMode = this::closingCommand;
                break;
            case '\0': // end of input
                tokens.accept(TokenClass.TEXT, i, j);
                break;
            default:
                tokens.add(character);
                break;
        }
    }

    private void command(final Character character) {
        switch (character) {
            case ':':  // end of command
                tokens.accept(TokenClass.ID, i, j);
                readMode = this::ordinaryText;
                break;
            case '~': // end of property evaluation
                tokens.accept(TokenClass.ID, i, j);
                readMode = this::ordinaryText;
                break;
            case '\t':
            case '\n':
            case ' ': // separators
                tokens.accept(i, j);
                readMode = this::whiteSpaceInsideCommands;
                break;
            case '.': // accessor
                tokens.accept(TokenClass.ID, i, j);
                tokens.add(character);
                tokens.accept(TokenClass.ACCESSOR, i, j);
                break;
            case '\0':
                throw new IncompleteCommandException(i, j);
            default:
                tokens.add(character);
                break;
        }
    }

    private void closingCommand(final Character character) {
        switch (character) {
            case ':':  // else
                tokens.accept(TokenClass.KEYWORD, i, j);
                readMode = this::ordinaryText;
                break;
            case '~': // end of block
                tokens.add('e').add('n').add('d');
                tokens.accept(TokenClass.KEYWORD, i, j);
                readMode = this::ordinaryText;
                break;
            default:
                tokens.add(character);
                break;
        }
    }

    private void whiteSpaceInsideCommands(final Character character) {
        switch (character) {
            case '\t':
            case '\n':
            case ' ':
                break;
            case ':':
                readMode = this::ordinaryText;
                break;
            default:
                command(character);
                readMode = this::command;
                break;
        }
    }

    public List<Token> getTokens() {
        return tokens.all();
    }
}

package org.watertemplate.interpreter.lexer;

import org.watertemplate.interpreter.lexer.exception.IncompleteTokenException;

import java.util.List;
import java.util.function.Consumer;

public class WaterLexer {

    private final Tokens tokens = new Tokens();
    private Consumer<Character> readMode = this::ordinaryText;

    private int lineNumber = 0;
    private int columnNumber = 0;

    public void accept(final Character character) {
        switch (character) {
            case '\n':
                lineNumber++;
                columnNumber = 0;
            default:
                readMode.accept(character);
                columnNumber++;
        }
    }

    /* default */
    private void ordinaryText(final Character character) {
        switch (character) {
            case Symbol.Char.ENVIRONMENT_CHANGER:
                tokens.accept(TokenClass.TEXT);
                readMode = this::commandOrPropertyEvaluation;
                break;
            case Symbol.Char.BLOCK_CLOSER:
                tokens.accept(TokenClass.TEXT);
                readMode = this::endOfBlock;
                break;
            case '\0':
                tokens.accept(TokenClass.TEXT);
                break;
            default:
                tokens.add(character);
                break;
        }
    }

    private void commandOrPropertyEvaluation(final Character character) {
        switch (character) {
            case Symbol.Char.BLOCK_OPENER:
                tokens.accept(TokenClass.IDENTIFIER);
                readMode = this::ordinaryText;
                break;
            case Symbol.Char.PROPERTY_EVALUATION_CLOSER:
                tokens.accept(TokenClass.IDENTIFIER);
                readMode = this::ordinaryText;
                break;
            case '\t':
            case '\n':
            case ' ':
                tokens.accept();
                readMode = this::whiteSpaceInsideCommands;
                break;
            case Symbol.Char.NESTED_PROPERTY_ACCESSOR:
                tokens.accept(TokenClass.IDENTIFIER);
                tokens.add(character);
                tokens.accept(TokenClass.SYMBOL);
                break;
            case '\0':
                throw new IncompleteTokenException(lineNumber, columnNumber);
            default:
                tokens.add(character);
                break;
        }
    }

    private void endOfBlock(final Character character) {
        switch (character) {
            case Symbol.Char.BLOCK_OPENER:
                tokens.accept(TokenClass.KEYWORD);
                readMode = this::ordinaryText;
                break;
            case Symbol.Char.ENVIRONMENT_CHANGER:
                Keyword.END.getStringRepresentation().chars().forEach(c -> tokens.add((char) c));
                tokens.accept(TokenClass.KEYWORD);
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
            case Symbol.Char.BLOCK_OPENER:
                readMode = this::ordinaryText;
                break;
            default:
                commandOrPropertyEvaluation(character);
                readMode = this::commandOrPropertyEvaluation;
                break;
        }
    }

    public List<Token> getTokens() {
        return tokens.all();
    }
}

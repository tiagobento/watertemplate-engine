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

    private void ordinaryText(final Character character) {
        switch (character) {
            case Symbol.ENVIRONMENT_CHANGER:
                tokens.acceptFirstIfNotEmpty(TokenClass.TEXT);
                readMode = this::commandOrPropertyEvaluation;
                break;
            case Symbol.BLOCK_CLOSER:
                tokens.acceptFirstIfNotEmpty(TokenClass.TEXT);
                readMode = this::endOfBlock;
                break;
            case '\0':
                tokens.acceptFirstIfNotEmpty(TokenClass.TEXT);
                break;
            default:
                tokens.append(character);
                break;
        }
    }

    private void commandOrPropertyEvaluation(final Character character) {
        switch (character) {
            case Symbol.BLOCK_OPENER:
                tokens.acceptFirstIfNotEmpty(TokenClass.IDENTIFIER);
                readMode = this::ordinaryText;
                break;
            case Symbol.PROPERTY_EVALUATION_CLOSER:
                tokens.acceptFirstIfNotEmpty(TokenClass.IDENTIFIER);
                readMode = this::ordinaryText;
                break;
            case '\t':
            case '\n':
            case ' ':
                tokens.acceptFirstIfNotEmpty(TokenClass.KEYWORD, TokenClass.IDENTIFIER);
                readMode = this::whiteSpacesInsideCommandEnvironment;
                break;
            case Symbol.NESTED_PROPERTY_ACCESSOR:
                tokens.acceptFirstIfNotEmpty(TokenClass.IDENTIFIER);
                tokens.append(character);
                tokens.acceptFirstIfNotEmpty(TokenClass.SYMBOL);
                break;
            case '\0':
                throw new IncompleteTokenException(lineNumber, columnNumber);
            default:
                tokens.append(character);
                break;
        }
    }

    private void endOfBlock(final Character character) {
        switch (character) {
            case Symbol.BLOCK_OPENER:
                tokens.acceptFirstIfNotEmpty(TokenClass.KEYWORD);
                readMode = this::ordinaryText;
                break;
            case Symbol.ENVIRONMENT_CHANGER:
                Keyword.END.getStringRepresentation().chars().forEach(c -> tokens.append((char) c));
                tokens.acceptFirstIfNotEmpty(TokenClass.KEYWORD);
                readMode = this::ordinaryText;
                break;
            default:
                tokens.append(character);
                break;
        }
    }

    private void whiteSpacesInsideCommandEnvironment(final Character character) {
        switch (character) {
            case '\t':
            case '\n':
            case ' ':
                break;
            case Symbol.BLOCK_OPENER:
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

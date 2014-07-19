package org.watertemplate.interpreter.lexer;

import org.watertemplate.interpreter.lexer.exception.IncompleteTokenException;

import java.util.List;
import java.util.function.Consumer;

public class Lexer {

    private final Tokens tokens = new Tokens();
    private Consumer<Character> readMode = this::text;

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

    private void text(final Character character) {
        switch (character) {
            case LexerSymbol.ENVIRONMENT_CHANGER:
                tokens.acceptFirstIfNotEmpty(TokenType.TEXT);
                readMode = this::commandOrPropertyEvaluation;
                break;
            case LexerSymbol.BLOCK_CLOSER:
                tokens.acceptFirstIfNotEmpty(TokenType.TEXT);
                readMode = this::endOfBlock;
                break;
            case '\0':
                tokens.acceptFirstIfNotEmpty(TokenType.TEXT);
                break;
            default:
                tokens.append(character);
                break;
        }
    }

    private void commandOrPropertyEvaluation(final Character character) {
        switch (character) {
            case LexerSymbol.BLOCK_OPENER:
                tokens.acceptFirstIfNotEmpty(TokenType.PROPERTY_KEY);
                readMode = this::text;
                break;
            case LexerSymbol.PROPERTY_EVALUATION_CLOSER:
                tokens.acceptFirstIfNotEmpty(TokenType.PROPERTY_KEY);
                readMode = this::text;
                break;
            case '\t':
            case '\n':
            case ' ':
                tokens.acceptFirstIfNotEmpty(TokenType.IF, TokenType.FOR, TokenType.IN, TokenType.PROPERTY_KEY);
                readMode = this::whiteSpacesInsideCommandEnvironment;
                break;
            case LexerSymbol.ACCESSOR:
                tokens.acceptFirstIfNotEmpty(TokenType.PROPERTY_KEY);
                tokens.append(character);
                tokens.acceptFirstIfNotEmpty(TokenType.ACCESSOR);
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
            case LexerSymbol.BLOCK_OPENER:
                tokens.acceptFirstIfNotEmpty(TokenType.ELSE);
                readMode = this::text;
                break;
            case LexerSymbol.ENVIRONMENT_CHANGER:
                Keyword.END.getStringRepresentation().chars().forEach(c -> tokens.append((char) c));
                tokens.acceptFirstIfNotEmpty(TokenType.END);
                readMode = this::text;
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
            case LexerSymbol.BLOCK_OPENER:
                readMode = this::text;
                break;
            default:
                readMode = this::commandOrPropertyEvaluation;
                commandOrPropertyEvaluation(character);
                break;
        }
    }

    public List<Token> getTokens() {
        return tokens.all();
    }
}

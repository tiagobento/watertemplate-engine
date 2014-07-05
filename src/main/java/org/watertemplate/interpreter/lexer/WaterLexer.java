package org.watertemplate.interpreter.lexer;

import org.watertemplate.interpreter.Interpreter;

import java.util.ArrayList;
import java.util.List;

public class WaterLexer {

    private String currentToken;
    private List<String> tokens;
    private String resultString;


    public WaterLexer() {
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
                if (!currentToken.trim().isEmpty()) { addToken(); }

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

    public List<String> getTokens() {
        return tokens;
    }

    public String getResultString() {
        return resultString;
    }
}

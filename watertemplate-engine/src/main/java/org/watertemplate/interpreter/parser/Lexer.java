package org.watertemplate.interpreter.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.watertemplate.interpreter.parser.Terminal.TEXT;


public class Lexer {
    private static final int BUFFER_SIZE = 8192;

    private final char[] buffer = new char[BUFFER_SIZE];
    private List<Terminal> previousCandidates = new ArrayList<>();


    public List<Token> lex(final File templateFile) {

        try (final BufferedReader bufferedReader = new BufferedReader(new FileReader(templateFile))) {

            List<Token> tokens = tokenize(bufferedReader);
            tokens.add(Token.END_OF_INPUT);
            return tokens;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private List<Token> tokenize(final BufferedReader bufferedReader) throws IOException {
        StringBuilder accumulator = new StringBuilder();
        List<Token> tokens = new ArrayList<>();

        for (int nReadChars; (nReadChars = bufferedReader.read(buffer, 0, BUFFER_SIZE)) != -1; ) {
            for (int i = 0; i < nReadChars; i++) {
                i = process(accumulator, tokens, i, buffer[i]);
            }
        }

        process(accumulator, tokens, 0, '\0');
        return tokens;
    }

    private int process(final StringBuilder accumulator, final List<Token> tokens, int i, char c) {
        String previous = accumulator.toString();
        String current = accumulator.append(c).toString();

        Token accepted = tryToAcceptFrom(previous, current);

        if (accepted != null) {
            tokens.add(accepted);
            accumulator.setLength(0);
            return i - 1;
        }

        return i;
    }

    private Token tryToAcceptFrom(final String previous, final String current) {

        final List<Terminal> currentCandidates = Arrays.asList(Terminal.values()).stream()
                .filter(terminal -> terminal.isCandidateFrom(current))
                .collect(Collectors.toList());

        if (containsOnly(TEXT, currentCandidates) && previousCandidates.isEmpty()) {
            return null;
        }

        if (currentCandidates.isEmpty() && previousCandidates.isEmpty()) {
            return new Token(previous, TEXT);
        }

        if ((currentCandidates.isEmpty() || containsOnly(TEXT, currentCandidates)) && !previousCandidates.isEmpty()) {
            return acceptFrom(previous);
        }

        previousCandidates = currentCandidates;
        return null;
    }

    private Token acceptFrom(final String string) {
        List<Terminal> allAcceptable = previousCandidates.stream()
                .filter(terminal -> terminal.isAcceptableFrom(string))
                .collect(Collectors.toList());

        previousCandidates.clear();
        return new Token(string, allAcceptable);
    }

    private Boolean containsOnly(final Terminal terminal, final List<Terminal> list) {
        return list.size() == 1 && list.contains(terminal);
    }

    //
    // tests only
    List<Token> tokenize(String input) {
        StringBuilder accumulator = new StringBuilder();
        List<Token> tokens = new ArrayList<>();
        input += '\0';

        for (int i = 0; i < input.length(); i++) {
            i = process(accumulator, tokens, i, input.charAt(i));
        }

        return tokens;
    }
}

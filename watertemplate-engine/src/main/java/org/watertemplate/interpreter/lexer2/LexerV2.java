package org.watertemplate.interpreter.lexer2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.watertemplate.interpreter.lexer2.TerminalV2.END_OF_INPUT;
import static org.watertemplate.interpreter.lexer2.TerminalV2.TEXT;


class LexerV2 {
    private static final int BUFFER_SIZE = 8192;
    private final char[] buffer = new char[BUFFER_SIZE];
    private List<TerminalV2> previousCandidates = new ArrayList<>();


    public List<TokenV2> lex(final File templateFile) {

        try (final BufferedReader bufferedReader = new BufferedReader(new FileReader(templateFile))) {

            List<TokenV2> tokens = readAmbiguousTokens(bufferedReader);
            tokens.add(TokenV2.END_OF_INPUT);
            return tokens;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private List<TokenV2> readAmbiguousTokens(final BufferedReader bufferedReader) throws IOException {
        StringBuilder accumulator = new StringBuilder();
        List<TokenV2> ambiguousTokens = new ArrayList<>();
        int i = 0;

        for (int nReadChars; (nReadChars = bufferedReader.read(buffer, 0, BUFFER_SIZE)) != -1; ) {
            i = 0;
            for (;i < nReadChars; i++) {
                i = process(accumulator, ambiguousTokens, i, buffer[i]);
            }
        }
        process(accumulator, ambiguousTokens, i, '\0');
        return ambiguousTokens;
    }

    private int process(final StringBuilder accumulator, final List<TokenV2> ambiguousTokens, int i, char c) {
        String previous = accumulator.toString();
        String current = accumulator.append(c).toString();

        TokenV2 accepted = tryToAcceptFrom(previous, current);

        if (accepted != null) {
            ambiguousTokens.add(accepted);
            accumulator.setLength(0);
            return i - 1;
        }

        return i;
    }

    private TokenV2 tryToAcceptFrom(final String previous, final String current) {

        final List<TerminalV2> currentCandidates = Arrays.asList(TerminalV2.values()).stream()
                .filter(terminal -> terminal.isCandidateFrom(current))
                .collect(Collectors.toList());

        if (containsOnly(TEXT, currentCandidates) && previousCandidates.isEmpty()) {
            return null;
        }

        if (currentCandidates.isEmpty() && previousCandidates.isEmpty()) {
            return new TokenV2(previous, Collections.singletonList(TEXT));
        }

        if ((currentCandidates.isEmpty() || containsOnly(TEXT, currentCandidates)) && !previousCandidates.isEmpty()) {
            return acceptFrom(previous);
        }

        previousCandidates = currentCandidates;
        return null;
    }

    private TokenV2 acceptFrom(final String string) {
        List<TerminalV2> allAcceptable = previousCandidates.stream()
                .filter(terminal -> terminal.isAcceptableFrom(string))
                .collect(Collectors.toList());

        previousCandidates.clear();
        return new TokenV2(string, allAcceptable);
    }

    private Boolean containsOnly(final TerminalV2 terminal, final List<TerminalV2> list) {
        return list.size() == 1 && list.contains(terminal);
    }

    // for tests only

    List<TokenV2> tokenize(String input) {
        StringBuilder accumulator = new StringBuilder();
        List<TokenV2> ambiguousTokens = new ArrayList<>();
        input += '\0';

        for (int i = 0; i < input.length(); i++) {
            i = process(accumulator, ambiguousTokens, i, input.charAt(i));
        }

        ambiguousTokens.forEach(System.out::println);
        return ambiguousTokens;
    }

    public static void main(String[] args) {
        LexerV2 lexer = new LexerV2();
        List<TokenV2> tokens = lexer.lex(new File("/Users/tiagobento/Desktop/watertemplate-engine/watertemplate-example/src/main/resources/templates/en_US/collection/months_grid.html"));

        System.out.println("LEX\n--\n");
        tokens.forEach(System.out::println);
        System.out.println(tokens.size());
    }
}

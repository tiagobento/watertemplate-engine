package org.watertemplate.interpreter.lexer2;

import org.watertemplate.interpreter.parser.AbstractSyntaxTree;

import java.util.function.Function;
import java.util.regex.Pattern;

enum TerminalV2 { // FIXME: implements GrammarSymbol

    IF("if"::startsWith, "if"::equals),
    IN("in"::startsWith, "in"::equals),
    FOR("for"::startsWith, "for"::equals),
    ELSE("else"::startsWith, "else"::equals),
    END_OF_BLOCK(":~"::startsWith, ":~"::equals),

    ACCESSOR("."::equals),
    COLON(":"::equals),
    WAVE("~"::equals),

    BLANK(s -> s.trim().isEmpty()),

    PROP_KEY(TerminalV2::propertyKeyPatternMatches) {
        @Override
        public AbstractSyntaxTree buildAbstractSyntaxTree(final TokenV2 token) {
            return new AbstractSyntaxTree.Id(token.getValue());
        }
    },

    TEXT(s -> s.length() == 1 || (!s.endsWith(":") && !s.endsWith("~")), s -> true) {
        @Override
        public AbstractSyntaxTree buildAbstractSyntaxTree(final TokenV2 token) {
            return new AbstractSyntaxTree.Text(token.getValue());
        }
    },

    END_OF_INPUT("\0"::equals);

    //

    private final static Pattern PROP_KEY_PATTERN = Pattern.compile("^[a-zA-Z_][a-zA-Z0-9_]*$");

    private final Function<String, Boolean> isCandidate;
    private final Function<String, Boolean> isAcceptable;

    TerminalV2(final Function<String, Boolean> isCandidate) {
        this(isCandidate, isCandidate);
    }

    TerminalV2(final Function<String, Boolean> isCandidate, final Function<String, Boolean> isAcceptable) {
        this.isCandidate = isCandidate;
        this.isAcceptable = isAcceptable;
    }

    public final Boolean isCandidateFrom(final String s) {
        return isCandidate.apply(s);
    }

    public final Boolean isAcceptableFrom(final String s) {
        return isAcceptable.apply(s);
    }

    //

    private static Boolean propertyKeyPatternMatches(final String s) {
        return PROP_KEY_PATTERN.matcher(s).matches();
    }

    AbstractSyntaxTree buildAbstractSyntaxTree(final TokenV2 current) {
        return null; //FIXME: return AbstractSyntaxTree.EMPTY;
    }
}

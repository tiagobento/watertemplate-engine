package org.watertemplate.interpreter.parser;

import org.watertemplate.interpreter.parser.exception.IncorrectLocationForToken;

import java.util.function.Function;
import java.util.regex.Pattern;

enum Terminal implements GrammarSymbol {

    IF(Keywords.IF::startsWith, Keywords.IF::equals),
    IN(Keywords.IN::startsWith, Keywords.IN::equals),
    FOR(Keywords.FOR::startsWith, Keywords.FOR::equals),
    ELSE(Keywords.ELSE::startsWith, Keywords.ELSE::equals),
    END_OF_BLOCK(Keywords.END_OF_BLOCK::startsWith, Keywords.END_OF_BLOCK::equals),

    ACCESSOR(Keywords.ACCESSOR::equals),
    COLON(Keywords.COLON::equals),
    WAVE(Keywords.WAVE::equals),

    BLANK(s -> s.trim().isEmpty()),

    PROPERTY_KEY(Terminal::propertyKeyPatternMatches) {
        @Override
        public AbstractSyntaxTree buildAbstractSyntaxTree(final Token token) {
            return new AbstractSyntaxTree.Id(token.getValue());
        }
    },

    TEXT(s -> s.length() == 1 || (!s.endsWith(Keywords.COLON) && !s.endsWith(Keywords.WAVE)), s -> true) {
        @Override
        public AbstractSyntaxTree buildAbstractSyntaxTree(final Token token) {
            return new AbstractSyntaxTree.Text(token.getValue());
        }
    },

    END_OF_INPUT("\0"::equals);

    //

    private final static Pattern PROPERTY_KEY_PATTERN = Pattern.compile("^[a-zA-Z_][a-zA-Z0-9_]*$");

    private final Function<String, Boolean> isCandidate;
    private final Function<String, Boolean> isAcceptable;

    Terminal(final Function<String, Boolean> isCandidate) {
        this(isCandidate, isCandidate);
    }

    Terminal(final Function<String, Boolean> isCandidate, final Function<String, Boolean> isAcceptable) {
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
        return PROPERTY_KEY_PATTERN.matcher(s).matches();
    }

    @Override
    public final AbstractSyntaxTree buildAbstractSyntaxTree(final TokenStream tokenStream) {
        Token current = tokenStream.current();

        if (!current.canBe(this)) {
            throw new IncorrectLocationForToken(this, current);
        }

        tokenStream.shift();
        return buildAbstractSyntaxTree(current);
    }

    AbstractSyntaxTree buildAbstractSyntaxTree(final Token current) {
        return AbstractSyntaxTree.EMPTY;
    }

}

package org.watertemplate.interpreter.parser;

import org.watertemplate.interpreter.parser.exception.ParseException;

import java.util.ArrayList;
import java.util.List;

import static org.watertemplate.interpreter.parser.Terminal.*;

enum NonTerminal implements GrammarSymbol {

    IF_COMMAND {
        @Override
        void addProductions(final List<Production> productions) {
            productions.add(rhs(IF, ID, STATEMENTS, ELSE_BLOCK, END));
        }
    },
    FOR_COMMAND {
        @Override
        void addProductions(final List<Production> productions) {
            productions.add(rhs(FOR, PROPERTY_NAME, IN, ID, STATEMENTS, ELSE_BLOCK, END));
        }
    },
    ELSE_BLOCK {
        @Override
        void addProductions(final List<Production> productions) {
            productions.add(rhs(ELSE, STATEMENTS));
            productions.add(rhs());
        }
    },
    ID {
        @Override
        void addProductions(final List<Production> productions) {
            productions.add(rhs(PROPERTY_NAME, ACCESSOR, ID));
            productions.add(rhs(PROPERTY_NAME));
        }
    },
    STATEMENT {
        @Override
        void addProductions(final List<Production> productions) {
            productions.add(rhs(TEXT));
            productions.add(rhs(FOR_COMMAND));
            productions.add(rhs(IF_COMMAND));
            productions.add(rhs(ID));
        }
    },
    STATEMENTS {
        @Override
        void addProductions(final List<Production> productions) {
            productions.add(rhs(STATEMENT, STATEMENTS));
            productions.add(rhs());
        }
    },
    START_SYMBOL {
        @Override
        void addProductions(final List<Production> productions) {
            productions.add(rhs(STATEMENTS, END_OF_INPUT));
        }
    };

    static {
        for (final NonTerminal nonTerminal : NonTerminal.values()) {
            nonTerminal.addProductions(nonTerminal.productions);
        }
    }

    private final List<Production> productions = new ArrayList<>();

    abstract void addProductions(final List<Production> productions);

    Production rhs(final GrammarSymbol... symbols) {
        return new Production(this, symbols);
    }

    @Override
    public final ParseTree buildParseTreeFor(final TokenStream tokenStream) throws ParseException {
        ParseException lastException = null;

        for (Production production : productions) {
            try {
                return production.buildParseTreeFor(tokenStream);
            } catch (ParseException e) {
                lastException = e;
            }
        }

        throw lastException;
    }

}

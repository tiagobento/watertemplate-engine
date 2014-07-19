package org.watertemplate.interpreter.parser;

import org.watertemplate.interpreter.parser.exception.ParseException;

import java.util.ArrayList;
import java.util.List;

import static org.watertemplate.interpreter.parser.Terminal.*;

enum NonTerminal implements GrammarSymbol {

    IF_COMMAND {
        @Override
        void addProductions(final List<Production> productions) {
            productions.add(production(IF, ID, STATEMENTS, ELSE_BLOCK, END));
        }
    },
    FOR_COMMAND {
        @Override
        void addProductions(final List<Production> productions) {
            productions.add(production(FOR, PROPERTY_KEY, IN, ID, STATEMENTS, ELSE_BLOCK, END));
        }
    },
    ELSE_BLOCK {
        @Override
        void addProductions(final List<Production> productions) {
            productions.add(production(ELSE, STATEMENTS));
            productions.add(production());
        }
    },
    ID {
        @Override
        void addProductions(final List<Production> productions) {
            productions.add(production(PROPERTY_KEY, ACCESSOR, ID));
            productions.add(production(PROPERTY_KEY));
        }
    },
    STATEMENT {
        @Override
        void addProductions(final List<Production> productions) {
            productions.add(production(TEXT));
            productions.add(production(FOR_COMMAND));
            productions.add(production(IF_COMMAND));
            productions.add(production(ID));
        }
    },
    STATEMENTS {
        @Override
        void addProductions(final List<Production> productions) {
            productions.add(production(STATEMENT, STATEMENTS));
            productions.add(production());
        }
    },
    START_SYMBOL {
        @Override
        void addProductions(final List<Production> productions) {
            productions.add(production(STATEMENTS, END_OF_INPUT));
        }
    };

    static {
        for (final NonTerminal nonTerminal : NonTerminal.values()) {
            nonTerminal.addProductions(nonTerminal.productions);
        }
    }

    private final List<Production> productions = new ArrayList<>();

    abstract void addProductions(final List<Production> productions);

    Production production(final GrammarSymbol... symbols) {
        return new Production(this, symbols);
    }

    @Override
    public final ParseTree buildParseTree(final TokenStream tokenStream) throws ParseException {
        ParseException lastException = null;

        for (Production production : productions) {
            try {
                return production.buildParseTree(tokenStream);
            } catch (ParseException e) {
                lastException = e;
            }
        }

        throw lastException;
    }

}

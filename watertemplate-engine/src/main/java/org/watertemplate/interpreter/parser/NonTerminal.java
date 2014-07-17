package org.watertemplate.interpreter.parser;

import java.util.ArrayList;
import java.util.List;

import static org.watertemplate.interpreter.parser.Terminal.*;

enum NonTerminal implements ParserSymbol {

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
    ID_EVALUATION {
        @Override
        void addProductions(final List<Production> productions) {
            productions.add(rhs(ID));
        }
    },
    STATEMENT {
        @Override
        void addProductions(final List<Production> productions) {
            productions.add(rhs(TEXT));
            productions.add(rhs(FOR_COMMAND));
            productions.add(rhs(IF_COMMAND));
            productions.add(rhs(ID_EVALUATION));
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
        for (NonTerminal nonTerminal : NonTerminal.values()) {
            nonTerminal.addProductions(nonTerminal.productions);
        }
    }

    private final List<Production> productions = new ArrayList<>();

    private static Production rhs(final ParserSymbol... symbols) {
        return new Production(symbols);
    }

    public final boolean matches(final TokenStream tokenStream) {
        return productions.stream().anyMatch((production) -> production.matches(tokenStream));
    }

    abstract void addProductions(final List<Production> productions);
}

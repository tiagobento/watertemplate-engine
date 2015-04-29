package org.watertemplate.interpreter.parser;

import org.watertemplate.interpreter.parser.exception.ParseException;

import java.util.ArrayList;
import java.util.List;

import static org.watertemplate.interpreter.parser.Terminal.*;

enum NonTerminal implements GrammarSymbol {

    IF_COMMAND {
        @Override
        void addProductions(final List<GrammarSymbol> symbols) {
            symbols.add(new Production.If());
            symbols.add(new Production.IfElse());
        }
    },
    FOR_COMMAND {
        @Override
        void addProductions(final List<GrammarSymbol> symbols) {
            symbols.add(new Production.For());
            symbols.add(new Production.ForElse());
        }
    },
    NESTED_PROP {
        @Override
        void addProductions(final List<GrammarSymbol> symbols) {
            symbols.add(new Production.NestedProperty());
            symbols.add(new Production.Empty());
        }
    },
    ID {
        @Override
        void addProductions(final List<GrammarSymbol> symbols) {
            symbols.add(new Production.Id());
            symbols.add(PROPERTY_KEY);
        }
    },
    EVALUATION {
        @Override
        void addProductions(final List<GrammarSymbol> symbols) {
            symbols.add(new Production.Evaluation());
        }
    },
    STATEMENT {
        @Override
        void addProductions(final List<GrammarSymbol> symbols) {
            symbols.add(FOR_COMMAND);
            symbols.add(IF_COMMAND);
            symbols.add(EVALUATION);
            symbols.add(TEXT);
        }
    },
    STATEMENTS {
        @Override
        void addProductions(final List<GrammarSymbol> symbols) {
            symbols.add(new Production.Statements(STATEMENT, STATEMENTS));
            symbols.add(new Production.Empty());
        }
    },
    TEMPLATE {
        @Override
        void addProductions(final List<GrammarSymbol> symbols) {
            symbols.add(new Production.Statements(STATEMENTS, END_OF_INPUT));
        }
    };

    static {
        for (final NonTerminal nonTerminal : NonTerminal.values()) {
            nonTerminal.addProductions(nonTerminal.symbols);
        }
    }

    private final List<GrammarSymbol> symbols = new ArrayList<>();

    abstract void addProductions(final List<GrammarSymbol> symbols);

    public final AbstractSyntaxTree buildAbstractSyntaxTree(final TokenStream tokenStream) throws ParseException {
        ParseException lastException = null;

        for (GrammarSymbol symbol : symbols) {
            try {
                return symbol.buildAbstractSyntaxTree(tokenStream);
            } catch (ParseException e) {
                lastException = e;
            }
        }

        throw lastException;
    }
}

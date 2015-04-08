package org.watertemplate.interpreter.parser;

import org.watertemplate.interpreter.parser.abs.AbstractSyntaxTree;
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
    ID {
        @Override
        void addProductions(final List<GrammarSymbol> symbols) {
            symbols.add(new Production.IdWithNestedProperties());
            symbols.add(PROPERTY_KEY);
        }
    },
    STATEMENT {
        @Override
        void addProductions(final List<GrammarSymbol> symbols) {
            symbols.add(ID);
            symbols.add(TEXT);
            symbols.add(FOR_COMMAND);
            symbols.add(IF_COMMAND);
        }
    },
    STATEMENTS {
        @Override
        void addProductions(final List<GrammarSymbol> symbols) {
            symbols.add(new Production.Statements(STATEMENT, STATEMENTS));
            symbols.add(new Production.Empty());
        }
    },
    START_SYMBOL {
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

    public final AbstractSyntaxTree buildAbs(final TokenStream tokenStream) throws ParseException {
        ParseException lastException = null;

        for (GrammarSymbol symbol : symbols) {
            try {
                return symbol.buildAbs(tokenStream);
            } catch (ParseException e) {
                lastException = e;
            }
        }

        throw lastException;
    }


}

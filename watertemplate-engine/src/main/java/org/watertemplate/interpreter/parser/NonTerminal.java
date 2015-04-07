package org.watertemplate.interpreter.parser;

import org.watertemplate.interpreter.parser.abs.AbstractSyntaxTree;
import org.watertemplate.interpreter.parser.exception.ParseException;

import java.util.ArrayList;
import java.util.List;

import static org.watertemplate.interpreter.parser.Terminal.*;

enum NonTerminal implements GrammarSymbol {

    IF_COMMAND {
        @Override
        void addProductions(final List<GrammarSymbol> productions) {
            productions.add(new Production.If());
            productions.add(new Production.IfElse());
        }
    },
    FOR_COMMAND {
        @Override
        void addProductions(final List<GrammarSymbol> productions) {
            productions.add(new Production.For());
            productions.add(new Production.ForElse());
        }
    },
    ID {
        @Override
        void addProductions(final List<GrammarSymbol> productions) {
            productions.add(new Production.IdWithNestedProperties());
            productions.add(PROPERTY_KEY);
        }
    },
    STATEMENT {
        @Override
        void addProductions(final List<GrammarSymbol> productions) {
            productions.add(TEXT);
            productions.add(FOR_COMMAND);
            productions.add(IF_COMMAND);
            productions.add(ID);
        }
    },
    STATEMENTS {
        @Override
        void addProductions(final List<GrammarSymbol> productions) {
            productions.add(new Production(STATEMENT, STATEMENTS));
            productions.add(new Production.Empty());
        }
    },
    START_SYMBOL {
        @Override
        void addProductions(final List<GrammarSymbol> productions) {
            productions.add(new Production(STATEMENTS, END_OF_INPUT));
        }
    };

    static {
        for (final NonTerminal nonTerminal : NonTerminal.values()) {
            nonTerminal.addProductions(nonTerminal.productions);
        }
    }

    private final List<GrammarSymbol> productions = new ArrayList<>();

    abstract void addProductions(final List<GrammarSymbol> productions);

    @Override
    public final ParseTree buildParseTree(final TokenStream tokenStream) throws ParseException {
        ParseException lastException = null;

        for (GrammarSymbol production : productions) {
            try {
                return production.buildParseTree(tokenStream);
            } catch (ParseException e) {
                lastException = e;
            }
        }

        throw lastException;
    }
}

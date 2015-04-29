package org.watertemplate.interpreter.parser;

import org.watertemplate.interpreter.parser.exception.ParseException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.watertemplate.interpreter.parser.AbstractSyntaxTree.EMPTY;
import static org.watertemplate.interpreter.parser.NonTerminal.*;
import static org.watertemplate.interpreter.parser.Terminal.*;

abstract class Production implements GrammarSymbol {

    private final List<GrammarSymbol> symbols;

    Production(final GrammarSymbol... symbols) {
        this.symbols = Arrays.asList(symbols);
    }

    @Override
    public AbstractSyntaxTree buildAbstractSyntaxTree(final TokenStream tokenStream) {
        List<AbstractSyntaxTree> abstractSyntaxTrees = new ArrayList<>();
        int save = tokenStream.getCurrentTokenPosition();

        try {
            for (GrammarSymbol symbol : symbols) {
                abstractSyntaxTrees.add(symbol.buildAbstractSyntaxTree(tokenStream));
            }
        } catch (ParseException e) {
            tokenStream.reset(save);
            throw e;
        }

        return zip(abstractSyntaxTrees);
    }

    abstract AbstractSyntaxTree zip(final List<AbstractSyntaxTree> statements);

    static class If extends Production {

        If() {
            super(WAVE, IF, BLANK, ID, COLON,
                    STATEMENTS,
                    END_OF_BLOCK);
        }

        @Override
        AbstractSyntaxTree zip(final List<AbstractSyntaxTree> statements) {
            AbstractSyntaxTree.Id conditionId = (AbstractSyntaxTree.Id) statements.get(3);
            AbstractSyntaxTree ifStatements = statements.get(5);

            return new AbstractSyntaxTree.If(conditionId, ifStatements);
        }
    }

    static class IfElse extends Production {

        IfElse() {
            super(WAVE, IF, BLANK, ID, COLON,
                    STATEMENTS,
                    ELSE,
                    STATEMENTS, END_OF_BLOCK);
        }

        @Override
        AbstractSyntaxTree zip(final List<AbstractSyntaxTree> statements) {
            AbstractSyntaxTree.Id conditionId = (AbstractSyntaxTree.Id) statements.get(3);
            AbstractSyntaxTree ifStatements = statements.get(5);
            AbstractSyntaxTree elseStatements = statements.get(7);

            return new AbstractSyntaxTree.If(conditionId, ifStatements, elseStatements);
        }
    }

    static class For extends Production {

        For() {
            super(WAVE, FOR, BLANK, PROPERTY_KEY, BLANK, IN, BLANK, ID, COLON,
                    STATEMENTS,
                    END_OF_BLOCK);
        }

        @Override
        AbstractSyntaxTree zip(final List<AbstractSyntaxTree> statements) {
            String propertyKey = ((AbstractSyntaxTree.Id) statements.get(3)).getPropertyKey();
            AbstractSyntaxTree.Id collectionId = (AbstractSyntaxTree.Id) statements.get(7);
            AbstractSyntaxTree forStatements = statements.get(9);

            return new AbstractSyntaxTree.For(propertyKey, collectionId, forStatements);
        }
    }

    static class ForElse extends Production {

        ForElse() {
            super(WAVE, FOR, BLANK, PROPERTY_KEY, BLANK, IN, BLANK, ID, COLON,
                    STATEMENTS,
                    ELSE,
                    STATEMENTS,
                    END_OF_BLOCK);
        }

        @Override
        AbstractSyntaxTree zip(final List<AbstractSyntaxTree> statements) {
            String propertyKey = ((AbstractSyntaxTree.Id) statements.get(3)).getPropertyKey();
            AbstractSyntaxTree.Id collectionId = (AbstractSyntaxTree.Id) statements.get(7);
            AbstractSyntaxTree forStatements = statements.get(9);
            AbstractSyntaxTree elseStatements = statements.get(11);

            return new AbstractSyntaxTree.For(propertyKey, collectionId, forStatements, elseStatements);
        }
    }

    static class Id extends Production {

        Id() {
            super(PROPERTY_KEY, NESTED_PROP);
        }

        @Override
        AbstractSyntaxTree zip(final List<AbstractSyntaxTree> statements) {
            AbstractSyntaxTree.Id propertyKey = (AbstractSyntaxTree.Id) statements.get(0);
            AbstractSyntaxTree nested = statements.get(1);

            if (nested == EMPTY) {
                return propertyKey;
            } else {
                return new AbstractSyntaxTree.Id(propertyKey.getPropertyKey(), (AbstractSyntaxTree.Id) nested);
            }
        }
    }

    static class NestedProperty extends Production {

        NestedProperty() {
            super(ACCESSOR, PROPERTY_KEY);
        }

        @Override
        AbstractSyntaxTree zip(final List<AbstractSyntaxTree> statements) {
            return statements.get(1);
        }
    }

    static class Evaluation extends Production {

        Evaluation() {
            super(WAVE, ID, WAVE);
        }

        @Override
        AbstractSyntaxTree zip(final List<AbstractSyntaxTree> statements) {
            return statements.get(1);
        }
    }

    static class Statements extends Production {

        public Statements(final GrammarSymbol... statements) {
            super(statements);
        }

        @Override
        AbstractSyntaxTree zip(final List<AbstractSyntaxTree> statements) {
            return new AbstractSyntaxTree.Statements(statements);
        }
    }

    static class Empty extends Production {
        @Override
        AbstractSyntaxTree zip(final List<AbstractSyntaxTree> statements) {
            return AbstractSyntaxTree.EMPTY;
        }
    }
}





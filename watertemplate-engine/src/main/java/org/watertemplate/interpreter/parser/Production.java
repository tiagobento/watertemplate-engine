package org.watertemplate.interpreter.parser;

import org.watertemplate.interpreter.parser.exception.ParseException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.watertemplate.interpreter.parser.NonTerminal.ID;
import static org.watertemplate.interpreter.parser.NonTerminal.STATEMENTS;
import static org.watertemplate.interpreter.parser.Terminal.*;
import static org.watertemplate.interpreter.parser.AbstractSyntaxTree.Id;

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

        return zip(new AbstractSyntaxTree.Statements(abstractSyntaxTrees));
    }

    AbstractSyntaxTree zip(AbstractSyntaxTree.Statements statements) {
        return statements;
    }

    static class If extends Production {

        If() {
            super(IF, ID, STATEMENTS, END);
        }

        @Override
        AbstractSyntaxTree zip(final AbstractSyntaxTree.Statements statements) {
            Id conditionId = (Id) statements.child(1);
            AbstractSyntaxTree ifStatements = statements.child(2);

            return new AbstractSyntaxTree.If(conditionId, ifStatements);
        }
    }

    static class IfElse extends Production {

        IfElse() {
            super(IF, ID, STATEMENTS, ELSE, STATEMENTS, END);
        }

        @Override
        AbstractSyntaxTree zip(final AbstractSyntaxTree.Statements statements) {
            Id conditionId = (Id) statements.child(1);
            AbstractSyntaxTree ifStatements = statements.child(2);
            AbstractSyntaxTree elseStatements = statements.child(3);

            return new AbstractSyntaxTree.If(conditionId, ifStatements, elseStatements);
        }
    }

    static class For extends Production {

        For() {
            super(FOR, PROPERTY_KEY, IN, ID, STATEMENTS, END);
        }

        @Override
        AbstractSyntaxTree zip(final AbstractSyntaxTree.Statements statements) {
            String propertyKey = ((Id) statements.child(1)).getPropertyKey();
            Id collectionId = (Id) statements.child(3);
            AbstractSyntaxTree forStatements = statements.child(4);

            return new AbstractSyntaxTree.For(propertyKey, collectionId, forStatements);
        }
    }

    static class ForElse extends Production {

        ForElse() {
            super(FOR, PROPERTY_KEY, IN, ID, STATEMENTS, ELSE, STATEMENTS, END);
        }

        @Override
        AbstractSyntaxTree zip(final AbstractSyntaxTree.Statements statements) {
            String propertyKey = ((Id) statements.child(1)).getPropertyKey();
            Id collectionId = (Id) statements.child(3);
            AbstractSyntaxTree forStatements = statements.child(4);
            AbstractSyntaxTree elseStatements = statements.child(6);

            return new AbstractSyntaxTree.For(propertyKey, collectionId, forStatements, elseStatements);
        }
    }

    static class IdWithNestedProperties extends Production {

        IdWithNestedProperties() {
            super(PROPERTY_KEY, ACCESSOR, ID);
        }

        @Override
        AbstractSyntaxTree zip(final AbstractSyntaxTree.Statements statements) {
            String propertyKey = ((Id) statements.child(0)).getPropertyKey();
            Id nestedId = (Id) statements.child(2);
            return new Id(propertyKey, nestedId);
        }
    }

    static class Statements extends Production {
        public Statements(final GrammarSymbol... statements) {
            super(statements);
        }
    }

    static class Empty extends Production {
    }

    public static class Text extends Production {
    }

    public static class PropertyKey extends Production {
    }
}





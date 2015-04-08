package org.watertemplate.interpreter.parser;

import org.watertemplate.interpreter.parser.abs.AbstractSyntaxTree;
import org.watertemplate.interpreter.parser.exception.ParseException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.watertemplate.interpreter.parser.NonTerminal.ID;
import static org.watertemplate.interpreter.parser.NonTerminal.STATEMENTS;
import static org.watertemplate.interpreter.parser.Terminal.*;
import static org.watertemplate.interpreter.parser.abs.AbstractSyntaxTree.Id;

public abstract class Production implements GrammarSymbol {

    private final List<GrammarSymbol> symbols;

    Production(final GrammarSymbol... symbols) {
        this.symbols = Arrays.asList(symbols);
    }

    @Override
    public final ParseTree buildParseTree(final TokenStream tokenStream) {
        final ParseTree parseTree = new ParseTree(this);
        int save = tokenStream.getCurrentTokenPosition();

        try {
            for (GrammarSymbol symbol : symbols) {
                parseTree.addChild(symbol.buildParseTree(tokenStream));
            }
        } catch (ParseException e) {
            tokenStream.reset(save);
            throw e;
        }

        return parseTree;
    }

    public abstract AbstractSyntaxTree buildAbstractSyntaxTree(final ParseTree parseTree);

    static class If extends Production {

        If() {
            super(IF, ID, STATEMENTS, END);
        }

        @Override
        public AbstractSyntaxTree buildAbstractSyntaxTree(ParseTree parseTree) {
            Id conditionId = (Id) parseTree.child(1).getProduction().buildAbstractSyntaxTree(parseTree.child(1));
            AbstractSyntaxTree ifStatements = parseTree.child(2).getProduction().buildAbstractSyntaxTree(parseTree.child(2));

            return new AbstractSyntaxTree.If(conditionId, ifStatements);
        }

    }

    static class IfElse extends Production {

        IfElse() {
            super(IF, ID, STATEMENTS, ELSE, STATEMENTS, END);
        }

        @Override
        public AbstractSyntaxTree buildAbstractSyntaxTree(final ParseTree parseTree) {
            Id conditionId = (Id) parseTree.child(1).getProduction().buildAbstractSyntaxTree(parseTree.child(1));
            AbstractSyntaxTree ifStatements = parseTree.child(2).getProduction().buildAbstractSyntaxTree(parseTree.child(2));
            AbstractSyntaxTree elseStatements = parseTree.child(4).getProduction().buildAbstractSyntaxTree(parseTree.child(4));

            return new AbstractSyntaxTree.If(conditionId, ifStatements, elseStatements);
        }

    }

    static class For extends Production {

        For() {
            super(FOR, PROPERTY_KEY, IN, ID, STATEMENTS, END);
        }

        @Override
        public AbstractSyntaxTree buildAbstractSyntaxTree(final ParseTree parseTree) {
            String propertyName = parseTree.child(1).getValue();
            Id collectionId = (Id) parseTree.child(3).getProduction().buildAbstractSyntaxTree(parseTree.child(3));
            AbstractSyntaxTree forStatements = parseTree.child(4).getProduction().buildAbstractSyntaxTree(parseTree.child(4));

            return new AbstractSyntaxTree.For(propertyName, collectionId, forStatements);
        }

    }

    static class ForElse extends Production {

        ForElse() {
            super(FOR, PROPERTY_KEY, IN, ID, STATEMENTS, ELSE, STATEMENTS, END);
        }

        @Override
        public AbstractSyntaxTree buildAbstractSyntaxTree(final ParseTree parseTree) {
            String propertyName = parseTree.child(1).getValue();
            Id collectionId = (Id) parseTree.child(3).getProduction().buildAbstractSyntaxTree(parseTree.child(3));
            AbstractSyntaxTree forStatements = parseTree.child(4).getProduction().buildAbstractSyntaxTree(parseTree.child(4));
            AbstractSyntaxTree elseStatements = parseTree.child(6).getProduction().buildAbstractSyntaxTree(parseTree.child(6));

            return new AbstractSyntaxTree.For(propertyName, collectionId, forStatements, elseStatements);
        }

    }

    static class IdWithNestedProperties extends Production {

        IdWithNestedProperties() {
            super(PROPERTY_KEY, ACCESSOR, ID);
        }

        @Override
        public AbstractSyntaxTree buildAbstractSyntaxTree(final ParseTree parseTree) {
            Id nestedId = (Id) parseTree.child(2).getProduction().buildAbstractSyntaxTree(parseTree.child(2));
            return new Id(parseTree.child(0).getValue(), nestedId);
        }

    }

    static class Statements extends Production {

        public Statements(final GrammarSymbol... statements) {
            super(statements);
        }

        @Override
        public AbstractSyntaxTree buildAbstractSyntaxTree(ParseTree parseTree) {
            List<AbstractSyntaxTree> statements = parseTree.getChildren().stream().map(
                    child -> child.getProduction().buildAbstractSyntaxTree(child)
            ).collect(Collectors.toList());

            return new AbstractSyntaxTree.Statements(statements);
        }

    }

    static class PropertyKey extends Production {

        @Override
        public AbstractSyntaxTree buildAbstractSyntaxTree(ParseTree parseTree) {
            return new Id(parseTree.getValue());
        }
    }

    static class Text extends Production {

        @Override
        public AbstractSyntaxTree buildAbstractSyntaxTree(ParseTree parseTree) {
            return new AbstractSyntaxTree.Text(parseTree.getValue());
        }
    }

    static class Empty extends Production {
        @Override
        public AbstractSyntaxTree buildAbstractSyntaxTree(ParseTree parseTree) {
            return new AbstractSyntaxTree.Empty();
        }
    }
}





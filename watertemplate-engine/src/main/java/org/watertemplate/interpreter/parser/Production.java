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

abstract class Production implements GrammarSymbol, ParseTreeNode {

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

    @Override
    public abstract AbstractSyntaxTree buildAbstractSyntaxTree(final ParseTree parseTree);

    static class Empty extends Production {
        @Override
        public AbstractSyntaxTree buildAbstractSyntaxTree(ParseTree parseTree) {
            return new AbstractSyntaxTree.Empty();
        }
    }

    static class If extends Production {

        If() {
            super(IF, ID, STATEMENTS, END);
        }

        @Override
        public AbstractSyntaxTree buildAbstractSyntaxTree(ParseTree parseTree) {
            Id conditionId = (Id) parseTree.child(1).getNode().buildAbstractSyntaxTree(parseTree.child(1));
            AbstractSyntaxTree ifStatements = parseTree.child(2).getNode().buildAbstractSyntaxTree(parseTree.child(2));

            return new AbstractSyntaxTree.If(conditionId, ifStatements);
        }
    }

    static class IfElse extends Production {
        IfElse() {
            super(IF, ID, STATEMENTS, ELSE, STATEMENTS, END);
        }

        @Override
        public AbstractSyntaxTree buildAbstractSyntaxTree(final ParseTree parseTree) {
            Id conditionId = (Id) parseTree.child(1).getNode().buildAbstractSyntaxTree(parseTree.child(1));
            AbstractSyntaxTree ifStatements = parseTree.child(2).getNode().buildAbstractSyntaxTree(parseTree.child(2));
            AbstractSyntaxTree elseStatements = parseTree.child(4).getNode().buildAbstractSyntaxTree(parseTree.child(4));

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
            Id listIdAbstractSyntaxTree = (Id) parseTree.child(3).getNode().buildAbstractSyntaxTree(parseTree.child(3));
            AbstractSyntaxTree forStatements = parseTree.child(4).getNode().buildAbstractSyntaxTree(parseTree.child(4));

            return new AbstractSyntaxTree.For(propertyName, listIdAbstractSyntaxTree, forStatements);
        }
    }

    static class ForElse extends Production {
        ForElse() {
            super(FOR, PROPERTY_KEY, IN, ID, STATEMENTS, ELSE, STATEMENTS, END);
        }

        @Override
        public AbstractSyntaxTree buildAbstractSyntaxTree(final ParseTree parseTree) {
            String propertyName = parseTree.child(1).getValue();
            Id listIdAbstractSyntaxTree = (Id) parseTree.child(3).getNode().buildAbstractSyntaxTree(parseTree.child(3));
            AbstractSyntaxTree forStatements = parseTree.child(4).getNode().buildAbstractSyntaxTree(parseTree.child(4));
            AbstractSyntaxTree elseStatements = parseTree.child(6).getNode().buildAbstractSyntaxTree(parseTree.child(6));

            return new AbstractSyntaxTree.For(propertyName, listIdAbstractSyntaxTree, forStatements, elseStatements);
        }
    }

    static class IdWithNestedProperties extends Production {
        IdWithNestedProperties() {
            super(PROPERTY_KEY, ACCESSOR, ID);
        }

        @Override
        public AbstractSyntaxTree buildAbstractSyntaxTree(final ParseTree parseTree) {
            Id nestedPropertyAbs = (Id) parseTree.child(2).getNode().buildAbstractSyntaxTree(parseTree.child(2));
            return new Id(parseTree.child(0).getValue(), nestedPropertyAbs);
        }
    }

    static class Statements extends Production {
        public Statements(final GrammarSymbol ... statements) {
            super(statements);
        }

        @Override
        public AbstractSyntaxTree buildAbstractSyntaxTree(ParseTree parseTree) {
            List<AbstractSyntaxTree> statements = parseTree.getChildren().stream().map(
                    child -> child.getNode().buildAbstractSyntaxTree(child)
            ).collect(Collectors.toList());

            return new AbstractSyntaxTree.Statements(statements);
        }
    }
}





package org.watertemplate.interpreter.parser;

import org.junit.Test;
import org.watertemplate.interpreter.lexer.Token;
import org.watertemplate.interpreter.lexer.TokenFixture;
import org.watertemplate.interpreter.parser.exception.IncorrectLocationForToken;
import org.watertemplate.interpreter.parser.exception.NoMoreTokensOnStreamException;
import org.watertemplate.interpreter.parser.exception.ParseException;

import static org.junit.Assert.assertNotNull;

public class NonTerminalTest {
    public static class ForTest {
        @Test
        public void noElse() {
            TokenStream tokenStream = new TokenStream(
                new TokenFixture.For(), new TokenFixture.PropertyKey("x"), new TokenFixture.In(), new TokenFixture.PropertyKey("foo"),
                new TokenFixture.End()
            );

            assertNotNull(NonTerminal.FOR_COMMAND.buildParseTree(tokenStream));
        }

        @Test
        public void emptyBodies() {
            TokenStream tokenStream = new TokenStream(
                new TokenFixture.For(), new TokenFixture.PropertyKey("x"), new TokenFixture.In(), new TokenFixture.PropertyKey("foo"),
                new TokenFixture.Else(),
                new TokenFixture.End()
            );

            assertNotNull(NonTerminal.FOR_COMMAND.buildParseTree(tokenStream));
        }

        @Test
        public void regular() {
            TokenStream tokenStream = new TokenStream(
                new TokenFixture.For(), new TokenFixture.PropertyKey("x"), new TokenFixture.In(), new TokenFixture.PropertyKey("foo"),
                new TokenFixture.Text("foo text bar text"),
                new TokenFixture.Else(),
                new TokenFixture.Text("bar text foo text"),
                new TokenFixture.End()
            );

            assertNotNull(NonTerminal.FOR_COMMAND.buildParseTree(tokenStream));
        }
    }

    public static class IdWithNestedPropertiesTest {
        @Test
        public void singlePropertyKey() {
            TokenStream tokenStream = new TokenStream(
                new TokenFixture.PropertyKey("x")
            );

            assertNotNull(NonTerminal.ID.buildParseTree(tokenStream));
        }

        @Test
        public void nestedProperties() {
            TokenStream tokenStream = new TokenStream(
                new TokenFixture.PropertyKey("x"),
                new TokenFixture.Accessor(),
                new TokenFixture.PropertyKey("y")
            );

            assertNotNull(NonTerminal.ID.buildParseTree(tokenStream));
        }

        @Test (expected = IncorrectLocationForToken.class)
        public void doubleAccessor() {
            TokenStream tokenStream = new TokenStream(
                new TokenFixture.PropertyKey("x"),
                new TokenFixture.Accessor(),
                new TokenFixture.Accessor(),
                new TokenFixture.PropertyKey("y")
            );

            NonTerminal.START_SYMBOL.buildParseTree(tokenStream);
        }

        @Test (expected = IncorrectLocationForToken.class)
        public void extraAccessor() {
            TokenStream tokenStream = new TokenStream(
                new TokenFixture.PropertyKey("x"),
                new TokenFixture.Accessor(),
                new TokenFixture.PropertyKey("y"),
                new TokenFixture.Accessor()
            );

            NonTerminal.START_SYMBOL.buildParseTree(tokenStream);
        }
    }

    public static class IfTest {
        @Test
        public void noElse() {
            TokenStream tokenStream = new TokenStream(
                new TokenFixture.If(), new TokenFixture.PropertyKey("x"),
                new TokenFixture.End()
            );

            assertNotNull(NonTerminal.IF_COMMAND.buildParseTree(tokenStream));
        }

        @Test
        public void emptyBodies() {
            TokenStream tokenStream = new TokenStream(
                new TokenFixture.If(), new TokenFixture.PropertyKey("x"),
                new TokenFixture.Else(),
                new TokenFixture.End()
            );

            assertNotNull(NonTerminal.IF_COMMAND.buildParseTree(tokenStream));
        }

        @Test
        public void regular() {
            TokenStream tokenStream = new TokenStream(
                new TokenFixture.If(), new TokenFixture.PropertyKey("x"),
                new TokenFixture.Text("foo text bar text"),
                new TokenFixture.Else(),
                new TokenFixture.Text("bar text foo text"),
                new TokenFixture.End()
            );

            assertNotNull(NonTerminal.IF_COMMAND.buildParseTree(tokenStream));
        }

        @Test
        public void nested() {
            TokenStream tokenStream = new TokenStream(
                new TokenFixture.If(), new TokenFixture.PropertyKey("x"),
                    new TokenFixture.If(), new TokenFixture.PropertyKey("y"),
                        new TokenFixture.Text("nested foo text bar text"),
                    new TokenFixture.Else(),
                        new TokenFixture.If(), new TokenFixture.PropertyKey("y"),
                            new TokenFixture.Text("else nested foo text bar text"),
                        new TokenFixture.Else(),
                            new TokenFixture.Text("else nested foo text bar text"),
                        new TokenFixture.End(),
                    new TokenFixture.End(),
                new TokenFixture.Else(),
                    new TokenFixture.Text("bar text foo text"),
                new TokenFixture.End()
            );

            assertNotNull(NonTerminal.IF_COMMAND.buildParseTree(tokenStream));
        }

        @Test (expected = IncorrectLocationForToken.class)
        public void invalidNested() {
            TokenStream tokenStream = new TokenStream(
                new TokenFixture.If(), new TokenFixture.PropertyKey("x"),
                    new TokenFixture.For(), new TokenFixture.PropertyKey("y"), new TokenFixture.In(), new TokenFixture.PropertyKey("z"),
                        new TokenFixture.Text("nested bar text foo text"),
                    new TokenFixture.Else(),
                        new TokenFixture.Text("nested foo text bar text"),
                    // new End(), intentionally commented for visualization purpose.
                new TokenFixture.Else(),
                    new TokenFixture.Text("bar text foo text"),
                new TokenFixture.End()
            );

            NonTerminal.IF_COMMAND.buildParseTree(tokenStream);
        }

        @Test(expected = NoMoreTokensOnStreamException.class)
        public void missingEnd() {
            TokenStream tokenStream = new TokenStream(
                new TokenFixture.If(), new TokenFixture.PropertyKey("x"),
                    new TokenFixture.Text("foo text bar text"),
                new TokenFixture.Else(),
                    new TokenFixture.Text("bar text foo text")
            );

            NonTerminal.IF_COMMAND.buildParseTree(tokenStream);
        }
    }

    public static class StartSymbolTest {

        @Test
        public void onlyEndOfInput() {
            TokenStream tokenStream = new TokenStream(
                Token.END_OF_INPUT
            );

            assertNotNull(NonTerminal.START_SYMBOL.buildParseTree(tokenStream));
        }

        @Test(expected = ParseException.class)
        public void missingEndOfInput() {
            TokenStream tokenStream = new TokenStream(
                new TokenFixture.PropertyKey("x"),
                new TokenFixture.Text("a text"),
                new TokenFixture.PropertyKey("y"),
                new TokenFixture.Text("another text")
            );

            NonTerminal.START_SYMBOL.buildParseTree(tokenStream);
        }

        @Test
        public void regular() {
            TokenStream tokenStream = new TokenStream(
                new TokenFixture.PropertyKey("x"),
                new TokenFixture.Text("a text"),
                new TokenFixture.PropertyKey("y"),
                new TokenFixture.Text("another text"),
                Token.END_OF_INPUT
            );

            assertNotNull(NonTerminal.START_SYMBOL.buildParseTree(tokenStream));
        }
    }

    public static class StatementsTest {

        @Test
        public void empty() {
            assertNotNull(NonTerminal.STATEMENTS.buildParseTree(new TokenStream()));
        }

        @Test
        public void singleStatement() {
            TokenStream tokenStream = new TokenStream(
                new TokenFixture.PropertyKey("x")
            );

            assertNotNull(NonTerminal.STATEMENTS.buildParseTree(tokenStream));
        }

        @Test
        public void multipleStatements() {
            TokenStream tokenStream = new TokenStream(
                new TokenFixture.PropertyKey("x"),
                new TokenFixture.PropertyKey("y"),
                new TokenFixture.PropertyKey("z"),
                new TokenFixture.PropertyKey("w"),
                new TokenFixture.PropertyKey("foo"),
                new TokenFixture.PropertyKey("bar")
            );

            assertNotNull(NonTerminal.STATEMENTS.buildParseTree(tokenStream));
        }
    }

    public static class StatementTest {
        @Test(expected = ParseException.class)
        public void incompleteCommand() {
            TokenStream tokenStream = new TokenStream(
                new TokenFixture.If()
            );

            NonTerminal.STATEMENT.buildParseTree(tokenStream);
        }

        @Test(expected = ParseException.class)
        public void invalid() {
            TokenStream tokenStream = new TokenStream(
                new TokenFixture.Accessor()
            );

            NonTerminal.STATEMENT.buildParseTree(tokenStream);
        }
    }
}

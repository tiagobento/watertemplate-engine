package org.watertemplate.interpreter.lexer;

import org.junit.Assert;
import org.junit.Test;
import org.watertemplate.interpreter.lexer.exception.IncompleteTokenException;
import org.watertemplate.interpreter.lexer.exception.InvalidTokenException;

import java.util.List;
import java.util.stream.Collectors;

public class WaterLexerTest {

    @Test
    public void validTexts() {
        assertLexEqualsArray(lex("a"), tokens("a"));
        assertLexEqualsArray(lex("ab"), tokens("ab"));
        assertLexEqualsArray(lex("a b"), tokens("a b"));
        assertLexEqualsArray(lex("a\nb"), tokens("a\nb"));
        assertLexEqualsArray(lex("a\tb"), tokens("a\tb"));
        assertLexEqualsArray(lex("a\t\nb"), tokens("a\t\nb"));
    }


    @Test
    public void validProperties() {
        assertLexEqualsArray(lex("a~i~"), tokens("a", "i"));
        assertLexEqualsArray(lex("~i~b"), tokens("i", "b"));
        assertLexEqualsArray(lex("~i~~id~"), tokens("i", "id"));
        assertLexEqualsArray(lex("~i~ ~id~"), tokens("i", " ", "id"));
        assertLexEqualsArray(lex("~i~\n~id~"), tokens("i", "\n", "id"));
        assertLexEqualsArray(lex("~i~\t~id~"), tokens("i", "\t", "id"));
        assertLexEqualsArray(lex("~i~\t~id~\n"), tokens("i", "\t", "id", "\n"));
    }

    @Test
    public void validIds() {
        assertLexEqualsArray(lex("~a~"), tokens("a"));
        assertLexEqualsArray(lex("~a._~"), tokens("a", ".", "_"));
        assertLexEqualsArray(lex("~a.bc~"), tokens("a", ".", "bc"));
        assertLexEqualsArray(lex("~a.b.c~"), tokens("a", ".", "b", ".", "c"));
        assertLexEqualsArray(lex("~a1.b2.c3~"), tokens("a1", ".", "b2", ".", "c3"));
        assertLexEqualsArray(lex("~a.b.c~a.b.c"), tokens("a", ".", "b", ".", "c", "a.b.c"));
    }

    @Test
    public void validAccessors() {
        assertLexEqualsArray(lex("."), tokens("."));
        assertLexEqualsArray(lex("a."), tokens("a."));
        assertLexEqualsArray(lex(".a."), tokens(".a."));
        assertLexEqualsArray(lex(".a.b"), tokens(".a.b"));
        assertLexEqualsArray(lex("a.~a.b~"), tokens("a.", "a", ".", "b"));
        assertLexEqualsArray(lex("a.~a.b~.abc"), tokens("a.", "a", ".", "b", ".abc"));
        assertLexEqualsArray(lex("abcd.~a.b~. .~x.y.z~"), tokens("abcd.", "a", ".", "b", ". .", "x", ".", "y", ".", "z"));
    }

    @Test
    public void validIfs() {
        assertLexEqualsArray(lex("~if foo::~"), tokens("if", "foo", "end"));
        assertLexEqualsArray(lex("~if foo.bar::~"), tokens("if", "foo", ".", "bar", "end"));
        assertLexEqualsArray(lex("~if foo.bar::~"), tokens("if", "foo", ".", "bar", "end"));
        assertLexEqualsArray(lex("~if foo.bar: :~"), tokens("if", "foo", ".", "bar", " ", "end"));
        assertLexEqualsArray(lex("~if x.y:  ~z~  :~"), tokens("if", "x", ".", "y", "  ", "z", "  ", "end"));
        assertLexEqualsArray(lex("~if x.y:  ~z~  :else::~"), tokens("if", "x", ".", "y", "  ", "z", "  ", "else", "end"));
        assertLexEqualsArray(lex("~if x.y:  ~z~  :else: :~"), tokens("if", "x", ".", "y", "  ", "z", "  ", "else", " ", "end"));
        assertLexEqualsArray(lex("~if x::else:~if j: :~:~"), tokens("if", "x", "else", "if", "j", " ", "end", "end"));
    }

    @Test
    public void validFors() {
        assertLexEqualsArray(lex("~for foo in bar::~"), tokens("for", "foo", "in", "bar", "end"));
        assertLexEqualsArray(lex("~for foo.bar in x::~"), tokens("for", "foo", ".", "bar", "in", "x", "end"));
        assertLexEqualsArray(lex("~for x in y::else::~"), tokens("for", "x", "in", "y", "else", "end"));
        assertLexEqualsArray(lex("~for x in y: :else: :~"), tokens("for", "x", "in", "y", " ", "else", " ", "end"));
        assertLexEqualsArray(lex("~for x in y:~x~:else:~if x.a.b.c::else::~:~"), tokens("for", "x", "in", "y", "x", "else", "if", "x", ".", "a", ".", "b", ".", "c", "else", "end", "end"));
    }

    @Test
    public void validWhitespaces() {
        assertLexEqualsArray(lex("~if       foo.bar: :~"), tokens("if", "foo", ".", "bar", " ", "end"));
        assertLexEqualsArray(lex("~for     x    in    y: :else: :~"), tokens("for", "x", "in", "y", " ", "else", " ", "end"));
        assertLexEqualsArray(lex("~for\t\tx\tin  y::else::~"), tokens("for", "x", "in", "y", "else", "end"));
        assertLexEqualsArray(lex("~for\n\t\tx\n\t\tin\n\t\ty::else::~"), tokens("for", "x", "in", "y", "else", "end"));
        assertLexEqualsArray(lex("~if :"), tokens("if"));
        assertLexEqualsArray(lex("~if x :"), tokens("if", "x"));
        assertLexEqualsArray(lex("~for   x in  x.y  :"), tokens("for", "x", "in", "x", ".", "y"));
    }

    @Test
    public void invalidCommands() {
        lexExpecting("~", IncompleteTokenException.class);
        lexExpecting("~x", IncompleteTokenException.class);
        lexExpecting("~if", IncompleteTokenException.class);
        lexExpecting("~if x ::else: :x~", InvalidTokenException.class);
        lexExpecting(":  else :", InvalidTokenException.class);
        lexExpecting("~if *(::", InvalidTokenException.class);
    }

    @Test
    public void validTemplate() {
        String[] lex = lex(
            "~if x:\n" +
                "~y.p~\n" +
                "~z~\n" +
                "~if j:\n" +
                "~for x in z:\n" +
                "~x.u~\n" +
                ":~\n" +
                ":~\n" +
                ":else:\n" +
                "~bla~\n" +
                ":~"
        );

        String[] tokens = tokens(
            "if", "x", "\n",
            "y", ".", "p", "\n",
            "z", "\n",
            "if", "j", "\n",
            "for", "x", "in", "z", "\n",
            "x", ".", "u", "\n",
            "end", "\n",
            "end", "\n",
            "else", "\n",
            "bla", "\n",
            "end"
        );

        assertLexEqualsArray(lex, tokens);
    }

    //

    private String[] tokens(final String... strings) {
        return strings;
    }

    private String[] lex(final String string) {
        final WaterLexer lexer = new WaterLexer();

        string.chars().forEach((c) -> lexer.accept((char) c));
        lexer.accept('\0');

        final List<String> tokenValues = lexer.getTokens()
            .stream()
            .map(Token::getValue)
            .collect(Collectors.toList());

        return tokenValues.toArray(new String[tokenValues.size()]);
    }

    private void assertLexEqualsArray(final String[] lex, final String[] exp) {
        try {
            Assert.assertArrayEquals(exp, lex);
        } catch (AssertionError e) {
            System.out.print("lex: ");
            for (String s : lex) System.out.printf("[%s]", s);

            System.out.print("\n");
            System.out.print("exp: ");
            for (String s : exp) System.out.printf("[%s]", s);

            throw e;
        }
    }

    private <T extends Exception> void lexExpecting(final String string, final Class<T> exceptionClass) {
        try {
            lex(string);
        } catch (final Exception e) {
            Assert.assertEquals(exceptionClass, e.getClass());
            return;
        }

        Assert.fail("Invalid string lexed successfully");
    }

}
package org.watertemplate.interpreter.lexer;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class WaterLexerTest {

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

        String[] array = array(
            "if", "x", "\n",
            "y", ".", "p", "\n",
            "z", "\n",
            "if", "j", "\n",
            "for", "x", "in", "z", "\n",
            "x", ".", "u", "\n",
            "\n",
            "\n",
            "else", "\n",
            "bla", "\n"
        );

        assertLexEqualsArray(lex, array);
    }


    @Test
    public void validTexts() {
        assertLexEqualsArray(lex("a"), array("a"));
        assertLexEqualsArray(lex("ab"), array("ab"));
        assertLexEqualsArray(lex("a b"), array("a b"));
        assertLexEqualsArray(lex("a\nb"), array("a\nb"));
        assertLexEqualsArray(lex("a\tb"), array("a\tb"));
        assertLexEqualsArray(lex("a\t\nb"), array("a\t\nb"));
    }

    @Test
    public void validProperties() {
        assertLexEqualsArray(lex("a~i~"), array("a", "i"));
        assertLexEqualsArray(lex("~i~b"), array("i", "b"));
        assertLexEqualsArray(lex("~i~~id~"), array("i", "id"));
        assertLexEqualsArray(lex("~i~ ~id~"), array("i", " ", "id"));
        assertLexEqualsArray(lex("~i~\n~id~"), array("i", "\n", "id"));
        assertLexEqualsArray(lex("~i~\t~id~"), array("i", "\t", "id"));
        assertLexEqualsArray(lex("~i~\t~id~\n"), array("i", "\t", "id", "\n"));
    }

    @Test
    public void validIds() {
        assertLexEqualsArray(lex("~a~"), array("a"));
        assertLexEqualsArray(lex("~a._~"), array("a", ".", "_"));
        assertLexEqualsArray(lex("~a.bc~"), array("a", ".", "bc"));
        assertLexEqualsArray(lex("~a.b.c~"), array("a", ".", "b", ".", "c"));
        assertLexEqualsArray(lex("~a1.b2.c3~"), array("a1", ".", "b2", ".", "c3"));
        assertLexEqualsArray(lex("~a.b.c~a.b.c"), array("a", ".", "b", ".", "c", "a.b.c"));
    }

    @Test
    public void validAccessors() {
        assertLexEqualsArray(lex("."), array("."));
        assertLexEqualsArray(lex("a."), array("a."));
        assertLexEqualsArray(lex(".a."), array(".a."));
        assertLexEqualsArray(lex(".a.b"), array(".a.b"));
        assertLexEqualsArray(lex("a.~a.b~"), array("a.", "a", ".", "b"));
        assertLexEqualsArray(lex("a.~a.b~.abc"), array("a.", "a", ".", "b", ".abc"));
        assertLexEqualsArray(lex("abcd.~a.b~. .~x.y.z~"), array("abcd.", "a", ".", "b", ". .", "x", ".", "y", ".", "z"));
    }

    @Test
    public void validIfs() {
        assertLexEqualsArray(lex("~if foo::~"), array("if", "foo"));
        assertLexEqualsArray(lex("~if foo.bar::~"), array("if", "foo", ".", "bar"));
        assertLexEqualsArray(lex("~if foo. bar::~"), array("if", "foo", ".", "bar"));
        assertLexEqualsArray(lex("~if foo. bar: :~"), array("if", "foo", ".", "bar", " "));
        assertLexEqualsArray(lex("~if  foo.bar: :~"), array("if", "foo", ".", "bar", " "));
        assertLexEqualsArray(lex("~if x.y:  ~z~  :~"), array("if", "x", ".", "y", "  ", "z", "  "));
        assertLexEqualsArray(lex("~if x.y:  ~z~  :else::~"), array("if", "x", ".", "y", "  ", "z", "  ", "else"));
        assertLexEqualsArray(lex("~if x.y:  ~z~  :else: :~"), array("if", "x", ".", "y", "  ", "z", "  ", "else", " "));
        assertLexEqualsArray(lex("~if x::else:~if j: :~:~"), array("if", "x", "else", "if", "j", " "));
    }

    //

    private String[] lex(final String string) {
        final WaterLexer lexer = new WaterLexer();

        string.chars().forEach((c) -> lexer.lex((char) c));
        lexer.lex('\0');

        final List<String> list = lexer.getTokenValues();
        return list.toArray(new String[list.size()]);
    }

    private String[] array(final String... strings) {
        return strings;
    }

    private void assertLexEqualsArray(final String[] lex, final String[] array) {
        try {
            Assert.assertArrayEquals(array, lex);
        } catch (AssertionError e) {
            System.out.print("lex: ");
            for (String s : lex) System.out.printf("[%s]", s);

            System.out.print("\nexp: ");
            for (String s : array) System.out.printf("[%s]", s);

            throw e;
        }
    }

}
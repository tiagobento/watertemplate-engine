package org.watertemplate.interpreter.parser;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class LexerTest {

    @Test
    public void testReadAmbiguousTokens() {
        assertEquals(9, lex("~if x: ~ :~").size());
        assertEquals(11, lex("~if x.y: ~ :~").size());
        assertEquals(13, lex("a ~if x.y: ~ :~").size());
        assertEquals(15, lex("a ~if x.y: ~x~ :~").size());

        assertEquals(10, lex("~if x:: ~ :~").size());
        assertEquals(11, lex("~if x:: ~ ::~").size());
        assertEquals(12, lex("~if x:: ~~ ::~").size());
    }

    @Test
    public void validTexts() {
        assertEquals(1, lex("a").size());
        assertEquals(1, lex("ab").size());
        assertEquals(3, lex("a b").size());
        assertEquals(3, lex("a\nb").size());
        assertEquals(3, lex("a\tb").size());
        assertEquals(3, lex("a\t\nb").size());
    }

    @Test
    public void differentStarts() {
        assertEquals(4, lex(".~i~").size());
        assertEquals(4, lex("<span>~i~").size());
        assertEquals(4, lex("\n~i~").size());
        assertEquals(4, lex(" ~i~").size());
        assertEquals(4, lex("asd~i~").size());
        assertEquals(6, lex("asd.fgh~i~").size());
        assertEquals(6, lex(" in ~i~").size());
        assertEquals(3, lex(":~i~").size());
        assertEquals(4, lex("~~i~").size());
    }

    @Test
    public void validProperties() {
        assertEquals(4, lex("a~i~").size());
        assertEquals(4, lex("~i~b").size());
        assertEquals(6, lex("~i~~id~").size());
        assertEquals(7, lex("~i~ ~id~").size());
        assertEquals(7, lex("~i~\n~id~").size());
        assertEquals(7, lex("~i~\t~id~").size());
        assertEquals(7, lex("~i~\t~id~\n").size());
    }

    @Test
    public void validIds() {
        assertEquals(3, lex("~a~").size());
        assertEquals(5, lex("~a._~").size());
        assertEquals(5, lex("~a.bc~").size());
        assertEquals(7, lex("~a.b.c~").size());
        assertEquals(7, lex("~a1.b2.c3~").size());
        assertEquals(12, lex("~a.b.c~a.b.c").size());
    }

    @Test
    public void validAccessors() {
        assertEquals(1, lex(".").size());
        assertEquals(2, lex("a.").size());
        assertEquals(3, lex(".a.").size());
        assertEquals(4, lex(".a.b").size());
        assertEquals(7, lex("a.~a.b~").size());
        assertEquals(9, lex("a.~a.b~.abc").size());
        assertEquals(17, lex("abcd.~a.b~. .~x.y.z~").size());
    }

    @Test
    public void validIfs() {
        assertEquals(6, lex("~if foo::~").size());
        assertEquals(8, lex("~if foo.bar::~").size());
        assertEquals(9, lex("~if foo.bar: :~").size());
        assertEquals(13, lex("~if x.y:  ~z~  :~").size());
        assertEquals(14, lex("~if x.y:  ~z~  :else::~").size());
        assertEquals(15, lex("~if x.y:  ~z~  :else: :~").size());
        assertEquals(15, lex("~if x::else: ~if j: :~:~").size());
    }

    @Test
    public void validFors() {
        assertEquals(10, lex("~for foo in bar::~").size());
        assertEquals(12, lex("~for foo.bar in x::~").size());
        assertEquals(11, lex("~for x in y::else::~").size());
        assertEquals(13, lex("~for x in y: :else: :~").size());
        assertEquals(20, lex("~for x y z in if for if y::~").size());
        assertEquals(29, lex("~for x in y: ~x~:else: ~if x.a.b.c::else::~:~").size());
    }

    @Test
    public void validWhitespaces() {
        assertEquals(9, lex("~if       foo.bar: :~").size());
        assertEquals(13, lex("~for     x    in    y: :else: :~").size());
        assertEquals(11, lex("~for\t\tx\tin  y::else::~").size());
        assertEquals(11, lex("~for\n\t\tx\n\t\tin\n\t\ty::else::~").size());
        assertEquals(4, lex("~if :").size());
        assertEquals(6, lex("~if x :").size());
        assertEquals(12, lex("~for   x in  x.y  :").size());
    }

    @Test
    public void invalidCommands() {
        assertEquals(1, lex("~").size());
        assertEquals(2, lex("~x").size());
        assertEquals(2, lex("~if").size());
        assertEquals(2, lex("~if ").size());
        assertEquals(11, lex("~if x ::else: :x~").size());
        assertEquals(5, lex(":  else :").size());
        assertEquals(6, lex("~if *(::").size());
    }

    @Test
    public void colonsAndWavesAsText() {
        assertEquals(7, lex("~if x:::~").size());
        assertEquals(9, lex("~if x: ~ :~").size());
        assertEquals(10, lex("~if x: ~: :~").size());
        assertEquals(10, lex("~if x: ~~ :~").size());
        assertEquals(11, lex("~if x: ~:: :~").size());
        assertEquals(9, lex("~if x: ~:else::~").size());
        assertEquals(11, lex("~if x: ~:else: ~:~").size());
        assertEquals(12, lex("~if x: ~:else:: ~:~").size());
    }

    private List<Token> lex(final String input) {
        return new Lexer().tokenize(input);
    }
}
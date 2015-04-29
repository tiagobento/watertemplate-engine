package org.watertemplate.interpreter.lexer2;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class LexerV2Test {

    @Test
    public void testReadAmbiguousTokens() {
        assertEquals(9, ambiguous("~if x: ~ :~").size());
        assertEquals(11, ambiguous("~if x.y: ~ :~").size());
        assertEquals(13, ambiguous("a ~if x.y: ~ :~").size());
        assertEquals(15, ambiguous("a ~if x.y: ~x~ :~").size());

        assertEquals(10, ambiguous("~if x:: ~ :~").size());
        assertEquals(11, ambiguous("~if x:: ~ ::~").size());
        assertEquals(12, ambiguous("~if x:: ~~ ::~").size());
    }

    @Test
    public void validTexts() {
        assertEquals(1, ambiguous("a").size());
        assertEquals(1, ambiguous("ab").size());
        assertEquals(3, ambiguous("a b").size());
        assertEquals(3, ambiguous("a\nb").size());
        assertEquals(3, ambiguous("a\tb").size());
        assertEquals(3, ambiguous("a\t\nb").size());
    }


    @Test
    public void differentStarts() {
        assertEquals(4, ambiguous(".~i~").size());
        assertEquals(4, ambiguous("<span>~i~").size());
        assertEquals(4, ambiguous("\n~i~").size());
        assertEquals(4, ambiguous(" ~i~").size());
        assertEquals(4, ambiguous("asd~i~").size());
        assertEquals(6, ambiguous("asd.fgh~i~").size());
        assertEquals(6, ambiguous(" in ~i~").size());
        assertEquals(3, ambiguous(":~i~").size());
        assertEquals(4, ambiguous("~~i~").size());
    }

    @Test
    public void validProperties() {
        assertEquals(4, ambiguous("a~i~").size());
        assertEquals(4, ambiguous("~i~b").size());
        assertEquals(6, ambiguous("~i~~id~").size());
        assertEquals(7, ambiguous("~i~ ~id~").size());
        assertEquals(7, ambiguous("~i~\n~id~").size());
        assertEquals(7, ambiguous("~i~\t~id~").size());
        assertEquals(7, ambiguous("~i~\t~id~\n").size());
    }

    @Test
    public void validIds() {
        assertEquals(3, ambiguous("~a~").size());
        assertEquals(5, ambiguous("~a._~").size());
        assertEquals(5, ambiguous("~a.bc~").size());
        assertEquals(7, ambiguous("~a.b.c~").size());
        assertEquals(7, ambiguous("~a1.b2.c3~").size());
        assertEquals(12, ambiguous("~a.b.c~a.b.c").size());
    }

    @Test
    public void validAccessors() {
        assertEquals(1, ambiguous(".").size());
        assertEquals(2, ambiguous("a.").size());
        assertEquals(3, ambiguous(".a.").size());
        assertEquals(4, ambiguous(".a.b").size());
        assertEquals(7, ambiguous("a.~a.b~").size());
        assertEquals(9, ambiguous("a.~a.b~.abc").size());
        assertEquals(17, ambiguous("abcd.~a.b~. .~x.y.z~").size());
    }

    @Test
    public void validIfs() {
        assertEquals(6, ambiguous("~if foo::~").size());
        assertEquals(8, ambiguous("~if foo.bar::~").size());
        assertEquals(9, ambiguous("~if foo.bar: :~").size());
        assertEquals(13, ambiguous("~if x.y:  ~z~  :~").size());
        assertEquals(16, ambiguous("~if x.y:  ~z~  :else::~").size());
        assertEquals(17, ambiguous("~if x.y:  ~z~  :else: :~").size());
        assertEquals(17, ambiguous("~if x::else: ~if j: :~:~").size());
    }

    @Test
    public void validFors() {
        assertEquals(10, ambiguous("~for foo in bar::~").size());
        assertEquals(12, ambiguous("~for foo.bar in x::~").size());
        assertEquals(13, ambiguous("~for x in y::else::~").size());
        assertEquals(15, ambiguous("~for x in y: :else: :~").size());
        assertEquals(20, ambiguous("~for x y z in if for if y::~").size());
        assertEquals(33, ambiguous("~for x in y: ~x~:else: ~if x.a.b.c::else::~:~").size());
    }

    @Test
    public void validWhitespaces() {
        assertEquals(9, ambiguous("~if       foo.bar: :~").size());
        assertEquals(15, ambiguous("~for     x    in    y: :else: :~").size());
        assertEquals(13, ambiguous("~for\t\tx\tin  y::else::~").size());
        assertEquals(13, ambiguous("~for\n\t\tx\n\t\tin\n\t\ty::else::~").size());
        assertEquals(4, ambiguous("~if :").size());
        assertEquals(6, ambiguous("~if x :").size());
        assertEquals(12, ambiguous("~for   x in  x.y  :").size());
    }

    @Test
    public void invalidCommands() {
        assertEquals(1, ambiguous("~").size());
        assertEquals(2, ambiguous("~x").size());
        assertEquals(2, ambiguous("~if").size());
        assertEquals(2, ambiguous("~if ").size());
        assertEquals(13, ambiguous("~if x ::else: :x~").size());
        assertEquals(5, ambiguous(":  else :").size());
        assertEquals(6, ambiguous("~if *(::").size());
    }

    @Test
    public void colonsAndWavesAsText() {
        assertEquals(7, ambiguous("~if x:::~").size());
        assertEquals(9, ambiguous("~if x: ~ :~").size());
        assertEquals(10, ambiguous("~if x: ~: :~").size());
        assertEquals(10, ambiguous("~if x: ~~ :~").size());
        assertEquals(11, ambiguous("~if x: ~:: :~").size());
        assertEquals(11, ambiguous("~if x: ~:else::~").size());
        assertEquals(13, ambiguous("~if x: ~:else: ~:~").size());
        assertEquals(14, ambiguous("~if x: ~:else:: ~:~").size());
    }

    private List<TokenV2> ambiguous(final String input) {
        return new LexerV2().tokenize(input);
    }
}
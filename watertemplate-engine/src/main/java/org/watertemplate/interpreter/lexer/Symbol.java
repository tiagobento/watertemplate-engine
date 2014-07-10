package org.watertemplate.interpreter.lexer;

public class Symbol {
    static class Char {
        public static final char BLOCK_OPENER = ':';
        public static final char BLOCK_CLOSER = ':';
        public static final char ACCESSOR = '.';
        public static final char PROPERTY_EVALUATION_CLOSER = '~';
        public static final char ENVIRONMENT_CHANGER = '~';
    }
}

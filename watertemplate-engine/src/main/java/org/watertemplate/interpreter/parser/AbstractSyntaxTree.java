package org.watertemplate.interpreter.parser;

import org.watertemplate.TemplateObject;
import org.watertemplate.interpreter.lexer.LexerSymbol;
import org.watertemplate.interpreter.parser.exception.IdCouldNotBeResolvedException;

import java.util.List;
import java.util.Locale;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.watertemplate.TemplateMap.Arguments;
import static org.watertemplate.TemplateObject.*;

public abstract class AbstractSyntaxTree {

    static final AbstractSyntaxTree EMPTY = new Empty();

    public final String evaluate(final Arguments arguments, final Locale locale) {
        return run(arguments, locale)
                .map(Supplier::get)
                .reduce(String::concat)
                .orElse("");
    }

    abstract Stream<Supplier<String>> run(final Arguments arguments, final Locale locale);

    public static class For extends AbstractSyntaxTree {

        private final String variableName;
        private final Id collectionId;
        private final AbstractSyntaxTree forStatements;
        private final AbstractSyntaxTree elseStatements;

        public For(final String variableName, final Id collectionId, final AbstractSyntaxTree forStatements) {
            this(variableName, collectionId, forStatements, EMPTY);
        }

        public For(final String variableName, final Id collectionId, final AbstractSyntaxTree forStatements, final AbstractSyntaxTree elseStatements) {
            this.variableName = variableName;
            this.collectionId = collectionId;
            this.forStatements = forStatements;
            this.elseStatements = elseStatements;
        }

        @Override
        @SuppressWarnings("unchecked")
        /* Because it's not possible to retrieve the type from the CollectionObject, the compiler
        * can't figure out which type to use in 'map'. This results in an warning. */
        Stream<Supplier<String>> run(final Arguments arguments, final Locale locale) {
            final CollectionObject collection = (CollectionObject) collectionId.templateObject(arguments, locale);

            if (collection.isEmpty()) {
                return elseStatements.run(arguments, locale);
            }

            final Arguments forArguments = new Arguments(arguments); // Mutable
            final BiConsumer mapper = collection.getMapper();

            return collection.getCollection().stream().map(item -> {
                forArguments.addMappedObject(variableName, item, mapper);
                return forStatements.run(forArguments, locale);
            }).flatMap((Function) s -> s);
        }
    }

    public static class Id extends AbstractSyntaxTree {

        private final String propertyKey;
        private final Id nestedId;

        public Id(final String propertyKey) {
            this(propertyKey, null);
        }

        public Id(final String propertyKey, final Id nestedId) {
            this.propertyKey = propertyKey;
            this.nestedId = nestedId;
        }

        public String getPropertyKey() {
            return propertyKey;
        }

        public String getFullId() {
            if (nestedId == null) {
                return propertyKey;
            }

            return propertyKey + LexerSymbol.ACCESSOR + nestedId.getFullId();
        }

        TemplateObject templateObject(final Arguments arguments, final Locale locale) {
            TemplateObject object = arguments.get(propertyKey);

            if (object == null) {
                throw new IdCouldNotBeResolvedException(this);
            }

            if (nestedId == null) {
                return object;
            }

            if (!(object instanceof MappedObject)) {
                throw new IdCouldNotBeResolvedException(this);
            }

            try {
                Arguments mappedProperties = ((MappedObject) object).map();
                return nestedId.templateObject(mappedProperties, locale);
            } catch (IdCouldNotBeResolvedException e) {
                throw new IdCouldNotBeResolvedException(this);
            }
        }

        @Override
        Stream<Supplier<String>> run(final Arguments arguments, final Locale locale) {
            return Stream.of(() -> this.templateObject(arguments, locale).evaluate(locale));
        }
    }

    public static class If extends AbstractSyntaxTree {

        private final Id conditionId;
        private final AbstractSyntaxTree ifStatements;
        private final AbstractSyntaxTree elseStatements;

        public If(final Id conditionId, final AbstractSyntaxTree ifStatements) {
            this(conditionId, ifStatements, EMPTY);
        }

        public If(final Id conditionId, final AbstractSyntaxTree ifStatements, final AbstractSyntaxTree elseStatements) {
            this.conditionId = conditionId;
            this.ifStatements = ifStatements;
            this.elseStatements = elseStatements;
        }

        @Override
        Stream<Supplier<String>> run(final Arguments arguments, final Locale locale) {
            ConditionObject condition = (ConditionObject) conditionId.templateObject(arguments, locale);

            if (condition.isTrue()) {
                return ifStatements.run(arguments, locale);
            } else {
                return elseStatements.run(arguments, locale);
            }
        }
    }

    public static class Statements extends AbstractSyntaxTree {

        private final List<AbstractSyntaxTree> abstractSyntaxTrees;

        public Statements(final List<AbstractSyntaxTree> abstractSyntaxTrees) {
            this.abstractSyntaxTrees = abstractSyntaxTrees;
        }

        @Override
        Stream<Supplier<String>> run(final Arguments arguments, final Locale locale) {
            return abstractSyntaxTrees.stream()
                    .map(ast -> ast.run(arguments, locale))
                    .flatMap(s -> s);
        }
    }

    public static class Text extends AbstractSyntaxTree {
        private final String value;

        public Text(final String value) {
            this.value = value;
        }

        @Override
        Stream<Supplier<String>> run(final Arguments arguments, final Locale locale) {
            return Stream.of(() -> value);
        }
    }

    private static class Empty extends AbstractSyntaxTree {
        @Override
        Stream<Supplier<String>> run(final Arguments arguments, final Locale locale) {
            return Stream.of(() -> "");
        }
    }
}

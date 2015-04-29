package org.watertemplate.interpreter.parser;

import org.watertemplate.TemplateObject;
import org.watertemplate.interpreter.parser.exception.IdCouldNotBeResolvedException;

import java.util.List;
import java.util.Locale;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.watertemplate.TemplateMap.Arguments;


public abstract class AbstractSyntaxTree {

    static final AbstractSyntaxTree EMPTY = new Empty();

    public abstract String string(final Arguments arguments, final Locale locale);

    public static class For extends AbstractSyntaxTree {

        private final String variableName;
        private final Id collectionId;
        private final AbstractSyntaxTree forStatements;
        private final AbstractSyntaxTree elseStatements;

        public For(final String variableName, final Id collectionId, final AbstractSyntaxTree forStatements) {
            this(variableName, collectionId, forStatements, EMPTY);
        }

        @Override
        public String string(final Arguments arguments, final Locale locale) {
            final TemplateObject.Collection collection = (TemplateObject.Collection) collectionId.templateObject(arguments);

            if (collection.isEmpty()) {
                return elseStatements.string(arguments, locale);
            }

            final Arguments forArguments = new Arguments(arguments); // Mutable
            final BiConsumer mapper = collection.getMapper();
            final StringBuilder sb = new StringBuilder();

            for (final Object item : collection.getCollection()) {
                forArguments.addMappedObject(variableName, item, mapper);
                sb.append(forStatements.string(forArguments, locale));
            }

            return sb.toString();
        }

        public For(final String variableName, final Id collectionId, final AbstractSyntaxTree forStatements, final AbstractSyntaxTree elseStatements) {
            this.variableName = variableName;
            this.collectionId = collectionId;
            this.forStatements = forStatements;
            this.elseStatements = elseStatements;
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

            return propertyKey + Keywords.ACCESSOR + nestedId.getFullId();
        }

        TemplateObject templateObject(final Arguments arguments) {
            TemplateObject object = arguments.get(propertyKey);

            if (object == null) {
                throw new IdCouldNotBeResolvedException(this);
            }

            if (nestedId == null) {
                return object;
            }

            if (!(object instanceof TemplateObject.Mapped)) {
                throw new IdCouldNotBeResolvedException(this);
            }

            try {
                Arguments mappedProperties = ((TemplateObject.Mapped) object).map();
                return nestedId.templateObject(mappedProperties);
            } catch (IdCouldNotBeResolvedException e) {
                throw new IdCouldNotBeResolvedException(this);
            }
        }

        @Override
        public String string(final Arguments arguments, final Locale locale) {
            return this.templateObject(arguments).string(locale);
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
        public String string(final Arguments arguments, final Locale locale) {
            TemplateObject.Condition condition = (TemplateObject.Condition) conditionId.templateObject(arguments);

            if (condition.isTrue()) {
                return ifStatements.string(arguments, locale);
            } else {
                return elseStatements.string(arguments, locale);
            }
        }
    }

    public static class Statements extends AbstractSyntaxTree {

        private final List<AbstractSyntaxTree> abstractSyntaxTrees;

        public Statements(final List<AbstractSyntaxTree> abstractSyntaxTrees) {
            this.abstractSyntaxTrees = abstractSyntaxTrees.stream()
                    .flatMap(this::flatten).collect(Collectors.toList());
        }

        private Stream<AbstractSyntaxTree> flatten(final AbstractSyntaxTree ast) {
            if (ast instanceof Statements) {
                return ((Statements) ast).abstractSyntaxTrees.stream();
            } else if (ast != EMPTY) {
                return Stream.of(ast);
            } else {
                return Stream.of();
            }
        }

        @Override
        public String string(final Arguments arguments, final Locale locale) {
            final StringBuilder sb = new StringBuilder();

            for (AbstractSyntaxTree ast : abstractSyntaxTrees) {
                sb.append(ast.string(arguments, locale));
            }

            return sb.toString();
        }
    }

    public static class Text extends AbstractSyntaxTree {
        private final String value;

        public Text(final String value) {
            this.value = value;
        }

        @Override
        public String string(final Arguments arguments, final Locale locale) {
            return value;
        }
    }

    private static class Empty extends AbstractSyntaxTree {
        @Override
        public String string(final Arguments arguments, final Locale locale) {
            return "";
        }
    }
}

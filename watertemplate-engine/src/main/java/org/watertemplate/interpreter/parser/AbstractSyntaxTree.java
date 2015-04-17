package org.watertemplate.interpreter.parser;

import org.watertemplate.TemplateObject;
import org.watertemplate.interpreter.lexer.LexerSymbol;
import org.watertemplate.interpreter.parser.exception.IdCouldNotBeResolvedException;
import org.watertemplate.interpreter.parser.exception.NotCollectionObjectException;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static org.watertemplate.TemplateMap.*;
import static org.watertemplate.TemplateObject.StringObject;

public abstract class AbstractSyntaxTree {

    public String evaluate(final Arguments arguments, final Locale locale) { return run(arguments, locale).evaluate(locale).toString(); }

    abstract TemplateObject run(final Arguments arguments, final Locale locale);

    public static class For extends AbstractSyntaxTree {

        private final String variableName;
        private final Id collectionId;
        private final AbstractSyntaxTree forStatements;
        private final AbstractSyntaxTree elseStatements;

        public For(final String variableName, final Id collectionId, final AbstractSyntaxTree forStatements) {
            this(variableName, collectionId, forStatements, new Empty());
        }

        public For(final String variableName, final Id collectionId, final AbstractSyntaxTree forStatements, final AbstractSyntaxTree elseStatements) {
            this.variableName = variableName;
            this.collectionId = collectionId;
            this.forStatements = forStatements;
            this.elseStatements = elseStatements;
        }

        @Override
        TemplateObject run(final Arguments arguments, final Locale locale) {
            TemplateObject collection = collectionId.run(arguments, locale);

            if (!(collection instanceof TemplateObject.CollectionObject)) {
                throw new NotCollectionObjectException(collectionId);
            }

            TemplateObject.CollectionObject collectionObject = (TemplateObject.CollectionObject) collection;

            if (collectionObject.isEmpty()) {
                return elseStatements.run(arguments, locale);
            }

            StringBuilder sb = new StringBuilder();

            for (final Object item : collectionObject.getIterable()) {
                arguments.addMappedObject(variableName, item, collectionObject.getMapper());
                sb.append(forStatements.evaluate(arguments, locale)); // toString called
            }

            arguments.remove(variableName);
            return new StringObject(sb.toString());
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

        TemplateObject run(final Arguments arguments, final Locale locale) {
            TemplateObject object = arguments.get(propertyKey);

            if (object == null) {
                throw new IdCouldNotBeResolvedException(this);
            }

            if (nestedId == null) {
                return object;
            }

            if (!(object instanceof TemplateObject.MappedObject)) {
                throw new IdCouldNotBeResolvedException(this);
            }

            try {
                Arguments mappedProperties = ((TemplateObject.MappedObject) object).map();
                return nestedId.run(mappedProperties, locale);
            } catch (IdCouldNotBeResolvedException e) {
                throw new IdCouldNotBeResolvedException(this);
            }
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
    }

    public static class If extends AbstractSyntaxTree {

        private final Id conditionId;
        private final AbstractSyntaxTree ifStatements;
        private final AbstractSyntaxTree elseStatements;

        public If(final Id conditionId, final AbstractSyntaxTree ifStatements) {
            this(conditionId, ifStatements, new Empty());
        }

        public If(final Id conditionId, final AbstractSyntaxTree ifStatements, final AbstractSyntaxTree elseStatements) {
            this.conditionId = conditionId;
            this.ifStatements = ifStatements;
            this.elseStatements = elseStatements;
        }

        @Override
        TemplateObject run(final Arguments arguments, final Locale locale) {
            if ((boolean) conditionId.run(arguments, locale).evaluate(locale)) {
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

        public Statements(final AbstractSyntaxTree... abstractSyntaxTrees) {
            this.abstractSyntaxTrees = Arrays.asList(abstractSyntaxTrees);
        }

        @Override
        TemplateObject run(final Arguments arguments, final Locale locale) {
            StringBuilder sb = new StringBuilder();
            for (AbstractSyntaxTree abstractSyntaxTree : abstractSyntaxTrees) {
                sb.append(abstractSyntaxTree.evaluate(arguments, locale)); // toString called
            }
            return new StringObject(sb.toString());
        }
    }

    public static class Text extends AbstractSyntaxTree {
        private final String value;

        public Text(final String value) {
            this.value = value;
        }

        @Override
        TemplateObject run(final Arguments arguments, final Locale locale) {
            return new StringObject(value);
        }
    }

    public static class Empty extends AbstractSyntaxTree {
        @Override
        TemplateObject run(final Arguments arguments, final Locale locale) {
            return new StringObject("");
        }
    }
}

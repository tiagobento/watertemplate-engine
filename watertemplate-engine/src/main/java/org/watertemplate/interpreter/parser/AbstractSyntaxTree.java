package org.watertemplate.interpreter.parser;

import org.watertemplate.interpreter.lexer.LexerSymbol;
import org.watertemplate.interpreter.parser.exception.IdCouldNotBeResolvedException;
import org.watertemplate.interpreter.parser.exception.ObjectNotTemplateCollectionException;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static org.watertemplate.TemplateMap.*;

public interface AbstractSyntaxTree {

    public Object run(final Arguments arguments, final Locale locale);

    static class For implements AbstractSyntaxTree {

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
        public Object run(final Arguments arguments, final Locale locale) {
            Object collection = collectionId.run(arguments, locale);

            if (!(collection instanceof CollectionObject)) {
                throw new ObjectNotTemplateCollectionException(collectionId);
            }

            CollectionObject collectionObject = (CollectionObject) collection;

            if (collectionObject.iterator() == null || !collectionObject.iterator().hasNext()) {
                return elseStatements.run(arguments, locale);
            }

            StringBuilder sb = new StringBuilder();

            for (final Object item : collectionObject) {
                arguments.addMappedObject(variableName, item, collectionObject.getMapper());
                sb.append(forStatements.run(arguments, locale));
            }

            arguments.remove(variableName);
            return sb.toString();
        }
    }

    static class Id implements AbstractSyntaxTree {

        private final String propertyKey;
        private final Id nestedId;

        public Id(final String propertyKey) {
            this(propertyKey, null);
        }

        public Id(final String propertyKey, final Id nestedId) {
            this.propertyKey = propertyKey;
            this.nestedId = nestedId;
        }

        public Object run(final Arguments arguments, final Locale locale) {
            Object object = arguments.getObject(propertyKey);

            if (object == null) {
                throw new IdCouldNotBeResolvedException(this);
            }

            if (nestedId == null) {
                if (object instanceof LocaleSensitiveObject) {
                    return ((LocaleSensitiveObject) object).apply(locale);
                } else {
                    return object;
                }
            }

            if (!(object instanceof MappedObject)) {
                throw new IdCouldNotBeResolvedException(this);
            }

            try {
                return nestedId.run(((MappedObject) object).map(), locale);
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

    static class If implements AbstractSyntaxTree {

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
        public Object run(final Arguments arguments, final Locale locale) {
            if ((boolean) conditionId.run(arguments, locale)) {
                return ifStatements.run(arguments, locale);
            } else {
                return elseStatements.run(arguments, locale);
            }
        }
    }

    static class Statements implements AbstractSyntaxTree {

        private final List<AbstractSyntaxTree> abstractSyntaxTrees;

        public Statements(final List<AbstractSyntaxTree> abstractSyntaxTrees) {
            this.abstractSyntaxTrees = abstractSyntaxTrees;
        }

        public Statements(final AbstractSyntaxTree... abstractSyntaxTrees) {
            this.abstractSyntaxTrees = Arrays.asList(abstractSyntaxTrees);
        }

        @Override
        public Object run(final Arguments arguments, final Locale locale) {
            StringBuilder sb = new StringBuilder();
            for (AbstractSyntaxTree abstractSyntaxTree : abstractSyntaxTrees) {
                sb.append(abstractSyntaxTree.run(arguments, locale));
            }
            return sb.toString();
        }
    }

    static class Text implements AbstractSyntaxTree {
        private final String value;

        public Text(final String value) {
            this.value = value;
        }

        @Override
        public Object run(final Arguments arguments, final Locale locale) {
            return value;
        }
    }

    static class Empty implements AbstractSyntaxTree {
        @Override
        public Object run(final Arguments arguments, final Locale locale) {
            return "";
        }
    }
}

package org.watertemplate.interpreter.parser;

import org.watertemplate.interpreter.lexer.LexerSymbol;
import org.watertemplate.interpreter.parser.exception.IdCouldNotBeResolvedException;
import org.watertemplate.interpreter.parser.exception.ObjectNotTemplateCollectionException;

import java.util.Arrays;
import java.util.List;

import static org.watertemplate.TemplateMap.*;

public interface AbstractSyntaxTree {

    public Object run(final Arguments arguments);

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
        public Object run(final Arguments arguments) {
            Object collection = collectionId.run(arguments);

            if (!(collection instanceof TemplateCollection)) {
                throw new ObjectNotTemplateCollectionException(collectionId);
            }

            TemplateCollection templateCollection = (TemplateCollection) collection;

            if (templateCollection.iterator() == null || !templateCollection.iterator().hasNext()) {
                return elseStatements.run(arguments);
            }

            StringBuilder sb = new StringBuilder();

            for (final Object item : templateCollection) {
                arguments.addMappedObject(variableName, item, templateCollection.getMapper());
                sb.append(forStatements.run(arguments));
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

        public Object run(final Arguments arguments) {
            Object object = arguments.getObject(propertyKey);

            if (object == null) {
                throw new IdCouldNotBeResolvedException(this);
            }

            if (nestedId == null) {
                return object;
            }

            if (!(object instanceof TemplateObject)) {
                throw new IdCouldNotBeResolvedException(this);
            }

            try {
                return nestedId.run(((TemplateObject) object).map());
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
        public Object run(final Arguments arguments) {
            if ((boolean) conditionId.run(arguments)) {
                return ifStatements.run(arguments);
            } else {
                return elseStatements.run(arguments);
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
        public Object run(final Arguments arguments) {
            StringBuilder sb = new StringBuilder();
            for (AbstractSyntaxTree abstractSyntaxTree : abstractSyntaxTrees) {
                sb.append(abstractSyntaxTree.run(arguments));
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
        public Object run(final Arguments arguments) {
            return value;
        }
    }

    static class Empty implements AbstractSyntaxTree {
        @Override
        public Object run(final Arguments arguments) {
            return "";
        }
    }
}

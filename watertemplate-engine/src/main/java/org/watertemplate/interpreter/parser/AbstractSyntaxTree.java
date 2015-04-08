package org.watertemplate.interpreter.parser;

import org.watertemplate.exception.TemplateException;

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
                throw new TemplateException("Cannot iterate if collection was not added by addCollection method.");
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

        private final AbstractSyntaxTree nestedIdAbstractSyntaxTree;

        public Id(final String propertyKey, final Id nestedIdCommand) {
            this.propertyKey = propertyKey;
            this.nestedIdAbstractSyntaxTree = nestedIdCommand;
        }

        public Id(final String propertyKey) {
            this(propertyKey, null);

        }

        public Object run(final Arguments arguments) {
            Object object = arguments.getObject(propertyKey);

            if (object == null) {
                throw new TemplateException("Property \"" + propertyKey + "\" was not correctly mapped");
            }

            if (nestedIdAbstractSyntaxTree instanceof Id) try {
                return nestedIdAbstractSyntaxTree.run(((TemplateObject) object).map());
            } catch (ClassCastException e) {
                throw new TemplateException("Property \"" + propertyKey + "\" contains no nested properties.");
            }

            return object;
        }

        public String getPropertyKey() {
            return propertyKey;
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
        public Object run(Arguments arguments) {
            return "";
        }
    }
}

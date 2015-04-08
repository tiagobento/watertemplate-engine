package org.watertemplate.interpreter.parser.abs;

import org.watertemplate.TemplateMap;
import org.watertemplate.exception.TemplateException;

import java.util.Arrays;
import java.util.List;

import static org.watertemplate.TemplateMap.Arguments;
import static org.watertemplate.TemplateMap.TemplateObject;

public interface AbstractSyntaxTree {

    public Object run(final Arguments arguments);

    class For implements AbstractSyntaxTree {

        private final String variableName;

        private final Id collectionIdCommand;
        private final AbstractSyntaxTree forStatements;
        private final AbstractSyntaxTree elseStatements;

        public For(final String variableName, final Id collectionIdCommand, final AbstractSyntaxTree forStatements) {
            this.variableName = variableName;
            this.collectionIdCommand = collectionIdCommand;
            this.forStatements = forStatements;
            this.elseStatements = (arguments) -> "";
        }

        public For(final String variableName, final Id collectionIdCommand, final AbstractSyntaxTree forStatements, final AbstractSyntaxTree elseStatements) {
            this.variableName = variableName;
            this.collectionIdCommand = collectionIdCommand;
            this.forStatements = forStatements;
            this.elseStatements = elseStatements;
        }

        @Override
        public Object run(final Arguments arguments) {
            Object collection = collectionIdCommand.run(arguments);

            if (!(collection instanceof TemplateMap.TemplateCollection)) {
                throw new TemplateException("Cannot iterate if collection was not added by addCollection method.");
            }

            TemplateMap.TemplateCollection templateCollection = (TemplateMap.TemplateCollection) collection;

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

    class Id implements AbstractSyntaxTree {

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
    }

    class If implements AbstractSyntaxTree {

        private final AbstractSyntaxTree ifStatements;

        private final AbstractSyntaxTree elseStatements;
        private final Id conditionIdCommand;

        public If(final Id conditionIdCommand, final AbstractSyntaxTree ifStatements) {
            this.ifStatements = ifStatements;
            this.elseStatements = (arguments) -> "";
            this.conditionIdCommand = conditionIdCommand;
        }

        public If(final Id conditionIdCommand, final AbstractSyntaxTree ifStatements, final AbstractSyntaxTree elseStatements) {
            this.ifStatements = ifStatements;
            this.elseStatements = elseStatements;
            this.conditionIdCommand = conditionIdCommand;
        }

        @Override
        public Object run(final Arguments arguments) {
            if ((boolean) conditionIdCommand.run(arguments)) {
                return ifStatements.run(arguments);
            } else {
                return elseStatements.run(arguments);
            }
        }
    }

    class Statements implements AbstractSyntaxTree {

        private final List<AbstractSyntaxTree> abstractSyntaxTrees;

        public Statements(List<AbstractSyntaxTree> abstractSyntaxTrees) {
            this.abstractSyntaxTrees = abstractSyntaxTrees;
        }

        public Statements(AbstractSyntaxTree... abstractSyntaxTrees) {
            this.abstractSyntaxTrees = Arrays.asList(abstractSyntaxTrees);
        }

        @Override
        public Object run(Arguments arguments) {
            StringBuilder sb = new StringBuilder();
            for (AbstractSyntaxTree abstractSyntaxTree : abstractSyntaxTrees) {
                sb.append(abstractSyntaxTree.run(arguments));
            }
            return sb.toString();
        }
    }

    class Text implements AbstractSyntaxTree {
        private final String value;

        public Text(final String value) {
            this.value = value;
        }

        @Override
        public Object run(final Arguments arguments) {
            return value;
        }

    }

    public static class Empty implements AbstractSyntaxTree {
        @Override
        public Object run(Arguments arguments) {
            return "";
        }
    }
}

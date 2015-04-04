package org.watertemplate.interpreter.parser.abs;

import java.util.Map;

class ForCommand implements AbstractSyntaxTree.Command {

    private final String variable;
    private final IdCommand listId;
    private final AbstractSyntaxTree.Command forBlock;
    private final AbstractSyntaxTree.Command elseBlock;

    public ForCommand(final String variable, final IdCommand listId, final AbstractSyntaxTree.Command forBlock) {
        this.variable = variable;
        this.listId = listId;
        this.forBlock = forBlock;
        this.elseBlock = (arguments) -> "";
    }

    public ForCommand(final String variable, final IdCommand listId, final AbstractSyntaxTree.Command forBlock, final AbstractSyntaxTree.Command elseBlock) {
        this.variable = variable;
        this.listId = listId;
        this.forBlock = forBlock;
        this.elseBlock = elseBlock;
    }

    @Override
    public Object run(final Map<String, Object> arguments) {
        Object list = listId.run(arguments);
        StringBuilder sb = new StringBuilder();

        if (list instanceof Iterable && ((Iterable) list).iterator().hasNext()) {
            for (final Object item : (Iterable) list) {
                arguments.put(variable, item);
                sb.append(forBlock.run(arguments));
            }

            arguments.remove(variable);
            return sb.toString();
        } else {
            return elseBlock.run(arguments);
        }
    }
}

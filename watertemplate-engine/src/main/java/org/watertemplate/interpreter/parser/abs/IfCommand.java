package org.watertemplate.interpreter.parser.abs;

import java.util.Map;

class IfCommand implements AbstractSyntaxTree.Command {

    private final AbstractSyntaxTree.Command ifStatements;
    private final AbstractSyntaxTree.Command elseStatements;
    private final IdCommand conditionIdCommand;

    public IfCommand(final IdCommand conditionIdCommand, final AbstractSyntaxTree.Command ifStatements) {
        this.ifStatements = ifStatements;
        this.elseStatements = (arguments) -> "";
        this.conditionIdCommand = conditionIdCommand;
    }

    public IfCommand(final IdCommand conditionIdCommand, final AbstractSyntaxTree.Command ifStatements, final AbstractSyntaxTree.Command elseStatements) {
        this.ifStatements = ifStatements;
        this.elseStatements = elseStatements;
        this.conditionIdCommand = conditionIdCommand;
    }

    @Override
    public Object run(final Map<String, Object> arguments) {
        if ((boolean) conditionIdCommand.run(arguments)) {
            return ifStatements.run(arguments);
        } else {
            return elseStatements.run(arguments);
        }
    }
}

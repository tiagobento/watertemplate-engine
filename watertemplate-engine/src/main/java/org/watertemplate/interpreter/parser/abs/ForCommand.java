package org.watertemplate.interpreter.parser.abs;

import static org.watertemplate.TemplateMap.Arguments;
import static org.watertemplate.TemplateMap.TemplateCollection;

class ForCommand implements AbstractSyntaxTree.Command {

    private final String variableName;
    private final IdCommand collectionId;
    private final AbstractSyntaxTree.Command forBlock;
    private final AbstractSyntaxTree.Command elseBlock;

    public ForCommand(final String variableName, final IdCommand collectionId, final AbstractSyntaxTree.Command forBlock) {
        this.variableName = variableName;
        this.collectionId = collectionId;
        this.forBlock = forBlock;
        this.elseBlock = (arguments) -> "";
    }

    public ForCommand(final String variableName, final IdCommand collectionId, final AbstractSyntaxTree.Command forBlock, final AbstractSyntaxTree.Command elseBlock) {
        this.variableName = variableName;
        this.collectionId = collectionId;
        this.forBlock = forBlock;
        this.elseBlock = elseBlock;
    }

    @Override
    public Object run(final Arguments arguments) {
        Object collection = collectionId.run(arguments);


        if (collection instanceof TemplateCollection) {
            StringBuilder sb = new StringBuilder();
            TemplateCollection templateCollection = (TemplateCollection) collection;

            for (final Object item : templateCollection) {
                arguments.addMappedObject(variableName, item, templateCollection.getMapper());
                sb.append(forBlock.run(arguments));
            }

            arguments.remove(variableName);
            return sb.toString();
        } else {
            return elseBlock.run(arguments);
        }
    }
}

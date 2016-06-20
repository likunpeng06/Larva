package com.runbox.debug.parser.statement.node;

/**
 * Created by qstesiro on 2016/5/21.
 */
public class CommandNode extends Node {

    public CommandNode(String command) {
        super(command);
    }

    BlockNode block = null;

    public BlockNode block(BlockNode block) {
        BlockNode previous = this.block;
        this.block = block;
        return previous;
    }

    public BlockNode block() {
        return block;
    }
}

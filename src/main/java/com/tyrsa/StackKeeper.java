package com.tyrsa;

import java.util.ArrayList;
import java.util.Stack;

class StackKeeper {
    private Stack<JSONTree> stack;

    private static StackKeeper keeper;

    private StackKeeper() {
        stack = new Stack<>();
    }

    static StackKeeper instance() {
        if(keeper == null){
            keeper = new StackKeeper();
        }
        return keeper;
    }

    void push(JSONTree item){
        stack.push(item);
    }

    JSONTree[] pop(){
        if(stack.size() == 0){
            return null;
        }
        JSONTree pop = stack.pop();
        JSONTree parent = pop.getParent();
        ArrayList<JSONTree> dirs = parent.getDirs();
        if(!dirs.get(0).getName().equals("...")){
            dirs.add(0, new JSONTree("...", true, parent));
        }
        return dirs.toArray(new JSONTree[]{});
    }
    void popNode(){
        stack.pop();
    }
}

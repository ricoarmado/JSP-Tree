package com.tyrsa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class StackKeeper {
    private Stack<JSONTree> stack;

    private static StackKeeper keeper;

    private StackKeeper() {
        stack = new Stack<>();
    }

    public static StackKeeper instance() {
        if(keeper == null){
            keeper = new StackKeeper();
        }
        return keeper;
    }

    public void push(JSONTree item, JSONTree parent){

        stack.push(item);
    }

    public JSONTree[] pop(){
        if(stack.size() == 0){
            return null;
        }
        JSONTree pop = stack.pop();
        JSONTree parent = pop.getParent();
        if(pop.getName().equals("root")){

        }
        ArrayList<JSONTree> dirs = parent.getDirs();
        dirs.add(0, new JSONTree("...", true, parent));
        return dirs.toArray(new JSONTree[]{});
    }
    public void popNode(){
        stack.pop();
    }
}

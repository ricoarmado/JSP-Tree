package com.tyrsa;

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

    public void push(JSONTree item){
        stack.push(item);
    }

    public JSONTree pop(){
        return stack.pop();
    }
}

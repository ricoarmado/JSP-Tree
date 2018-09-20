package com.tyrsa;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class JSONTree {

    private String name;
    private boolean isDirectory;
    private ArrayList<JSONTree> dirs;
    private JSONTree parent;


    public JSONTree() {

    }

    public JSONTree(String name, boolean isDirectory, JSONTree parent) {
        this.name = name;
        this.isDirectory = isDirectory;
        this.parent = parent;
        this.dirs = new ArrayList<>();
    }

    @Override
    public String toString() {
        JSONObject obj = new JSONObject();
        obj.put("name", name);
        obj.put("directory", isDirectory);
        JSONArray array = new JSONArray();
        for(JSONTree tmp : dirs){
            array.add(tmp.toString());
        }
        obj.put("files", array);
        return obj.toJSONString();
    }
    public static JSONTree parseFromJSON(Object json, JSONTree parent){
        JSONTree result = new JSONTree();
        JSONObject tmp = (JSONObject) json;
        result.name = (String) tmp.get("name");
        result.isDirectory = (boolean) tmp.get("directory");
        result.dirs = parseFromJSONArray(tmp.get("files"), result);
        result.parent = parent;
        if(result.dirs == null) {
            result.dirs = new ArrayList<>();
        }
        return result;
    }
    public static ArrayList<JSONTree> parseFromJSONArray(Object json, JSONTree parent){
        if(json != null){
            ArrayList<JSONTree> result = new ArrayList<>();
            JSONArray array = (JSONArray) json;
            for(Object obj : array){
                JSONTree node = parseFromJSON(obj, parent);
                result.add(node);
            }
            return result;
        }
        else {
            return null;
        }
    }

    public JSONTree[] search(String fileName, JSONTree parent){
        if(this.name.equals(fileName)){
            ArrayList<JSONTree> tmp = new ArrayList<>(dirs);
            if(!tmp.get(0).getName().equals("..."))
            tmp.add(0,new JSONTree("...", true,parent));
            return tmp.toArray(new JSONTree[0]);
        }
        else{
            for (JSONTree dir : dirs) {
                if(dir.isDirectory){
                    StackKeeper.instance().push(dir);
                    JSONTree[] search = dir.search(fileName, dir);
                    if(search != null){
                        return search;
                    }
                    else{
                        StackKeeper.instance().popNode();
                    }
                }
            }
        }
        return null;
    }

    public ArrayList<JSONTree> getDirs() {
        return dirs;
    }

    public String getName() {
        return name;
    }

    public boolean isDirectory() {
        return isDirectory;
    }

    public JSONTree getParent() {
        return parent;
    }

    public void setParent(JSONTree parent) {
        this.parent = parent;
    }
}

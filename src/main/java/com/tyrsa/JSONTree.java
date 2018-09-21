package com.tyrsa;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

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
        return obj.toString();
    }
    public static JSONTree parseFromJSON(Object json, JSONTree parent) throws ParseException {
        JSONTree result = new JSONTree();
        String valueOf = String.valueOf(json);
        Object parse = new JSONParser().parse(valueOf);
        JSONObject tmp = (JSONObject) parse;
        result.name = (String) tmp.get("name");
        result.isDirectory = (boolean) tmp.get("directory");
        result.dirs = parseFromJSONArray(tmp.get("files"), result);
        result.parent = parent;
        if(result.dirs == null) {
            result.dirs = new ArrayList<>();
        }
        return result;
    }
    public static ArrayList<JSONTree> parseFromJSONArray(Object json, JSONTree parent) throws ParseException {
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
            if(tmp.size() > 0 && !tmp.get(0).getName().equals("...") || tmp.size() == 0)
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

    public void addChild(JSONTree item){
        dirs.add(item);
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

    public void save() throws FileNotFoundException {
        String s = toString();
        PrintWriter writer = new PrintWriter("C:\\tree.json");
        writer.write(s);
        writer.close();
    }

    public void delete(String deleteFile) {
        for (JSONTree dir : dirs) {
            if(dir.getName().equals(deleteFile)){
                dirs.remove(dir);
                break;
            }
            if(dir.isDirectory()){
                dir.delete(deleteFile);
            }
        }
    }
}

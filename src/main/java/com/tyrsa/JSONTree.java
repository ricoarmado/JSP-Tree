package com.tyrsa;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class JSONTree {

    private String name;
    private boolean isDirectory;
    private ArrayList<JSONTree> dirs;

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
    public static JSONTree parseFromJSON(Object json){
        JSONTree result = new JSONTree();
        JSONObject tmp = (JSONObject) json;
        result.name = (String) tmp.get("name");
        result.isDirectory = (boolean) tmp.get("directory");
        result.dirs = parseFromJSONArray(tmp.get("files"));
        if(result.dirs == null) {
            result.dirs = new ArrayList<>();
        }
        return result;
    }
    public static ArrayList<JSONTree> parseFromJSONArray(Object json){
        if(json != null){
            ArrayList<JSONTree> result = new ArrayList<>();
            JSONArray array = (JSONArray) json;
            for(Object obj : array){
                JSONTree node = parseFromJSON(obj);
                result.add(node);
            }
            return result;
        }
        else {
            return null;
        }
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
}

package com.tyrsa;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class TreeRoot {
	
	private static JSONTree root;
	private final static String PATH = "C:\\tree.json";
	
	public static JSONTree[] getRoot() {
		if(root == null) {
			JSONParser parser = new JSONParser();
			try {
				File f = new File(PATH);
				if(f.exists()) {
					Object parsed = parser.parse(new FileReader(PATH));
					root = JSONTree.parseFromJSON(parsed);
				}
				else {
				    root = new JSONTree();
                }
			} catch (IOException | ParseException e) {
				e.printStackTrace();
			}
		}
		return new JSONTree[]{root};
	}
	public static void addItem(String fileName, boolean isDir){

    }

    public static JSONTree[] openFolder(String fileName){
	    return root.search(fileName);
    }
}

package com.tyrsa;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class TreeRoot {
	
	private static JSONTree root;
	private static JSONTree[] current;
	private final static String PATH = System.getProperty("user.home") + "/tree.json";
    private static JSONTree cuttedNode;
	
	public static JSONTree[] getRoot() {
		if(root == null) {
			JSONParser parser = new JSONParser();
			try {
				File f = new File(PATH);
				if(f.exists()) {
					Object parsed = parser.parse(new FileReader(PATH));
					root = JSONTree.parseFromJSON(parsed , null);
				}
				else {
				    root = new JSONTree();
				    root.save();
                }
			} catch (IOException | ParseException e) {
				e.printStackTrace();
			}
			current = new JSONTree[]{root};
		}
		return new JSONTree[]{root};
	}
	public static void addItem(String fileName, boolean isDir) throws FileNotFoundException {
        JSONTree parent;
        if(!current[0].equals(root)){
            parent = current[0].getParent();
        }else {
            parent = root;
        }
        JSONTree tmp = new JSONTree(fileName, isDir, parent);
        parent.addChild(tmp);
        root.save();
    }

    public static JSONTree[] openFolder(String fileName){
        if(fileName.equals("...")){
            current = StackKeeper.instance().pop();
            if(current == null){
                current = new JSONTree[]{root};
            }
        }else {
            current = root.search(fileName, root);
        }
        return current;
    }
    public static JSONTree[] getCurrentObject(){
        return current == null ? new JSONTree[]{root} : current;
    }

    public static void edit(String editName, String newName, boolean isDirectory) throws FileNotFoundException {
        for (JSONTree tree : current) {
            if(tree.getName().equals(editName)){
                if(!isDirectory && tree.getDirs().size() != 0){
                    tree.removeDirs();
                }
                tree.setDirectory(isDirectory);
                tree.setName(newName);
                break;
            }
        }
        root.save();
    }

    public static void setCutElem(String cutElem) {
        for (JSONTree tree : current) {
            if(tree.getName().equals(cutElem)){
                cuttedNode = tree;
                break;
            }
        }
    }

    public static boolean pasteElem() throws FileNotFoundException {
        if(cuttedNode == null){
            return false;
        }
        JSONTree newParent = current[0].getParent();
        JSONTree oldParent = cuttedNode.getParent();
        if(cuttedNode.equals(newParent)){ // Запрет копирования папки в себя
            return false;
        }
        oldParent.delete(cuttedNode.getName());
        newParent.addChild(cuttedNode);
        cuttedNode.setParent(newParent);
        cuttedNode = null;
        root.save();
        return true;
    }
}

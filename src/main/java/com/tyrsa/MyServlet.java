package com.tyrsa;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class MyServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String button = req.getParameter("button");
        String selectedFolder = req.getParameter("folder_name");
        String deleteFile = req.getParameter("del_name");
        String editName = req.getParameter("edit_name");
        String namefield;
        String returnMessage = "";
        boolean isDirectory;
        if(button == "add_button"){
            namefield = req.getParameter("name_field");
            isDirectory = req.getParameter("file_type").equals("directory");
            if(namefield != ""){
                TreeRoot.addItem(namefield, isDirectory);
            }
            else{
                req.setAttribute("getAlert", "Не выбрано имя для файла");
                req.getRequestDispatcher("/index.jsp").forward(req, resp);
            }
        }
        else if(editName != null){
            namefield = req.getParameter("new_name");
            isDirectory = req.getParameter("dir").equals("true");
            TreeRoot.edit(editName, namefield, isDirectory);
        }
        else if(selectedFolder != null){
            JSONTree[] items = TreeRoot.openFolder(selectedFolder);
            req.setAttribute("message", items); // Make available by ${message} in request scope.
        }
        else if(deleteFile != null){
            if(deleteFile != "root"){
                JSONTree tree = TreeRoot.getRoot()[0];
                tree.delete(deleteFile);
                tree.save();
            }
        }
        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }
}

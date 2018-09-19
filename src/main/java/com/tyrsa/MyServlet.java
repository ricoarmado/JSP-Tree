package com.tyrsa;

import sun.reflect.generics.tree.Tree;

import java.io.IOException;
import java.lang.reflect.Array;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class MyServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String button = req.getParameter("button");
        String selectedFolder = req.getParameter("folder_name");
        String namefield;
        String returnMessage = "";
        boolean isDirectory;
        if(button != null){
            switch (button){
                case "add_button":
                    namefield = req.getParameter("name_field");
                    isDirectory = req.getParameter("file_type").equals("directory");
                    TreeRoot.addItem(namefield, isDirectory);
                    break;
            }
        }
        else if(selectedFolder != null){
            JSONTree[] items = TreeRoot.openFolder(selectedFolder);

            req.setAttribute("message", items); // Make available by ${message} in request scope.
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
        }
    }


}

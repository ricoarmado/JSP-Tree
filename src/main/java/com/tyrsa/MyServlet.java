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
        String namefield;
        String returnMessage = "";
        boolean isDirectory;
        if(button != null){
            switch (button){
                case "add_button":
                    namefield = req.getParameter("name_field");
                    isDirectory = req.getParameter("file_type").equals("directory");
                    if(namefield != ""){
                        TreeRoot.addItem(namefield, isDirectory);
                    }
                    else{
                        req.setAttribute("getAlert", "Не выбрано имя для файла");
                        req.getRequestDispatcher("/index.jsp").forward(req, resp);
                    }
                    break;
            }
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
        }
        else if(selectedFolder != null){
            JSONTree[] items = TreeRoot.openFolder(selectedFolder);

            req.setAttribute("message", items); // Make available by ${message} in request scope.
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
        }
    }


}

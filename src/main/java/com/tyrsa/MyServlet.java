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
        String selectedName = req.getParameter("selected_name");
        String pasteName = req.getParameter("paste_name");
        String namefield;
        String returnMessage = "";
        boolean isDirectory;
        if(button == "add_button"){
            namefield = req.getParameter("name_field");
            if(!namefield.equals("root")) {
                isDirectory = req.getParameter("file_type").equals("directory");
                if (namefield != "") {
                    TreeRoot.addItem(namefield, isDirectory);
                } else {
                    req.setAttribute("getAlert", "Не выбрано имя для файла");
                    req.getRequestDispatcher("/index.jsp").forward(req, resp);
                }
            }
            else {
                req.setAttribute("getAlert", "Имя Root зарезервировано");
            }
        }
        else if(editName != null){
            namefield = req.getParameter("new_name");
            if(!namefield.equals("root")) {
                isDirectory = req.getParameter("dir").equals("true");
                TreeRoot.edit(editName, namefield, isDirectory);
            }
            else {
                req.setAttribute("getAlert", "Имя Root зарезервировано");
            }
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
            else {
                req.setAttribute("getAlert", "Запрещено удалять папку Root");
            }
        }
        else if(selectedName != null){
            if(!selectedName.equals("root")) {
                TreeRoot.setCutElem(selectedName);
                req.setAttribute("getAlert", "Теперь перейдите в папку и нажмите `Вставить`");
            }
            else {
                req.setAttribute("getAlert", "Запрещено вырезать Root");
            }
        }
        else if(pasteName != null){
            boolean result = TreeRoot.pasteElem();
            if(!result){
                req.setAttribute("getAlert", "Файл не был вставлен. Возможно, вы не выбрали файл");
            }
        }
        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }
}

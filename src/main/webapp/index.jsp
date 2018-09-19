<%@page import="com.tyrsa.JSONTree"%>
<%@page import="com.tyrsa.TreeRoot"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
    <link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>
    <script>
        var clickHandler = function(e){
            var p = e.target;
            while (p != null && !p.classList.contains("panel-body")){
                p = p.parentElement;
            }
            var selected = document.getElementsByClassName("selected");
            for (var i = 0; i < selected.length; i++){
                selected[i].classList.remove("selected");
            }
            p.classList.add("selected");
        };
    </script>
    Hello World!<br/>
    <div class="file-list" id="file-list"></div>
        <script>
            var list = document.getElementById("file-list");
            list.innerHTML = ""
        </script>
        <%
           JSONTree[] node = TreeRoot.getRoot();
           for(JSONTree obj : node){
            String name = obj.getName();
            boolean isDir = obj.isDirectory();
        %>
            <script>
            var panelDiv = document.createElement("div");
            panelDiv.classList.add("panel");
            var panelBody = document.createElement("div");
            panelBody.classList.add("panel-body");
            var p = document.createElement("div");
            p.setAttribute("style", "font-weight: bold");
            p.innerText = "<%=name%>";
            panelBody.onclick = clickHandler;

            var img = document.createElement("img");
            if("<%=isDir%>" === true){
                img.setAttribute("src","/img/folder.png");
            }
            else{
                img.setAttribute("src","/img/docs.png");
            }
            panelBody.appendChild(img);
            panelBody.appendChild(p);
            panelDiv.appendChild(panelBody);
            list.appendChild(panelDiv);
            </script>
         <%
            }
         %>




    <br><br><br><br><br>
    <form action="/tree_action" method="post">
        Имя для добавления/редактирования<br>
        <input type="text" name="name_field"><br>
        <input type="radio" name="file_type" value="directory">Каталог<br>
        <input type="radio" name="file_type" value="file">Файл<br>
        <button type="submit" name="button" value="add_button">Добавить элемент</button>
        <button type="submit" name="button" value="edit_button">Изменить элемент</button>
        <button type="submit" name="button" value="remove_buton">Удалить элемент</button>
    </form>
</body>
</html>
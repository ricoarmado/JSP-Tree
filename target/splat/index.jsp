<%@page import="org.json.simple.JSONObject"%>
<%@page import="org.json.simple.JSONArray"%>
<%@page import="com.tyrsa.JSONTree"%>
<%@page import="com.tyrsa.TreeRoot"%>
<%@ page import="java.util.ArrayList" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
</head>
<body>
Hello World!<br/>
<%!
    String parseTree(JSONTree root){
        String result = "";
        result += "<ul><br><li>";
        result += "<b>" + root.getName() + "</b><br>";
        ArrayList<JSONTree> dirs = root.getDirs();
        for(JSONTree tmp : dirs){
            result += parseTree(tmp);
        }
        result += "</li><br></ul>";
        return result;
    }
%>
<%
    JSONTree root = TreeRoot.getRoot();
    String result = parseTree(root);
    out.println(result);
%>
    <form action="${pageContext.request.contextPath}/tree_action" method="post" >
        Имя для добавления<br>
        <input type="text" name="name_field"><br>
        <input type="radio" name="file_type" value="directory">Каталог<br>
        <input type="radio" name="file_type" value="file">Файл<br>
        <button type="submit" name="button" value="add_button">Добавить элемент</button>
    </form>
</body>
</html>
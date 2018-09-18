<%@page import="org.json.simple.JSONObject"%>
<%@page import="org.json.simple.JSONArray"%>
<%@page import="com.tyrsa.JSONTree"%>
<%@page import="com.tyrsa.TreeRoot"%>
<%@ page import="java.util.ArrayList" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
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
</body>
</html>
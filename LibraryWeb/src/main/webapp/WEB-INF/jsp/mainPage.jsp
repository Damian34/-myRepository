<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
           prefix="security"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="<c:url value='/resources/bootstrap.css'/>"  rel="stylesheet" type="text/css"/>
        <title>Strona główna</title>
    </head>
    <body>
        <div class="backFirst">
            <h2>Strona główna</h2>
            <security:authorize access="isAnonymous()">
                <form action="userLogged">
                    <input type="submit" value="Logowanie" class="button1"/>
                </form>
            </security:authorize>
            <security:authorize access="isAuthenticated()">
                <h3>
                    Hej
                    <security:authentication property="principal.username" />
                </h3>
                <form action="userLogged">
                    <input type="submit" value="Opcje" class="button1"/>
                </form></br>
                <a href="logout">Logout</a></br>
            </security:authorize>
        </div>
    </body>
</html>

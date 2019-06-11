<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
           prefix="security"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href='<c:url value="/resources/bootstrap.css"/>' rel="stylesheet" type="text/css">
        <title>Dodaj użytkownika</title>
    </head>
    <body>
        <div class="backFirst">
            <h2>Dodaj użytkownika</h2>
            <form action="addUser" method="post">
                <div class="row">
                    <div class="col1">Login:</div>
                    <div class="col2">
                        <input type="text" name="login" placeholder="login..">
                    </div>
                </div>
                <div class="row">
                    <div class="col1">Hasło:</div>
                    <div class="col2">
                        <input type="text" name="password" placeholder="hasło..">
                    </div>
                </div>
                <div class="row">
                    <div class="col1">Imie:</div>
                    <div class="col2">
                        <input type="text" name="name" placeholder="imie..">
                    </div>
                </div>
                <div class="row">
                    <div class="col1">Nazwisko:</div>
                    <div class="col2">
                        <input type="text" name="surname" placeholder="nazwisko..">
                    </div>
                </div>
                <input type="submit" value="OK" class="button1">
            </form>
            <form action="/Library/">
                <input type="submit" value="Cofnij" class="button1"/>
            </form>              
            <div class="cls">${addUserResponse}</div>
        </div>
    </body>
</html>

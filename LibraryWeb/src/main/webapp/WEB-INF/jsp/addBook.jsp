<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
           prefix="security"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href='<c:url value="/resources/bootstrap.css"/>' rel="stylesheet" type="text/css">
        <title>Dodaj książke</title>
    </head>
    <body>
        <div class="backFirst">
            <h2>Dodaj książke</h2>
            <form action="addBook" method="post">
                <div class="row">
                    <div class="col1">Tytuł:</div>
                    <div class="col2">
                        <input type="text" name="title" placeholder="tytuł..">
                    </div>
                </div>
                <div class="row">
                    <div class="col1">Autor:</div>
                    <div class="col2">
                        <input type="text" name="author" placeholder="autor..">
                    </div>
                </div>                
                <div class="row">
                    <div class="col1">Isbn;</div>
                    <div class="col2">
                        <input type="text" name="isbn" placeholder="isbn..">
                    </div>
                </div>                
                <div class="row">
                    <div class="col1">Rok;</div>
                    <div class="col2">
                        <input type="text" name="year" placeholder="year..">
                    </div>
                </div>
                <input type="submit" value="OK" class="button1">
            </form>
            <form action="/Library/">
                <input type="submit" value="Cofnij" class="button1"/>
            </form>     
            <div class="cls">${addBookResponse}</div>
        </div>
    </body>
</html>

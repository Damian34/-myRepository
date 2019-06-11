<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/security/tags"
          prefix="security"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href='<c:url value="/resources/bootstrap.css"/>' rel="stylesheet" type="text/css">   
        <title>Biblioteka</title>
    </head>
    <body>
        <div class="backFirst">
            <form action="/Library/">
                <input type="submit" value="Cofnij" class="button1"/>
            </form>     
            <form action="addUserPath">
                <input type="submit" value="Dodaj użytkownika" class="button1"/>
            </form>
            <form action="addBookPath">
                <input type="submit" value="Dodaj książke" class="button1"/>
            </form>
            <form action="getBooks">
                <input type="submit" value="Pokaż książki" class="button1"/>
            </form>
            <form action="getUsers">
                <input type="submit" value="Pokaż użytkowników" class="button1"/>
            </form>
            <div class="cls">${menuUserResponse}</div> 
        </div>
        <div class="backSecond">
            <c:if test="${not empty bookList}">    
                <h3 class="cls">Wszystkie książki</h3>
                <table id="tables">
                    <tr>
                        <th>id</th>
                        <th>tytuł</th>
                        <th>autor</th>
                        <th>rok wydania</th>
                        <th>ISBN</th>
                        <th>status</th>
                    </tr>
                    <c:forEach items="${bookList}" var="book">
                        <tr>
                            <td>${book.id}</td>
                            <td>${book.title}</td>
                            <td>${book.author}</td>
                            <td>${book.year}</td>
                            <td>${book.isbn}</td>
                            <td>${book.status}</td>
                        </tr>
                    </c:forEach>
                </table>
            </c:if>
            <c:if test="${not empty userList}">                
                <h3 class="cls">Wybierz użytkownika</h3>
                <table id="tables">
                    <tr>
                        <th>id</th>
                        <th>imie</th>
                        <th>nazwisko</th>
                        <th>liczba wypożyczonych książek</th>
                    </tr>                  
                    <c:forEach items="${userList}" var="user"> 
                        <form action="menuUser" method="post">
                            <tr>
                                <td>
                                    <input type="submit" value="${user.id}" class="cellButton"/>
                                </td>
                                <td>                                    
                                    <input type="submit" value="${user.name}" class="cellButton"/>                                    
                                </td>
                                <td>
                                    <input type="submit" value="${user.surname}" class="cellButton"/>    
                                </td>
                                <td>
                                    <input type="submit" value="${fn:length(user.borrowedBookId)}" class="cellButton"/>    
                                </td>
                            </tr>
                            <input type="hidden" name="idUser" value="${user.id}"/>
                        </form>
                    </c:forEach>
                </table>
            </form>  
        </c:if>           
    </div>
</body>
</html>

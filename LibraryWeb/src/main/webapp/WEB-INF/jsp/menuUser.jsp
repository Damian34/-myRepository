<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/security/tags"
          prefix="security"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href='<c:url value="/resources/bootstrap.css"/>' rel="stylesheet" type="text/css">
        <title>Menu użytkownika</title>
    </head>
    <body>        
        <security:authorize access="isAuthenticated()">
            <div class="backFirst">
                <form action="/Library/">
                    <input type="submit" value="Cofnij" class="button1"/>
                </form>            
                <form action="getAvailableBook">
                    <input type="submit" value="Wyświetl dostępne książki" class="button1"/>
                </form>            
                <form action="getBoorowedBook">
                    <input type="submit" value="Pokaż posiadane książki" class="button1"/>
                </form>               
                <div class="cls">${borrowBookResponse}</div>
                <div class="cls">${returnBookResponse}</div>
            </div>
            <div class="backFirst">
                <c:if test="${not empty availableBookList}">                                     
                    <security:authorize access="hasRole('ADMIN')">
                        <h3 class="cls">Dostępne książke</h3>               
                    </security:authorize>                    
                    <security:authorize access="hasRole('USER')">
                        <h3 class="cls">Wypożycz książke</h3>                    
                    </security:authorize>
                    <table id="tables">
                        <tr>
                            <th>nr</th>
                            <th>tytuł</th>
                            <th>autor</th>
                            <th>rok wydania</th>
                            <th>ISBN</th>
                        </tr>
                        <c:forEach items="${availableBookList}" var="book">
                            <security:authorize access="hasRole('ADMIN')">
                                <tr>
                                    <td>
                                        <input type="submit" value="${book.id}" class="cellButton"/>
                                    </td>
                                    <td>
                                        <input type="submit" value="${book.title}" class="cellButton"/>
                                    </td>
                                    <td>
                                        <input type="submit" value="${book.author}" class="cellButton"/>
                                    </td>
                                    <td>
                                        <input type="submit" value="${book.year}" class="cellButton"/>
                                    </td>
                                    <td>
                                        <input type="submit" value="${book.isbn}" class="cellButton"/>
                                    </td>                                
                                </tr>
                            </security:authorize>                            
                            <security:authorize access="hasRole('USER')">
                                <form action="borrowBook" method="post">
                                    <tr>
                                        <td>
                                            <input type="submit" value="${book.id}" class="cellButton"/>
                                        </td>
                                        <td>
                                            <input type="submit" value="${book.title}" class="cellButton"/>
                                        </td>
                                        <td>
                                            <input type="submit" value="${book.author}" class="cellButton"/>
                                        </td>
                                        <td>
                                            <input type="submit" value="${book.year}" class="cellButton"/>
                                        </td>
                                        <td>
                                            <input type="submit" value="${book.isbn}" class="cellButton"/>
                                        </td>                                
                                    </tr>
                                    <input type="hidden" name="idBook" value="${book.id}"/>
                                </form>
                            </security:authorize>
                        </c:forEach>
                    </table>            
                </c:if>                        
                <c:if test="${not empty getBoorowedBookList}">                    
                    <security:authorize access="hasRole('ADMIN')">
                        <h3 class="cls">Zwróć książke</h3>                        
                    </security:authorize>                    
                    <security:authorize access="hasRole('USER')">
                        <h3 class="cls">Posiadane książke</h3>                        
                    </security:authorize>
                    <table id="tables">
                        <tr>
                            <th>nr</th>
                            <th>tytuł</th>
                            <th>autor</th>
                            <th>rok wydania</th>
                            <th>ISBN</th>
                        </tr>
                        <c:forEach items="${getBoorowedBookList}" var="boorowedBook">
                            <security:authorize access="hasRole('ADMIN')">
                                <form action="returnBook" method="post">
                                    <tr>
                                        <td>
                                            <input type="submit" value="${boorowedBook.id}" class="cellButton"/>
                                        </td>
                                        <td>
                                            <input type="submit" value="${boorowedBook.title}" class="cellButton"/>
                                        </td>
                                        <td>
                                            <input type="submit" value="${boorowedBook.author}" class="cellButton"/>
                                        </td>
                                        <td>
                                            <input type="submit" value="${boorowedBook.year}" class="cellButton"/>
                                        </td>
                                        <td>
                                            <input type="submit" value="${boorowedBook.isbn}" class="cellButton"/>
                                        </td>
                                    </tr>
                                    <input type="hidden" name="idBook" value="${boorowedBook.id}"/>                                
                                </form>
                            </security:authorize>
                            <security:authorize access="hasRole('USER')">
                                <tr>
                                    <td>
                                        <input type="submit" value="${boorowedBook.id}" class="cellButton"/>
                                    </td>
                                    <td>
                                        <input type="submit" value="${boorowedBook.title}" class="cellButton"/>
                                    </td>
                                    <td>
                                        <input type="submit" value="${boorowedBook.author}" class="cellButton"/>
                                    </td>
                                    <td>
                                        <input type="submit" value="${boorowedBook.year}" class="cellButton"/>
                                    </td>
                                    <td>
                                        <input type="submit" value="${boorowedBook.isbn}" class="cellButton"/>
                                    </td>
                                </tr>
                            </security:authorize>
                        </c:forEach>
                    </table>       
                </c:if>                
            </div>
        </security:authorize>
    </body>
</html>

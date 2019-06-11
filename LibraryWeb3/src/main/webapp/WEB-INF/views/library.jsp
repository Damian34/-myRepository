<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>HelloWorld page</title>
    </head>
    <body>
        Greeting : 
        ${greeting}
        ${sayAgain}

        <form action="add" method="post">
            <input type="text" name="nr1"><br/>
            <input type="text" name="nr2"><br/>            
            <input type="submit" value="OK"><br/>
            ${added}<br/>
        </form>          
    </body>
</html>
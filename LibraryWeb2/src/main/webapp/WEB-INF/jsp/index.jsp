<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Welcome to Spring Web MVC project</title>
    </head>

    <body>
        <p>hello</p>        
        <p>${say}</p>
        <p>${sayAgain}</p>
                
        <form action="add" method="post">
            <input type="text" name="nr1"><br/>
            <input type="text" name="nr2"><br/>            
            <input type="submit" value="OK"><br/>
            ${added}<br/>
        </form>
    </body>
</html>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Hash-it!</title>
    </head>
    <body>
        <img src="images/hash.gif"/>
        <h1>Hash-it!&trade; Hashes-To-Date</h1>
        <p>
            What follows is the full list of collected
            hashes. It'll get big fast!
        </p>
        <ol>
        <c:forEach var="hash" items="${hashes}">
            <li><tt>${hash}</tt></li>
        </c:forEach>    
        </ol>
        <p><a href="main?action=hash">Collect More Hashes!</a></p>
    </body>
</html>

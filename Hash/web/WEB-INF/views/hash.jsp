<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Hash-it!</title>
    </head>
    <body>
        <img src="images/hash.gif"/>
        <h1>Welcome to Hash-it!&trade;</h1>
        <p>
            Enter some text below and see the resulting
            MD-5 and SHA-256 hashes of that string! Whee!
        </p>
        <c:if test="${not empty md5}">
        <p>
            MD-5:<tt>${md5}</tt><br/>
            SHA-256:<tt>${sha256}</tt><br/>
            <c:if test="${hashedBefore}">
            That text has been hashed before.
            </c:if>
        </p>
        </c:if>
        <form method="POST" action="main">
            <input type="hidden" name="action" value="hash"/>
            <textarea rows="8" cols="30" name="text"></textarea>
            <p><input type="submit"/></p>
        </form>
        <br/>
        <form method="POST" action="main">
            <input type="hidden" name="action" value="clear"/>
            <input type="submit" value="Clear All Stored Hashes"/>
        </form>
        <p><a href="main?action=view">View Collected Hashes</a></p>
    </body>
</html>
<!--
Write a Web Application called Hash that
1) presents the user with a text field in which to type a String
2) generates an MD5 and a SHA-256 hash of the incoming text
3) displays the resulting hashes in the view above the text field
4) stores all the hashes it has produced in context scope
5) informs the user when their String has been hashed before
6) has a button that causes the list of stored hashes to empty
7) has a link to a page that displays all the collected hashes

Hint: Java has a MessageDigest class for hashing and Apache Commons does, too

-->

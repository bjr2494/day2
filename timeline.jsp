<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Hubbub&trade; &raquo; Timeline</title>
    </head>
    <body>
    	<p><center><img src="images/hubbub_logo.png"/></center></p>
        <h1>
            Welcome to Hubbub&trade;<c:if test="${not empty user}">, ${user}</c:if>!
        </h1>
        <c:choose>
            <c:when test="${not empty user}">
                <a href="main?action=post">Post Yer Hack Status</a> |
                <a href="main?action=wall&for=${user}">Show Me My Wall</a> |
                <a href="main?action=logout">Log Me Out</a>
            </c:when>
            <c:otherwise>
                <a href="main?action=login">Log Me In!</a> |
                <a href="main?action=join">Sign Me Up!</a>
            </c:otherwise>
        </c:choose>
        <c:if test="${not empty user}">
            <form method="POST" action="main">
                <input type="hidden" name="action" value="timeline"/>
                <p>Choose how many posts you'd like to see per page:</p>
                <select name="pageSize">
                    <c:forEach var="option" items="${options}">
                    <option value="${option}"> </option><br/>
                    </c:forEach>
                </select>  
            </form>
        </c:if>
        <c:choose>
            <c:when test="${not empty user}">
                <h2>Here's page ${currentPage} of what our users are hackin' on:</h2>
            </c:when>
            <c:otherwise>
                <h2>Here's what our users are hackin' on:</h2>
            </c:otherwise>
        </c:choose>
        <ul>
        <c:forEach var="post" items="${posts}">
            <li>
                Posted by
                <span class="author" title="${post.author.joinDate}">
                    <c:choose>
                        <c:when test="${not empty user}">
                            <a href="main?action=profile&for=${post.author}">
                                ${post.author}
                            </a>
                        </c:when>
                        <c:otherwise>
                            ${post.author}
                        </c:otherwise>
                    </c:choose>
                </span>
                <span class="postdate">${post.postDate}</span>
                <p class="content">${post.content}</p>
            </li>
        </c:forEach>
        </ul>
        <a href="main?action=timeline&page=start">Top of Timeline</a>
        <c:if test="${more}">
        | <a href="main?action=timeline&page=next">Next Page</a>
        </c:if>
        <c:if test="${back}">
        | <a href="main?action=timeline&page=prev">Previous Page</a>
        </c:if>
        | <a href="main?action=timeline&page=end">End of Timeline</a>
    </body>
</html>

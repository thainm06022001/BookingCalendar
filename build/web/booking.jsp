<%-- 
    Document   : booking
    Created on : Mar 21, 2022, 11:32:22 AM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="my" uri="/WEB-INF/tlds/newtag_library.tld" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style>
            .book{
                background-color: #4caf50;
                color: white;
                text-align: center;
            }
        </style>
    </head>
    <body>
        <form method="GET" action="book">
            <input name="change" type="submit" value="prv" />
            <c:choose>
                <c:when test="${week==thisweek}">
                    This week <input hidden="" style="border: none; width: 17px;" name="week" value="${week}"> 
                </c:when>
                <c:otherwise>
                    Week: <input style="border: none; width: 17px;" name="week" value="${week}"> 
                </c:otherwise>
            </c:choose>

          
            <input name="change" type="submit" value="next" />
            <table border="1">

                <tbody>
                    <tr>
                        <td></td>
                        <c:forEach items="${dates}" var="d">
                            <td><my:Day date="${d}"></my:Day></td>
                        </c:forEach>
                    </tr>


                    <c:forEach items="${ro}" var="ro">
                        <tr>
                            <td>${ro.name}</td>
                            <c:forEach items="${dates}" var="d">
                                <td>
                                    <c:if test="${ro.from != null && ro.to != null}">    <my:CheckBook date="${d}" from="${ro.from}" to="${ro.to}" name="${ro.c}"></my:CheckBook> </c:if>

                                        </td>
                            </c:forEach>

                        </tr>
                    </c:forEach>


                </tbody>
            </table>

        </form>


    </body>
</html>

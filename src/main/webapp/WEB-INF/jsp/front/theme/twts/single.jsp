<%--
  Created by IntelliJ IDEA.
  User: whoami
  Date: 02/05/2017
  Time: 11:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path+"/style/front/theme/twts";
    String URL = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<!DOCTYPE html>
<html>
<head>
    <title>Itweet &amp; Boot</title>
    <jsp:include page="tools/style.jsp"></jsp:include>
    <style>
        img {
            width: 720px;
        }
    </style>
</head>

<body id="single">

<%--Nav Menu--%>
<jsp:include page="tools/navbar.jsp"></jsp:include>

<div class="content-body">
    <div class="container">
        <div class="row">
            <main class="col-md-8">
                <article class="post post-1">
                    <header class="entry-header">
                        <h1 class="entry-title">
                            <c:if test="${article.typeArticle eq 1}">
                                [Tweet] ${article.title}
                            </c:if>
                            <c:if test="${article.typeArticle eq 0}">
                                ${article.title}
                            </c:if>
                        </h1>
                        <div class="entry-meta">
                            <span class="post-category"><a href="#">标签：${tagsList}</a></span>

                            <span class="post-date"><a href="#"><time class="entry-date">日期：<fmt:formatDate pattern="yyyy-MM-dd hh:mm:ss" value="${article.createDate}"/></time></a></span>

                            <span class="post-author"><a href="#">作者：${article.author}</a></span>

                            <span class="comments-link"><a href="#">评论：4 Comments</a></span>
                        </div>
                    </header>
                    <div class="entry-content clearfix">
                        ${article.htmlContent}
                    </div>
                </article>
            </main>

            <%-- Left Menu --%>
            <jsp:include page="tools/left-menu.jsp"></jsp:include>

        </div>
    </div>
</div>

<footer id="site-footer">
    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <p class="copyright">&copy; 2017 itweet.cn</p>
            </div>
        </div>
    </div>
</footer>

<!-- Mobile Menu -->
<jsp:include page="tools/mobile-menu.jsp"></jsp:include>

<script src="<%=basePath%>/js/script.js"></script>

</body>
</html>

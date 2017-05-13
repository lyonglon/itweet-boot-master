<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String URL = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<!DOCTYPE html>
<html>
<head>
    <title>BlogSingle</title>
    <jsp:include page="tools/style.jsp"></jsp:include>
</head>

<body>

<!--This is the START of the menu-->
<jsp:include page="tools/menu.jsp"></jsp:include>


<!--This is the START of the content-->
<div id="content">
    <!--This is the START of the blog section-->
    <div id="blog">
        <div class="blog-item-single">
            <h3>${article.title}</h3>
            <div class="blog-item-single-info">
                <div class="user">${article.author}</div>
                <div class="comments">6 comments</div>
                <div class="tags">${tagsList}</div>
            </div>

            <div class="blog-item-single-content">
                <div class="blog-item-single-content"> <a class="single_image" href="<%=URL%>/upload/files/${article.coverPicture}"><img src="<%=URL%>/upload/files/${article.coverPicture}" width="720" height="280" alt="blog1" /></a>
                    ${article.htmlContent}
                </div>

            </div>

            <div class="comments-block">
                <h3 class="comments-title">Comments (2)</h3>
                <div class="spacer"></div>
                <div class="comment"> <img class="avatar" alt="" src="http://www.gravatar.com/avatar/00000000000000000000000000000000?d=mm" width="50" height="35" />
                    <div class="comment-info">
                        <a class="post-author">John Doe</a>
                        <p>January 21, 2011</p>
                    </div>
                    <div class="comment-body">
                        <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. In in vestibulum eros. Praesent at cursus orci. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam vitae sagittis dolor. Proin iaculis suscipit quam non suscipit. Vestibulum vestibulum semper tortor...</p>
                        <a class="reply" href="#">Reply</a>
                    </div>
                </div>
                <div class="spacer"></div>
                <div class="comment"><img class="avatar" alt="" src="http://www.gravatar.com/avatar/00000000000000000000000000000000?d=mm" width="50" height="35" />
                    <div class="comment-info">
                        <a class="post-author">Jane Doe</a>
                        <p>January 21, 2011</p>
                    </div>
                    <div class="comment-body">
                        <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. In in vestibulum eros. Praesent at cursus orci. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam vitae sagittis dolor. Proin iaculis suscipit quam non suscipit. Vestibulum vestibulum semper tortor...</p>
                        <a class="reply" href="#">Reply</a>
                    </div>
                </div>
                <div class="spacer"></div>
            </div>
            <div class="response">
                <h3>Leave a Comment</h3>
                <h6 class="required-comment">* required</h6>
                <div id="comment-form" class="form">
                    <div class="input-block">
                        <h6 class="short-label">Name*</h6>
                        <input class="short-field" id="name" type="text" name="name" value="" />
                    </div>
                    <div class="input-block">
                        <h6 class="short-label">Email*</h6>
                        <input class="short-field" id="email" type="text" name="email" value="" />
                    </div>
                    <div class="input-block-long">
                        <h6 class="short-label">Web</h6>
                        <input class="long-field" id="url" type="text" placeholder="http://" name="url" value="" />
                    </div>
                    <div class="textarea-block">
                        <h6>Comments*</h6>
                        <textarea id="comment" rows="10" cols="70" name="comment"></textarea>
                    </div>
                    <div class="clear"></div>
                    <input id="comment-button" type="submit" value="Submit" name="submit" />
                </div>
                <div class="spacer"></div>
            </div>
        </div>
    </div>
    <!--END of blog section-->
</div>
<!--END of content-->

<!--This is the START of the follow section-->
<%--<jsp:include page="tools/panel.jsp"></jsp:include>--%>

</div>
</body>

</html>
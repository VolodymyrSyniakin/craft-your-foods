<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8" content="text/html">
    <title>Личный кабинет</title>

    <script type="text/javascript" src="<c:url value="/static/js/jquery.min.js" />"></script>
    <script type="text/javascript" src="<c:url value="/static/js/popper.min.js" />"></script>
    <script type="text/javascript" src="<c:url value="/static/js/bootstrap.min.js" />"></script>

    <link rel="stylesheet" href="<c:url value="/static/css/bootstrap.min.css" />">
    <link rel="stylesheet" href="<c:url value="/static/css/header.css" />">
    <link rel="stylesheet" href="<c:url value="/static/css/acc.css" />">

</head>

<body>
<div class="container-fluid">
    <header class="masthead">
        <div class="row">
            <div class="col">
                <h3 class="text-muted">Craft your foods</h3>
            </div>
            <div class="col auth_panel">
                <c:choose>
                    <c:when test="${userAccount!=null}">
                        <div class="btn-group btn-group-md" id="user_data_menu" role="group" aria-label="...">
                            <a class="btn btn-outline-success" href="/acc" role="button">${userAccount.login}</a>
                            <a class="btn btn-outline-success" href="/logout" role="button">Выйти</a>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="btn-group btn-group-md" id="authentication_menu" role="group" aria-label="...">
                            <button class="btn btn-success" type="button" id="form_login">Войти</button>
                            <button class="btn btn-success" type="button" id="registration">Регистрация</button>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
        <nav class="navbar navbar-expand-md navbar-light bg-light rounded mb-3">
            <div class="collapse navbar-collapse" id="navbarCollapse">
                <ul class="navbar-nav text-md-center nav-justified w-100">
                    <li class="nav-item"><a class="nav-link" href="/">Главная</a></li>
                    <li class="nav-item"><a class="nav-link" href="/recipes">Рецепты</a></li>
                    <li class="nav-item"><a class="nav-link" href="/add_recipe">Добавить
                        рецепт</a></li>
                    <li class="nav-item active"><a class="nav-link" href="/acc/?idu=${userAccount.id}">Личный
                        кабинет</a></li>
                    <li class="nav-item"><a class="nav-link" href="#">О нас</a></li>

                    <form class="form-inline" method="post" action="/recipes/search_recipe">
                        <input class="form-control mr-sm-2" type="text"
                               placeholder="Search" name="name" aria-label="Search">
                        <button class="btn btn-outline-success my-2 my-sm-0"
                                type="submit">Поиск
                        </button>
                    </form>

                </ul>
            </div>
        </nav>
    </header>

    <div class="row">
        <div class="col-12 col-md-9">
            <%@include file="jspf/acc/acc_menu.jspf" %>

            <c:choose>
                <c:when test="${view == 0}">
                    <%@include file="jspf/acc/info.jspf" %>
                </c:when>
                <c:when test="${view == 1}">
                    <c:if test='${recipes.size() > 0}'>
                        <%@include file="jspf/get_recipes.jspf" %>
                        <%@include file="jspf/button_group.jspf" %>
                    </c:if>
                </c:when>
                <c:when test="${view == 2}">
                    <c:if test='${messages.size() > 0}'>
                        <%@include file="jspf/acc/messages.jspf" %>
                        <%@include file="jspf/button_group.jspf" %>
                    </c:if>
                </c:when>
                <c:when test="${view == 3}">
                    <c:if test='${viewObjects.size() > 0}'>
                        <%@include file="jspf/acc/moderation.jspf" %>
                        <%@include file="jspf/button_group.jspf" %>
                    </c:if>
                </c:when>
                <c:when test="${view == 4}">
                    <%@include file="jspf/acc/admin.jspf" %>
                </c:when>
                <c:otherwise>
                    <%@include file="jspf/acc/info.jspf" %>
                </c:otherwise>
            </c:choose>
        </div>

        <div class="col-12 col-md-3 last_recipes">
            <h6>Последние добавления: </h6>
            <link rel="stylesheet" href="<c:url value="/static/css/get_recipes.css" />">
            <script type="text/javascript" src="<c:url value="/static/js/last_recipes.js" />"></script>
        </div>
    </div>
</div>

<%@include file="jspf/authentication.jspf" %>

</body>
</html>

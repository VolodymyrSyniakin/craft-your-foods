<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8" content="text/html">
    <title>Рецепты</title>

    <script type="text/javascript" src="<c:url value="/static/js/jquery.min.js" />"></script>
    <script type="text/javascript" src="<c:url value="/static/js/popper.min.js" />"></script>
    <script type="text/javascript" src="<c:url value="/static/js/bootstrap.min.js" />"></script>

    <link rel="stylesheet" href="<c:url value="/static/css/bootstrap.min.css" />">
    <link rel="stylesheet" href="<c:url value="/static/css/header.css" />">
    <link rel="stylesheet" href="<c:url value="/static/css/search_menu.css" />">

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
                    <li class="nav-item active"><a class="nav-link" href="/recipes">Рецепты</a></li>
                    <li class="nav-item"><a class="nav-link" href="/add_recipe">Добавить
                        рецепт</a></li>
                    <li class="nav-item"><a class="nav-link" href="/acc/?idu=${userAccount.id}">Личный кабинет</a></li>
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

            <form class="search_menu" action="/recipes/parameters" method="GET">

                <div class="row standard_menu">
                    <div class="col">
                        <select class="form-control form-control-sm" name="type" id="type">
                            <option value="">Тип рецепта</option>
                            <c:forEach items="${typeRecipe.keySet()}" var="typeDefault">
                                <option <c:if test="${typeDefault.equals(type)}"> selected="selected" </c:if>
                                        value="${typeDefault}"> ${typeRecipe.get(typeDefault)} </option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col">
                        <select class="form-control form-control-sm" name="difficulty" id="difficulty">
                            <option value="">Cложность</option>
                            <c:forEach items="${difficultyRecipe.keySet()}" var="difficultyDefault">
                                <option <c:if
                                        test="${difficultyDefault.equals(difficulty)}"> selected="selected" </c:if>
                                        value="${difficultyDefault}"> ${difficultyRecipe.get(difficultyDefault)} </option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col">
                        <select name="rating" class="form-control form-control-sm">
                            <option value="0">Рейтинг</option>
                            <option
                                    <c:if test="${rating==5.0}">selected</c:if> value="5">5,0
                            </option>
                            <option
                                    <c:if test="${rating==4.0}">selected</c:if> value="4">4,0
                            </option>
                            <option
                                    <c:if test="${rating==3.0}">selected</c:if> value="3">3,0
                            </option>
                            <option
                                    <c:if test="${rating==2.0}">selected</c:if> value="2">2,0
                            </option>
                            <option
                                    <c:if test="${rating==1.0}">selected</c:if> value="1">1,0
                            </option>
                        </select>
                    </div>
                    <div class="col">
                        <input type="text" name="timeMin"
                               <c:if test="${timeMin>0}">value="${timeMin}"</c:if> class="form-control form-control-sm"
                               placeholder="Время до, мин">
                    </div>

                    <div class="col">
                        <button type="button" class="btn btn-light btn-sm show_ingredients_menu">Ингредиенты...</button>
                    </div>

                    <div class="col">
                        <input type="submit" class="btn btn-light btn-sm" value="Показать">
                    </div>
                </div>
                <div class="row ingredients_menu">
                    <div class="col">
                        <h6> Введите обязательные ингредиенты:</h6>
                        <div class="input-group input-group-sm search_ingr">
                            <input type="text" class="form-control noValidation noDynamicValid"
                                   placeholder="Наименование ингридиента"
                                   id="search_name_i" aria-label="Text input with dropdown button">
                            <div class="input-group-btn">
                                <button type="button" class="btn btn-outline-success dropdown-toggle" id="search_ingr"
                                        data-toggle="dropdown"
                                        aria-haspopup="true" aria-expanded="false">
                                    Поиск
                                </button>
                                <div class="dropdown-menu dropdown-menu-right" id="found_i">
                                </div>
                            </div>
                        </div>
                    </div>
                    <ol class="col selected_ingredients">
                        <c:if test="${ingredients!=null}">
                            <c:forEach items="${ingredients}" var="ingredient">
                                <li><input type="hidden" name="idi"
                                           value="${ingredient.id}">${ingredient.name}, ${ingredient.measurement}</li>
                            </c:forEach>
                        </c:if>
                    </ol>
                </div>
            </form>

            <c:if test="${recipes.size() > 0}">
                <%@include file="jspf/get_recipes.jspf" %>
                <%@include file="jspf/button_group.jspf" %>
            </c:if>
        </div>
        <div class="col-12 col-md-3 last_recipes">
            <h6>Последние добавления: </h6>
            <script type="text/javascript" src="<c:url value="/static/js/last_recipes.js" />"></script>
        </div>
    </div>
</div>

<%@include file="jspf/authentication.jspf" %>

<script type="text/javascript" src="<c:url value="/static/js/search_menu.js" />"></script>

</body>
</html>

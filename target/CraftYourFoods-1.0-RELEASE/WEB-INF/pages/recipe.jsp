<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Рецепт</title>

    <script type="text/javascript" src="<c:url value="/static/js/jquery.min.js" />"></script>
    <script type="text/javascript" src="<c:url value="/static/js/popper.min.js" />"></script>
    <script type="text/javascript" src="<c:url value="/static/js/bootstrap.min.js" />"></script>

    <link rel="stylesheet" href="<c:url value="/static/css/bootstrap.min.css" />">
    <link rel="stylesheet" href="<c:url value="/static/css/header.css" />">
    <link rel="stylesheet" href="<c:url value="/static/css/recipe_info.css" />">

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
            <div class="row">
                <div class="col-12 col-md-4">
                    <h5>${recipe.name}</h5>
                    <img class="card-img-top" src='/recipe/img/?idr=${recipe.id}' alt="Card image cap">
                    <p> Сложность: "${recipe.difficulty.toString()}"; <br/>
                        Рейтинг: ${recipe.rating}; <br/>
                        Время приготовления, мин: ${recipe.timeMin}; <br/>
                        Автор: <a href="/acc/?idu=${recipe.userAccount.id}">${recipe.userAccount.login}</a>
                    </p>
                    <c:if test="${edit}">
                        <div class="col edit_button">
                            <a href="/recipe/edit_recipe/?idr=${recipe.id}">
                                <button type="button" class="btn btn-outline-success">Редактировать рецепт</button>
                            </a>
                        </div>
                        <div class="col edit_button">
                            <a href="/recipe/delete/?idr=${recipe.id}">
                                <button type="button" class="btn btn-outline-success">Удалить</button>
                            </a>
                        </div>
                    </c:if>
                </div>
                <div class="col-12 col-md-8">
                    <p class="description"><span>Описание рецепта:</span> ${recipe.text} </p>
                    <div class="row">
                        <div class="col-12 col-md-6">
                            <div class="form-group">
                                <h6>Способ приготовления: </h6>
                                <div class="steps">
                                    <ol>
                                        <c:forEach items="${recipe.steps}" var="step">
                                            <li> ${step.text} </li>
                                        </c:forEach>
                                    </ol>
                                </div>
                            </div>
                        </div>
                        <div class="col-12 col-md-6">
                            <div class="form-group">
                                <h6>Ингредиенты: </h6>
                                <div class="ingredients">
                                    <ol>
                                        <c:forEach items="${recipe.ingredientsByRecipe}" var="ingrByRecipe">
                                            <li> ${ingrByRecipe.ingredient.name},
                                                кол-во: ${ingrByRecipe.weight} ${ingrByRecipe.ingredient.measurement};
                                                Важность: ${ingrByRecipe.important.toString()}.
                                            </li>
                                        </c:forEach>

                                    </ol>
                                    <p></p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="reviews">
                <h5> Отзывы: </h5>
                <form action="/send_message" method="post">
                    <input class="idr" type="hidden" name="idr" value="${recipe.id}">
                    <textarea class="form-control" rows="3" name="text" placeholder="Оставьте свой отзыв..."></textarea>
                    <button class="btn btn-outline-success" type="submit" id="send_message">Отправить</button>
                </form>
                <ul></ul>
                <div class="btn-group mr-2 page_buttons" role="group">
                </div>
            </div>
        </div>
        <div class="col-12 col-md-3 last_recipes">
            <h6>Последние добавления: </h6>
            <link rel="stylesheet" href="<c:url value="/static/css/get_recipes.css" />">
            <script type="text/javascript" src="<c:url value="/static/js/last_recipes.js" />"></script>
        </div>
    </div>
</div>

<%@include file="jspf/authentication.jspf" %>

<script type="text/javascript" src="<c:url value="/static/js/recipe_messages.js" />"></script>

</body>
</html>

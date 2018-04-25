<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">

    <c:choose>
        <c:when test="${view == 0}">
            <title>Редактировать рецепт</title>
        </c:when>
        <c:otherwise>
            <title>Добавить рецепт</title>
        </c:otherwise>
    </c:choose>

    <script type="text/javascript" src="<c:url value="/static/js/jquery.min.js" />"></script>
    <script type="text/javascript" src="<c:url value="/static/js/popper.min.js" />"></script>
    <script type="text/javascript" src="<c:url value="/static/js/bootstrap.min.js" />"></script>

    <link rel="stylesheet" href="<c:url value="/static/css/bootstrap.min.css" />">
    <link rel="stylesheet" href="<c:url value="/static/css/header.css" />">
    <link rel="stylesheet" href="<c:url value="/static/css/edit_recipe.css" />">

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
                    <li class="nav-item <c:if test='${view==1}'>active</c:if>"><a class="nav-link" href="/add_recipe">Добавить
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

        <form class="col-12 col-md-9 form_recipe" id="${recipe.id}">
            <div class="row">
                <div class="col-12 col-md-5">
                    <div class="form-group row">
                        <div class="col-sm-10">
                            <input type="text" name="name" id="name" class="form-control"
                                   placeholder="Наименование рецепта" value="${recipe.name}">
                        </div>
                    </div>
                    <div class="form-group row select_option">
                        <input type="text" name="time" id="time" class="form-control form-control-sm my-1 number"
                               placeholder="Время приготовления, мин" value="${recipe.timeMin}">
                        <select class="custom-select custom-select-sm my-1" name="difficulty" id="difficulty">
                            <option value="">Выберите сложность рецепта</option>
                            <c:forEach items="${difficultyRecipe.keySet()}" var="difficulty">
                                <option value="${difficulty}"
                                        <c:if test='${difficulty==recipe.difficulty}'>selected</c:if> > ${difficultyRecipe.get(difficulty)} </option>
                            </c:forEach>
                        </select>
                        <select class="custom-select custom-select-sm my-1" name="type" id="type">
                            <option value="">Тип рецепта</option>
                            <c:forEach items="${typeRecipe.keySet()}" var="type">
                                <option value="${type}"
                                        <c:if test='${type==recipe.type}'>selected</c:if> > ${typeRecipe.get(type)} </option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group" name="text">
                        <label for="text_area">Описание рецепта</label>
                        <textarea class="form-control" id="text_area" rows="3">${recipe.text}</textarea>
                    </div>
                    <div class="form-group step_add">
                        <h5>Способ приготовления: </h5>
                        <div class="input-group input-group-sm">
                            <select class="form-control form-control-sm noValidation" id="index_steps">
                                <c:forEach var="i" begin="1" end="${recipe.steps.size()+1}">
                                    <option>${i}</option>
                                </c:forEach>
                            </select>

                            <input type="text" class="form-control form-control-sm noValidation" id="new_step"
                                   placeholder="Этап приготовления">
                            <button class="btn btn-sm btn-outline-success" id="add_step" type="button">Добавить</button>
                        </div>
                        <div class="steps" <c:if test='${view==0}'>style="display:block"</c:if>>
                            <ol>
                                <c:forEach items="${recipe.steps}" var="step">
                                    <li>
                                        <div class="form-inline">
                                            <div class="col-sm-11">
                                                <input type="text" class="form-control form-control-sm"
                                                       value="${step.text}" id="step_id=${step.id}">
                                            </div>
                                            <div class="col-sm-1">
                                                <img src="<c:url value="/static/img/delete.png" />" alt="...">
                                            </div>
                                        </div>
                                    </li>
                                </c:forEach>
                            </ol>
                        </div>
                    </div>
                </div>
                <div class="col-12 col-md-7">
                    <div class="text-center image_recipe">
                        <div>
                            <c:choose>
                                <c:when test="${view == 0}">
                                    <img src="/recipe/img/?idr=${recipe.id}" id='img' class="rounded" alt="...">
                                </c:when>
                                <c:otherwise>
                                    <img src="<c:url value="/static/img/default other.png" />" id='img' class="rounded"
                                         alt="...">
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <form id="form_file" enctype="multipart/form-data">
                            <label class="custom-file">
                                <input type="file" id="add_file" accept="image/*"
                                       class="custom-file-input noValidation noDynamicValid">
                                <span class="custom-file-control">Выберите файл</span>
                            </label>
                        </form>
                    </div>
                    <div class="input-group search_ingr">
                        <input type="text" class="form-control noValidation noDynamicValid"
                               placeholder="Наименование ингридиента"
                               id="search_name_ingr" aria-label="Text input with dropdown button">
                        <div class="input-group-btn">
                            <button type="button" class="btn btn-outline-success dropdown-toggle" id="search_ingr"
                                    data-toggle="dropdown"
                                    aria-haspopup="true" aria-expanded="false">
                                Поиск
                            </button>
                            <div class="dropdown-menu dropdown-menu-right found_ingredients">
                                <a class="dropdown-item" id="display_new_ingredient" href="#">Добавить новый</a>
                                <div role="separator" class="dropdown-divider"></div>
                            </div>
                        </div>
                    </div>

                    <div class="form-row add_new_ingredient">
                        <div class="col-md-5">
                            <input type="text" class="form-control form-control-sm noValidation" id="name_new_ingr"
                                   placeholder="Новый ингридиент">
                        </div>
                        <div class="col-md-3">
                            <input type="text" class="form-control form-control-sm noValidation"
                                   id="measurement_new_ingr"
                                   placeholder="Еденица измерения">
                        </div>
                        <div class="col-md-2">
                            <select class="custom-select custom-select-sm noValidation" id="type_new_ingr">
                                <option value="">Тип</option>
                                <c:forEach items="${typeIngredient.keySet()}" var="type">
                                    <option value="${type}"> ${typeIngredient.get(type)} </option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-md-2">
                            <button class="btn btn-outline-success btn-sm" id="add_ingr"
                                    type="button"> Добавить
                            </button>
                        </div>
                    </div>
                    <div class="ingredients"
                         <c:if test='${view==0}'>style="display:block"</c:if> >
                        <ol>
                            <c:forEach items="${recipe.ingredientsByRecipe}" var="ingredientByRecipe">
                                <li class="ingredient" id="ingredient=${ingredientByRecipe.ingredient.id}">
                                    <div class="form-inline">
                                        <input class="type_ingredient" type="hidden" name="type"
                                               value="${ingredientByRecipe.ingredient.type}">
                                        <div class="col-md-5">
                                            <p>${ingredientByRecipe.ingredient.name}, ${ingredientByRecipe.ingredient.measurement}</p>
                                        </div>
                                        <div class="col-md-3 ing_by_recipe" id="ingByRecipe=${ingredientByRecipe.id}">
                                            <select class="custom-select custom-select-sm important">
                                                <option value="">Важность</option>
                                                <c:forEach items="${importantIngredient.keySet()}" var="important">
                                                    <option value="${important}"
                                                            <c:if test='${important==ingredientByRecipe.important}'>selected</c:if> > ${importantIngredient.get(important)} </option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <div class="col-md-3">
                                            <input type="text" class="form-control form-control-sm number weight"
                                                   placeholder="Количество" value="${ingredientByRecipe.weight}">
                                        </div>
                                        <div class="col-md-1">
                                            <img src="<c:url value="/static/img/delete.png" />" alt="...">
                                        </div>
                                    </div>
                                </li>
                            </c:forEach>
                            <c:if test='${view==1}'>
                                <li class="ingredient empty">
                                    <div class="form-inline">
                                        <input class="type_ingredient" type="hidden" name="type"
                                               value="">
                                        <div class="col-md-5">
                                            <p></p>
                                        </div>
                                        <div class="col-md-3 ing_by_recipe">
                                            <select class="custom-select custom-select-sm important">
                                                <option value="">Важность</option>
                                                <c:forEach items="${importantIngredient.keySet()}" var="important">
                                                    <option value="${important}"
                                                            <c:if test='${important==ingredientByRecipe.important}'>selected</c:if> > ${importantIngredient.get(important)} </option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <div class="col-md-3">
                                            <input type="text" class="form-control form-control-sm number weight"
                                                   placeholder="Количество" value="${ingredientByRecipe.weight}">
                                        </div>
                                        <div class="col-md-1">
                                            <img src="<c:url value="/static/img/delete.png" />" alt="...">
                                        </div>
                                    </div>
                                </li>
                            </c:if>
                        </ol>
                    </div>
                    <div class="form-group row send_button">
                        <c:choose>
                            <c:when test="${view == 0}">
                                <button type="button" id="update_recipe" class="btn btn-success">Сохранить</button>
                            </c:when>
                            <c:otherwise>
                                <button type="button" id="submit_recipe" class="btn btn-success">Добавить рецепт
                                </button>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>

            </div>
        </form>
        <div class="col-12 col-md-3 last_recipes">
            <h6>Последние добавления: </h6>
            <link rel="stylesheet" href="<c:url value="/static/css/get_recipes.css" />">
            <script type="text/javascript" src="<c:url value="/static/js/last_recipes.js" />"></script>
        </div>
    </div>
</div>


<%@include file="jspf/authentication.jspf" %>
<script type="text/javascript" src="<c:url value="/static/js/edit_recipe.js" />"></script>

</body>
</html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:if test="${lastRecipes.size()>0}">
    <c:forEach var="i" begin="0" end="${lastRecipes.size()-1}">
        <div class="card" id="recipe_${lastRecipes.get(i).id}">
            <div class="text-center">
                <img src='/recipe/img/?idr=${lastRecipes.get(i).id}' alt="Card image cap">
            </div>
            <div class="card-body">
                <h5 class="card-title"><a href="/recipe/?idr=${lastRecipes.get(i).id}">${lastRecipes.get(i).name}</a></h5>
                <p class="card-text">${(lastRecipes.get(i).getText().length()>75) ? lastRecipes.get(i).getText().substring(0, 75) : lastRecipes.get(i).getText()}...
                <a href="/recipe/?idr=${lastRecipes.get(i).id}">Подробне</a></p>
            </div>
        </div>
    </c:forEach>
</c:if>

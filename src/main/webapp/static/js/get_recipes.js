function showStars(value, changeStars) {
    for (var i = 0; i < changeStars.length; i++) {
        if (i < value) {
            changeStars.eq(i).css("background-position", "left -16px");
        } else {
            changeStars.eq(i).css("background-position", "left 0px");
        }

    }
}

function valueStar(cardRecipe) {
    var value = Math.round(cardRecipe.find(".rating").attr("value"));
    showStars(value, $(cardRecipe).find('li'));
}

function updateRating(data, textStatus, jqXHR) {
    var value = jqXHR.getResponseHeader("rating_value");
    var cardRecipe = $('[id $= recipe_' + jqXHR.getResponseHeader("id_recipe") + ']');

    $(cardRecipe).find('.rating_value').text('Рейтинг: ' + value);
    $(cardRecipe).find('.rating').attr('value', value);

    valueStar(cardRecipe);
}

function mouseoverToStar(event) {
    var value = event.target.getAttribute('value');
    var changeStars = $(event.target.parentNode).find('li');
    showStars(value, changeStars);
}


function mouseoutFromStar(event) {
    var value = Math.round(event.target.parentNode.getAttribute('value'));
    var changeStars = $(event.target.parentNode).find('li');
    showStars(value, changeStars);
}

function clickToStar(event) {
    var idr = $(event.target.parentNode).closest('.card').attr('id').replace('recipe_', '');
    var value = event.target.getAttribute('value');
    if ($(".auth_panel div").is('#user_data_menu')) {
        $.ajax({
            url: '/add_value_rating',
            type: 'POST',
            data: {idr: idr, value: value},
            success: updateRating
        });
    } else {
        alert("Пожалуйста, выполните вход на сайт!");
    }

}

window.onload = function () {
    var recipes = $('.card');
    for (var i = 0; i < recipes.length; i++) {
        valueStar(recipes.eq(i));
    }
    ;
};

$('.card').on("mouseover", "li", mouseoverToStar);
$('.card').on("mouseout", "li", mouseoutFromStar);
$('.card').on("click", "li", clickToStar);

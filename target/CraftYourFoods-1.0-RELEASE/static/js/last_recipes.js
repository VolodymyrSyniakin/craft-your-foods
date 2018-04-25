function showRecipes(data) {
    $('.last_recipes').append(data);
}

function loadRecipes() {
    $.ajax({
        url: '/last_recipes',
        type: 'POST',
        success: showRecipes
    });
}

$(document).ready(loadRecipes);
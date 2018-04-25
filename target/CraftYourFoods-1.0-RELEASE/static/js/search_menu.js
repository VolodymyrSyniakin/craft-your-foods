var lastSearchIngr;

function deleteFoundIngredients() {
    var elements = $('[id^="found="]');
    if (elements.length > 0) {
        elements.remove();
    }
}

function foundIngredientsAndShow(data) {

    deleteFoundIngredients();
    for (var i = 0; ((i < data.length) && (i < 5)); i++) {
        var element = '<a class="dropdown-item" id="found=' + data[i].id + '" href="#">' + data[i].name + ', ' + data[i].measurement + '</a>';

        $('#found_i').append(element);
    }
}

function searchIngredientsSubmit() {
    if ($("#search_name_i").val() == "") {
        deleteFoundIngredients();
        lastSearchIngr = '';
    } else if ($("#search_name_i").val() != lastSearchIngr) {
        $.ajax({
            url: "/search_ingredients",
            type: "POST",
            dataType: "json",
            data: ({name: $("#search_name_i").val()}),
            success: foundIngredientsAndShow
        });
        lastSearchIngr = $("#search_name_i").val();
    }
    ;
}

function choiceIngredientInDropdown(event) {
    var idi = event.target.getAttribute("id").replace("found=", "");
    var text = event.target.text;
    $('ol').append('<li> <input type="hidden" name="idi" value="' + idi + '">' + text + '</li>');
}

$("#search_ingr").click(searchIngredientsSubmit);
$('#found_i').on('click', '[id^="found="]', choiceIngredientInDropdown);

$('.show_ingredients_menu').click(function () {
    if ($('.ingredients_menu').css('display') == 'none') {
        $('.ingredients_menu').css('display', 'block');
    } else {
        $('.ingredients_menu').css('display', 'none');
    }
});
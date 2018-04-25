// functions for working with ingredients
var lastSearchIngr;

function addDataIngredient(element, id, type, name, measurement) {
    if (id != 0) {
        element.attr('id', 'ingredient=' + id);
    } else {
        element.removeAttr('id');
    }

    element.find(".type_ingredient").val(type);
    element.find("p").text(name + ", " + measurement);
    return element;
}

function hidePanelNewIngredient() {
    $(".add_new_ingredient").css("display", "none");
    $(".add_new_ingredient").find($("input, select")).removeClass("is-valid");
    $(".ingredients").css("margin-top", "51px");
    $("#search_name_ingr").val("");
}

function showPanelNewIngredient() {
    $("#name_new_ingr").val($("#search_name_ingr").val());
    $("#measurement_new_ingr").val("");
    $("#type_new_ingr").val("");
    $(".add_new_ingredient").css("display", "flex");
    $(".ingredients").css("margin-top", "0px");
}

function showIngredient(id, type, name, measurement) {
// add ingredient information to list
    if ($(".ingredients .empty").length > 0) {
        $(".ingredients li:first").removeClass('empty');
        addDataIngredient($(".ingredient:last"), id, type, name, measurement);
        $(".ingredients").css("display", "block");
    } else {
        var element = $(".ingredient:last").clone();
//clear the cloned element:
        element.find("input, select").removeClass("is-valid is-invalid");
        element.find("input").val("");
        element.find("select").eq(0).val("");
        element.find(".ing_by_recipe").removeAttr('id');

        $(".ingredients ol").append(addDataIngredient(element, id, type, name, measurement));
    }
}

function addIngredient() {
// validation
    var inputs = $(".ingredient_add").find($("input, select"));
    for (var i = 0; i < inputs.length; i++) {
        if (inputs.eq(i).val() == "") {
            inputs.eq(i).addClass("is-invalid");
            return;
        }
    }

// selected values of variables
    var type = $('#type_new_ingr').val();
    var name = $('#name_new_ingr').val();
    var measurement = $('#measurement_new_ingr').val();

    showIngredient(0, type, name, measurement);

    hidePanelNewIngredient();
}

function clearItemsDropDownButton() {
    var elements = $('[id^="found="]');
    if (elements.length > 0) {
        elements.remove();
    }
}

function showFoundIngredients(data) {
    clearItemsDropDownButton();
    if (data != "") {
        for (var i = 0; ((i < data.length) && (i < 5)); i++) {
            var element = $('#display_new_ingredient').clone();
            element.attr("id", ("found=" + data[i].id + ';' + data[i].type));
            element.text(data[i].name + ", " + data[i].measurement);
            $('.found_ingredients').append(element);
        }
    }
}

function searchIngredientsSubmit() {
    if ($("#search_name_ingr").val() == "") {
        clearItemsDropDownButton();
    } else if ($("#search_name_ingr").val() != lastSearchIngr) {
        $.ajax({
            url: "/search_ingredients",
            type: "POST",
            dataType: "json",
            data: ({name: $("#search_name_ingr").val()}),
            success: showFoundIngredients
        });
        lastSearchIngr = $("#search_name_ingr").val();
    }
}

function choiceIngredientInDropdown(event) {
    var splitId = event.target.getAttribute("id").replace("found=", "").split(";");
    var id = splitId[0];
    var type = splitId[1];

    var splitText = event.target.text.split(", ");
    var name = splitText[0];
    var measurement = splitText[1];

    showIngredient(id, type, name, measurement);
}

$("#display_new_ingredient").click(showPanelNewIngredient);
$("#add_ingr").click(addIngredient);
$("#search_ingr").click(searchIngredientsSubmit);
$('.found_ingredients').on('click', '[id^="found="]', choiceIngredientInDropdown);

// functions for working with steps
function addStep() {
    var position = Number($("#index_steps").val()) - 1;
    var text = $("#new_step").val();

    if (text != '') {
        $("#new_step").val("");
        $("#new_step").removeClass("is-valid");

        var liElement = '<li><div class="form-inline"><div class="col-sm-11">' +
            '<input type="text" class="form-control form-control-sm noDynamicValid" value="' + text + '"></div>' +
            '<div class="col-sm-1"> <img src="/static/img/delete.png" alt="..." /></div></div></li>';

        var lengthLi = $(".steps ol li").length;

// show new cooking step:
        if (position != 0) {
            $(liElement).insertAfter($('.steps ol li').eq(position - 1));
        } else if (lengthLi != 0) {
            $(liElement).insertBefore($('.steps ol li').eq(position));
        } else {
            $(".steps ol").append(liElement);
            $(".steps").css("display", "block");
        }

// add index to next step element
        $('#index_steps').append('<option selected="selected">' + (lengthLi + 2) + '</option>');
    } else {
        $("#new_step").addClass("is-invalid");
    }
}

function deleteStep(event) {
    $(event.target).closest("li").remove();
    $("#index_steps option:last").remove();

}

function deleteIngredient(event) {
    if ($(".ingredients li").length == 1) {
        var cleanElement = $(".ingredients li").eq(0);
        $(cleanElement).find(".ing_by_recipe").removeAttr("id");
        $(cleanElement).find(".weight").val("");
        $(cleanElement).find("option").removeAttr("selected");
        $(cleanElement).find("select").val("");
        cleanElement.addClass('empty');

        $(".ingredients").css("display", "none");

    } else {
        $(event.target).closest("li").remove();
    }
}

$("#add_step").click(addStep);
$(".steps").on("click", "img", deleteStep);
$(".ingredients").on("click", "img", deleteIngredient);

// validation inputs
$('.form_recipe').on("change", "select, textarea, input:not(.noDynamicValid)", dynamicValidation);

function dynamicValidation(event) {
    var element = $(event.target);
    if ((element.val() == "")) {
        element.removeClass("is-valid");
        element.addClass("is-invalid");
    } else {
        element.removeClass("is-invalid");
        element.addClass("is-valid");
    }
}

function validation() {
    var inputs = $(".form_recipe").find($("input, select, textarea")).not($('.noValidation'));
    var check = true;
    for (var i = 0; i < inputs.length; i++) {
        if (inputs.eq(i).val() == "") {
            inputs.eq(i).addClass("is-invalid");
            check = false;
        } else {
            inputs.eq(i).addClass("is-valid");
        }
    }
    if (!check) {
        return false;
    }
    var numbers = $('.number');
    for (var i = 0; i < numbers.length; i++) {
        if (isNaN(Number($('.number').eq(i).val()))) {
            $('.number').eq(i).addClass("is-invalid");
            alert("Не корректно указаны данные");
            return false;
        }
    }
    if ($('.steps li').length <= 0) {
        alert("Введите этапы приготовления");
        return false;
    }
    if ($('.ingredient').length <= 0) {
        alert("Введите список ингридиентов");
        return false;
    }
    return true;
}

// add image
var encodeImage;

function resizeImage(img) {

    var resultImg = document.createElement('canvas');
    var cResultImg = resultImg.getContext('2d');

    var resizeImg = document.createElement('canvas');
    var cResizeImg = resizeImg.getContext('2d');

    var width = img.width;
    var height = img.height;

    resizeImg.width = width;
    resizeImg.height = height;
    cResizeImg.drawImage(img, 0, 0, resizeImg.width, resizeImg.height);

    while (width > 630 && height > 360) {
        cResizeImg.drawImage(resizeImg, 0, 0, width, height, 0, 0, width * 0.5, height * 0.5);
        width = width * 0.5;
        height = height * 0.5;
    }

    resultImg.width = width;
    resultImg.height = height;
    cResultImg.drawImage(resizeImg, 0, 0, resultImg.width, resultImg.height,
        0, 0, resultImg.width, resultImg.height);

    return resultImg.toDataURL();
}

function addImage(event) {
    var file = event.target.files[0];
    var reader = new FileReader();
    reader.onload = function (event) {
        var loadImg = event.target.result;
        var img = new Image();
        img.src = loadImg;
        img.onload = function () {
            encodeImage = resizeImage(img);
            $('#img').attr('src', encodeImage);
        };
    };
    reader.readAsDataURL(file);
}


$('#add_file').change(addImage);

// send recipe
function Recipe() {
    this.id;
    this.name;
    this.text;
    this.timeMin;
    this.difficulty;
    this.type;
    this.ingredientsByRecipe = new Array();
    this.steps = new Array();
}

function IngredientByRecipe(id, weight, important, ingredient) {
    this.id = id;
    this.weight = weight;
    this.important = important;
    this.ingredient = ingredient;
}

function Ingredient(id, name, measurement, type) {
    this.id = id;
    this.name = name;
    this.measurement = measurement;
    this.type = type;
}

function Step(id, position, text) {
    this.id = id;
    this.position = position;
    this.text = text;
}

function createRecipe() {
    var recipe = new Recipe();

    recipe.id = $(".form_recipe").attr('id');
    recipe.name = $('#name').val();
    recipe.timeMin = $('#time').val();
    recipe.difficulty = $('#difficulty').val();
    recipe.type = $('#type').val();
    recipe.text = $('#text_area').val();

    var steps = $('.steps input');
    for (var i = 0; i < steps.length; i++) {
        var idStep = '';
        if (steps.eq(i).attr('id') != undefined) {
            idStep = steps.eq(i).attr('id').replace('step_id=', '');
        }
        recipe.steps.push(new Step(idStep, i + 1, steps.eq(i).val()));
    }

    var temp = $('.ingredients li');
    for (var i = 0; i < temp.length; i++) {
        var idIngredientByRecipe = '';
        var idIngredient = '';
        var splitP = temp.eq(i).find('p').text().split(", ");
        if (temp.eq(i).attr('id') != undefined) {
            idIngredient = temp.eq(i).attr('id').replace('ingredient=', '');
        }
        if (temp.eq(i).find('.ing_by_recipe').attr('id') != undefined) {
            idIngredientByRecipe = temp.eq(i).find('.ing_by_recipe').attr('id').replace('ingByRecipe=', '');
        }
        var ingredient = new Ingredient(idIngredient, splitP[0], splitP[1], temp.eq(i).find('.type_ingredient').val());
        recipe.ingredientsByRecipe.push(new IngredientByRecipe(idIngredientByRecipe, temp.eq(i).find('.weight').val(), temp.eq(i).find('.important').val(), ingredient));
    }
    return recipe;
}


function addRecipe() {
    if (validation()) {
        $.ajax({
            url: '/submit_recipe',
            type: 'POST',
            data: {strRecipe: JSON.stringify(createRecipe()), encodeImage: $('#img').attr('src')},
            success: function (data) {
                window.location.assign(data);
            }
        });
    }
}

function updateRecipe() {
    if (validation()) {
        $.ajax({
            url: '/update_recipe',
            type: 'POST',
            data: {
                strRecipe: JSON.stringify(createRecipe()),
                encodeImage: $('#img').attr('src'),
                idr: $(".form_recipe").attr('id')
            },
            success: function (data) {
                window.location.assign(data);
            }
        });
    }
}

$("#submit_recipe").click(addRecipe);
$("#update_recipe").click(updateRecipe);


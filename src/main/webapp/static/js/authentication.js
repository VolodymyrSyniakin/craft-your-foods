function showFormLogIn() {
    $(".authentication").css("display", "block");
    $(".authentication_bg").css("display", "block");
    $(".log_in").css("display", "block");
};

function showFormRegistration() {
    $(".authentication").css("display", "block");
    $(".authentication_bg").css("display", "block");
    $(".registration").css("display", "block");
};

function hideForms() {
    $(".authentication").css("display", "none");
    $(".authentication_bg").css("display", "none");
    $(".registration").css("display", "none");
    $(".log_in").css("display", "none");
}

$("#form_login").click(showFormLogIn);

$("#registration").click(showFormRegistration);

$(".close_img").click(hideForms);
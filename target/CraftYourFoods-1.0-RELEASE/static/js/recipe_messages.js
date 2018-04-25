function changeClassActivePage(numberActivePage) {
    var activePage = $('.page[id $= ' + numberActivePage + ']');
    activePage.removeClass('btn-outline-success');
    activePage.addClass('btn-success');

    var pages = $('.page_buttons .btn-success[id != page_' + numberActivePage + ']');
    if (pages != undefined) {
        pages.addClass('btn-outline-success');
        pages.removeClass('btn-success');
    }
}

function showMessages(data, textStatus, jqXHR) {
    if (data != null) {
        $('.reviews ol').remove();
        for (var i = 0; i < data.length; i++) {
            var date = new Date(data[i].date);
            var ol = $('<ol><p class="reviews_info">' + date.toLocaleString() + ', ' + data[i].userAccount.login + '</p><p class="review">' + data[i].text + '</p></ol>');
            $('.reviews ul').append(ol);
        }
    }

    var pages = parseInt(jqXHR.getResponseHeader('count_pages'));
    if (pages > 0) {
        var lastPageView = 0;
        if ($('.page:last').attr('id') != undefined) {
            lastPageView = parseInt($('.page:last').attr('id').replace("page_", "")) + 1;
        }
        if (lastPageView < pages) {
            for (var i = (lastPageView + 1); i <= pages; i++) {
                var buttonPage = $('<button type="button" class="btn btn-outline-success page" id="page_' + (i - 1) + '">' + i + '</button>');
                $('.page_buttons').append(buttonPage);
            }
        } else {
            for (var i = pages; i > lastPageView; i--) {
                $('[id = page_' + i + ']').remove();
            }
        }
    }
    changeClassActivePage(jqXHR.getResponseHeader('active_page'));
}

function loadPageMessages(event) {
    var element = event.target;
    loadMessages(element.getAttribute('id').replace("page_", ""));
}

function loadMessages(page) {
    $.ajax({
        url: '/get_messages',
        type: 'POST',
        data: ({
            page: page,
            idr: $('.idr').val()
        }),
        success: showMessages
    });
}

$('.page_buttons').on('click', '.page', loadPageMessages);

$(document).ready(loadMessages(0));

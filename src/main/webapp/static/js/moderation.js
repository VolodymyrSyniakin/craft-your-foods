function sendStatusModeration(event) {
    var idSplit = event.target.getAttribute('id').split('=');
    $.ajax({
        url: '/acc/moderation/set_moderation',
        type: 'POST',
        data: ({
            type: idSplit[0],
            ide: idSplit[1],
            value: $(event.target).val(),
        }),
    });
}

$('.status').on('change', sendStatusModeration);



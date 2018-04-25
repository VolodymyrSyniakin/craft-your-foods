function sendRole(event) {
    var idu = event.target.getAttribute('id').replace('userData=', '');
    $.ajax({
        url: '/acc/admin/change_role',
        type: 'POST',
        data: ({
            idu: idu,
            role: $(event.target).val(),
        }),
    });
}

$('.role').on('change', sendRole);
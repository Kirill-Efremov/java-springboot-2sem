<!DOCTYPE html>
<html lang="en">
<head>
    <title>Catalog</title>
    <meta name="_csrf" content="${_csrf.token}">
    <link rel="stylesheet" href="../static/css/settings.css">
</head>
<body>
<#include "menu.ftlh">
<#if user.isAdmin()>
    <a class="btn btn-dark mb-3"
       href="/admin">
        Панель администратора
    </a><br>
</#if>
<div class="container-settings">
    <form class="file-upload" id="uploadFileForm" enctype="multipart/form-data">
        <input type="file" name="file" id="fileInput" class="file-input">
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <input type="button" value="File Upload" onclick="uploadFile()" class="upload-button">
    </form>
    <form action="/settings/update" method="post" class="update-form">
        <div id="settings" class="white-container">
            <label>
                <input class="form-control" name="name" type="text" value="${user.name}"
                       placeholder="First name">
            </label>
            <label>
                <input class="form-control" name="numberPhone" type="text" value="${user.numberPhone}"
                       placeholder="Phone number">
            </label>
            <label>
                <input class="form-control" name="email" type="text" value="${user.email}"
                       placeholder="email">
            </label>
            <input type="hidden" name="_csrf" value="${_csrf.token}">
            <input  type="submit" value="Изменить личные данные" class="update-button">
        </div>
    </form>
    <form id="changePasswordForm" class="change-password-form">
        <div id="password" class="white-container">
            <label>
                <input class="form-control" name="oldPassword" id="oldPassword" type="password" placeholder="Старый пароль">
            </label>
            <label>
                <input class="form-control" name="newPassword" id="newPassword" type="password" placeholder="Новый пароль">
            </label>
            <label>
                <input class="form-control" name="confirmPassword"  id="confirmPassword" type="password" placeholder="Подвердите новый пароль">
            </label>
            <input type="hidden" name="_csrf" value="${_csrf.token}">
            <input type="submit" value="Изменить пароль" class="change-password-button">
        </div>
    </form>
</div>
<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
<script>
    function uploadFile() {
        var fileInput = document.getElementById("fileInput");
        var file = fileInput.files[0];

        if (file) {
            var formData = new FormData();
            formData.append("file", file);
            $.ajax({
                url: 'file-upload-settings',
                type: 'POST',
                data: formData,
                processData: false,
                contentType: false,
                headers: {
                    'X-CSRF-TOKEN': document.querySelector('input[name="_csrf"]').value
                } ,
                success: function () {
                    alert("Файл загружен успешно!");
                },
                error: function (xhr) {
                    alert('Ошибка обновления файла: ' +  xhr.responseText);
                }
            });
        } else {
            alert("Пожалуйста выберите файл!");
        }
    }
    $(document).ready(function () {
        $('#changePasswordForm').submit(function (event) {
            event.preventDefault();
            var oldPassword = $('#oldPassword').val();
            var newPassword = $('#newPassword').val();
            var confirmPassword = $('#confirmPassword').val();

            var formData = new FormData();
            formData.append('oldPassword', oldPassword);
            formData.append('newPassword', newPassword);
            formData.append('confirmPassword', confirmPassword);

            $.ajax({
                type: 'POST',
                url: '/settings/change-password',
                data: formData,
                contentType: false,
                processData: false,
                headers: {
                    'X-CSRF-TOKEN': document.querySelector('input[name="_csrf"]').value
                } ,
                success: function (response) {
                    alert(response);
                    $('#oldPassword').val('');
                    $('#newPassword').val('');
                    $('#confirmPassword').val('');
                },
                error: function (xhr) {
                    alert(xhr.responseText);
                }
            });
        });
    });
</script>
</body>
</html>

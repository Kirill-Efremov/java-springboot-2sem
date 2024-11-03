<!DOCTYPE html>
<html>
<head>
    <title>Сatalog</title>
    <link href="../static/css/post-сreate.css" rel="stylesheet">
</head>
<body>
<#include "menu.ftlh">
<h3 class="title">Создать новое объявление</h3>
<div class="white-container">
    <form id="create-post-form" enctype="multipart/form-data">
        <label for="title">Название объявления:</label>
        <input type="text" id="title" name="title"/><br>
        <label for="content">Описание объявления:</label>
        <input type="text" id="content" name="content"/><br>
        <label for="price">Цена:</label>
        <input type="number" id="price" name="price"/><br>
        <label for="city">Город:</label>
        <input type="text" id="city" name="city"/><br>
        <label for="file">Фотография:</label>
        <input type="file" id="file" name="file"/><br>
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <input class="submit-button" type="submit" value="Добавить товар"/>
    </form>
</div>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
    $(document).ready(function() {
        $('#create-post-form').submit(function(event) {
            event.preventDefault();
            var title = $('#title').val();
            var content = $('#content').val();
            var price = $('#price').val();
            var city = $('#city').val();

            if (!title || !content || !price || !city) {
                alert("Невозможно создать пост с пустыми полями.");
                return;
            }

            var formData = new FormData();
            formData.append('title', title);
            formData.append('content', content);
            formData.append('price', price);
            formData.append('city', city);
            formData.append('file', $('#file')[0].files[0]);

            $.ajax({
                type: 'POST',
                url: '/api/post',
                data: formData,
                contentType: false,
                processData: false,
                headers: {
                    'X-CSRF-TOKEN': document.querySelector('input[name="_csrf"]').value
                },
                success: function(response) {
                    alert(response);
                    $('#title').val('');
                    $('#content').val('');
                    $('#price').val('');
                    $('#city').val('');
                    $('#file').val('');
                },
                error: function(xhr) {
                    alert('Ошибка: ' + xhr.responseText);
                }
            });
        });
    });
</script>
</body>
</html>

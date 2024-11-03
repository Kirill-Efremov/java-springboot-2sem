<!DOCTYPE html>
<html>
<head>
    <title>Редактирование поста</title>
    <meta name="_csrf" content="${_csrf.token}">
    <link href="../static/css/post-сreate.css" rel="stylesheet">
</head>
<body>
<script>
    function uploadFile() {
        var fileInput = document.getElementById("fileInput");
        var file = fileInput.files[0];
        var postId = window.location.pathname.split('/').pop();


        if (file) {
            var formData = new FormData();
            formData.append("file", file);
            formData.append("postId", postId);
            $.ajax({
                url: '/file-upload-post',
                type: 'POST',
                data: formData,
                processData: false,
                contentType: false,
                headers: {
                    'X-CSRF-TOKEN': document.querySelector('input[name="_csrf"]').value
                },
                success: function (response) {
                    alert("Фотография успешно загружена!");
                },
                error: function (xhr) {
                    alert('Ошибка загружки файла: ' + xhr.responseText);
                }
            });
        } else {
            alert("Please select a file.");
        }
    }
</script>
<div class="container">
    <h4 class="title">Редактирование поста</h4>
    <form class="file-upload" id="uploadFileForm" enctype="multipart/form-data">
        <input type="file" name="file" id="fileInput">
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <input type="button" value="File Upload" onclick="uploadFile()" class="submit-button">
    </form>
    <form id="editPostForm">
        <label for="postTitle">Заголовок:</label><br>
        <input type="text" id="postTitle" name="postTitle" class="form-control"><br>
        <label for="postContent">Описание:</label><br>
        <textarea id="postContent" name="postContent" class="form-control"></textarea><br>
        <label for="postPrice">Цена:</label><br>
        <input type="number" id="postPrice" name="postPrice" class="form-control"><br>
        <label for="postCity">Город:</label><br>
        <input type="text" id="postCity" name="postCity" class="form-control"><br>
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <input type="submit" value="Сохранить изменения" class="submit-button">
    </form>
</div>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
    $(document).ready(function () {
        var postId = window.location.pathname.split('/').pop();
        $.get("/api/post/" + postId, function (post) {
            $("#postTitle").val(post.title);
            $("#postContent").text(post.content);
            $("#postPrice").val(post.price);
            $("#postCity").val(post.city);
        });
        $("#editPostForm").submit(function (e) {
            e.preventDefault()
            var formData = {
                title: $('#postTitle').val(),
                content: $('#postContent').val(),
                price: $('#postPrice').val(),
                city: $('#postCity').val()
            };
            updatePost(postId, formData);
        });

        function updatePost(postId, formData) {
            $.ajax({
                type: 'PUT',
                url: '/api/post/' + postId,
                data: JSON.stringify(formData),
                contentType: 'application/json',
                headers: {
                    'X-CSRF-TOKEN': document.querySelector('input[name="_csrf"]').value
                },
                success: function (response) {
                    alert(response)
                    window.location.href = '/post-info/' + postId;
                },
                error: function (xhr) {
                    alert('Ошибка: ' + xhr.responseText);
                }
            })
        }
    });
</script>
</body>
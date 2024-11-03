<!DOCTYPE html>
<html lang="en">
<head>
    <title>Catalog</title>
    <meta name="_csrf" content="${_csrf.token}">
    <link rel="stylesheet" href="static/css/posts.css">
</head>
<body>
<#include "menu.ftlh">
<div class="container">
    <h4>Все товары площадки</h4>
    <form id="searchForm" class="search-form">
        <input type="text" id="searchInput" name="title" placeholder="Поиск по названию объявления">
        <input type="submit" value="Поиск">
    </form>
    <div id="postsContainer" class="container-posts"></div>
</div>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
    $(document).ready(function () {
        loadAllPosts();

        $('#searchForm').on('submit', function (e) {
            e.preventDefault();
            var searchText = $('#searchInput').val();
            loadAllPosts(searchText);
        });

        var urlParams = new URLSearchParams(window.location.search);
        var searchText = urlParams.get('title');
        if (searchText) {
            $('#searchInput').val(searchText);
            loadAllPosts(searchText);
        }

        function loadAllPosts(searchText) {
            $.get("/api/post", {title: searchText}, function (posts) {
                var $postsContainer = $("#postsContainer");
                $postsContainer.empty();
                $.each(posts, function (index, post) {
                    var postPhotoHtml = "";
                    if (post.photo && post.photo.id) {
                        postPhotoHtml = "<img class='photo' alt='IMAGE' src='/files/" + post.photo.id + "' style='border:2px solid black; border-radius: 20px' height='200'/>";
                    } else {
                        postPhotoHtml = "<img class='photo' alt='IMAGE' src='/static/photo/no-photo.jpg' style='border:2px solid black; border-radius: 20px' height='200'/>";
                    }
                    $postsContainer.append("<div id='post-" + post.id + "'>" +
                        "<div class='post-photo'>" + postPhotoHtml + "</div>" +
                        "<p><b>" + post.title + "</b> " + post.price + " руб. | " +
                        "<a href='/post-info/" + post.id + "'>Подробнее...</a></p>" +
                        "<button class='toggle-favorite-button' data-postid='" + post.id + "'>Избранное</button>" +
                        "</div>");
                });
            });
        }

        $(document).on('click', '.toggle-favorite-button', function () {
            var postId = $(this).data('postid');
            toggleFavorite(postId);
        });

        function toggleFavorite(postId) {
            $.ajax({
                type: 'POST',
                url: '/api/post/' + postId + '/toggle-favorite',
                headers: {
                    'X-CSRF-TOKEN': document.querySelector('meta[name="_csrf"]').getAttribute('content')
                },
                success: function (response) {
                    alert(response);
                },
                error: function (xhr) {
                    alert('Ошибка: ' + xhr.responseText);
                }
            });
        }
    });
</script>
</body>
</html>

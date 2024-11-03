<!DOCTYPE html>
<html>
<head>
    <title>Избранные посты</title>
    <meta name="_csrf" content="${_csrf.token}">
    <link rel="stylesheet" href="../static/css/posts.css">
</head>
<body>
<#include "menu.ftlh">
<div class="container-posts" id="favoritePostsContainer">
</div>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
    $(document).ready(function () {
        loadFavoritePosts();

        function loadFavoritePosts() {
            $.get("/api/post/favorites", function (favoritePosts) {
                var $postsContainer = $("#favoritePostsContainer");
                $postsContainer.empty();
                $.each(favoritePosts, function (index, post) {
                    var postPhotoHtml = "";
                    if (post.photo && post.photo.id) {
                        postPhotoHtml = "<img class='photo' alt='IMAGE' src='/files/" + post.photo.id + "' style='border:2px solid black; border-radius: 20px' height='200'/>";
                    } else {
                        postPhotoHtml = "<img class='photo' alt='IMAGE' src='/static/photo/no-photo.jpg' style='border:2px solid black; border-radius: 20px' height='200'/>";
                    }
                    $postsContainer.append("<div class='post' id='post-" + post.id + "'>" +
                        "<div class='post-photo'>" + postPhotoHtml + "</div>" +
                        "<p><b>" + post.title + "</b> " + post.price + " руб. | " +
                        "<a href='/post-info/" + post.id + "'>Подробнее...</a></p>" +
                        "<button class='toggle-favorite-button' data-postid='" + post.id + "'>Удалить из избранного</button>" +
                        "</div>");
                });
            });
        }

        $(document).on('click', '.toggle-favorite-button', function () {
            var postId = $(this).data('postid');
            toggleFavorite(postId);
        });

        function toggleFavorite(postId) {
            var csrfToken = $('meta[name="_csrf"]').attr('content');
            $.ajax({
                type: 'POST',
                data: {_csrf: csrfToken},
                url: '/api/post/' + postId + '/toggle-favorite',
                success: function (response) {
                    alert(response);
                    $('#post-' + postId).remove();
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

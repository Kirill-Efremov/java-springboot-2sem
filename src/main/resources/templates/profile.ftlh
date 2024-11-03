<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Сatalog</title>
    <link href="../static/css/profile.css" rel="stylesheet">
    <link href="../static/css/posts.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <#include "menu.ftlh">
    <div class="container-favourite">
        <div id="profile" class="white-container">
            <div class="user-info-text">
                <div class="post-photo">
                    <#if user.avatar??>
                        <img class="photo" alt="IMAGE" src="files/${user.avatar.id}"  style="border:2px solid black; border-radius: 20px" height="200"/>
                    <#else>
                        <img class="photo" alt="IMAGE" src="../static/photo/no-photo.jpg"  style="border:2px solid black; border-radius: 20px" height="200"/>
                    </#if>
                </div>
                <div class="user-info"> Fist name: ${user.name}</div>
                <div class="user-info">Number phone: ${user.numberPhone}</div>
                <div class="user-info">Email: ${user.email}</div>
            </div>
        </div>
        <div class="container-posts">
            <div class="my-posts-title">
                <h2>My Posts</h2>
            </div>
            <#if posts?size == 0>
                <p>Вы еще не создали объявление</p>
            <#else>
                <div class="index-section">
                    <#list posts as postDto>
                        <div class="post">
                            <div class="post-photo">
                                <#if postDto.photo??>
                                    <#if postDto.photo.id != 0 >
                                        <img class="photo" alt="IMAGE" src="files/${postDto.photo.id}"  style="border:2px solid black; border-radius: 20px" height="120"/>
                                    <#else>
                                        <img class="photo" alt="IMAGE" src="/static/photo/no-photo.jpg"  style="border:2px solid black; border-radius: 20px" height="120"/>
                                    </#if>
                                <#else>
                                    <img class="photo" alt="IMAGE" src="/static/photo/no-photo.jpg"  style="border:2px solid black; border-radius: 20px" height="120"/>
                                </#if>
                            </div>
                            <div>
                                <a class="logo_text_h2">Price: </a>${postDto.price}
                            </div>
                            <div>
                                <a class="logo_text_h2">Title: </a>${postDto.title}
                            </div>
                            <div>
                                <a class="logo_text_h2">Content:</a>${postDto.content}
                            </div>
                            <p><a href="/post-info/${postDto.id}">Подробнее</a></p>
                            <p><a href="/post-edit/${postDto.id}">Редактировать пост</a></p>
                            <form class="delete-post-form" data-postid="${postDto.id}">
                                <input type="hidden" value="${_csrf.token}" name="_csrf">
                                <input type="submit" class="delete-button" value="Delete my post">
                            </form>
                        </div>
                    </#list>
                </div>
            </#if>
        </div>
    </div>
</div>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
    $(document).ready(function () {
        $('.delete-post-form').on('submit', function (e) {
            e.preventDefault();
            var postId = $(this).data('postid');
            var csrfToken = $(this).find('input[name="_csrf"]').val();
            $.ajax({
                type: 'DELETE',
                url: '/api/post/' + postId,
                data: {_csrf: csrfToken},
                success: function (response) {
                    alert(response);
                    $('.delete-post-form[data-postid="' + postId + '"]').closest('.post').remove();
                },
                error: function () {
                    alert('Ошибка при удалении поста');
                }
            });
        });
    });
</script>
</body>
</html>

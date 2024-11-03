<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Catalog</title>
    <link href="../static/css/menu.css" rel="stylesheet">
</head>
<body>
<div class="all_header_menu">
    <div class="Logo">CATALOG</div>
    <div class="top_menu">
        <ul>
            <li id="posts"><a href="/posts">
                    <div class="button_text">Объявления</div>
                </a></li>
            <li id="add"><a href="/post/create">
                    <div class="button_text">Добавить пост</div>
                </a></li>
            <li id="favourite"><a href="/posts/favourite">
                    <div class="button_text">Избарнное</div>
                </a></li>
            <li id="prof"><a href="/profile">
                    <div class="button_text">Профиль</div>
                </a></li>
            <li id="id"><a href="/settings">
                    <div class="button_text">Настройки</div>
                </a></li>
            <li id="ex">
                <form action="/logout" id="logout" method="post">
                    <input type="hidden" name="_csrf" value="${_csrf.token}">
                    <input type="submit" value="Выйти"/>
                </form>
            </li>
        </ul>
    </div>
</div>
</body>
</html>

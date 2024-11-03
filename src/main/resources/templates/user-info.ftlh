<!DOCTYPE html>
<html>
<head>
    <title>Сatalog</title>
</head>
<body>
<h3>Подробная информация о пользователе ${user.name}</h3>
<b>Номер телефона: </b> ${user.numberPhone}<br>
<b>Email: </b> ${user.email}<br>
<h4>Товары пользователя ${user.name}</h4>
<#list posts as post>
    <div>
        <p><b>${post.title}</b> ${post.price} руб. | <a href="/post-info/${post.id}">Подробнее...</a></p>
    </div>
<#else>
    <h3>Товаров нет</h3>
</#list>
<#if errorMessage??>
    <h2 style="color: red">${errorMessage}</h2>
</#if>
</body>
</html>
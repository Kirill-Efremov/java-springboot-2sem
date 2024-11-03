<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Catalog</title>
</head>
<body>
<h4>Регистрация</h4>
<form action="/registration" method="post" class="form-container">
    <label for="name">Имя пользователя:</label>
    <input type="text" id="name" name="name"><br><br>
    <label for="email">Эл. почта:</label>
    <input type="email" id="email" name="email"><br><br>
    <label for="numberPhone">Номер телефона:</label>
    <input type="text" id="numberPhone" name="numberPhone"><br><br>
    <label for="password">Пароль:</label>
    <input type="password" id="password" name="password"><br><br>
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <input type="submit" value="Зарегистрироваться" class="submit-button"/>
</form>
<a href="/login">Войти</a>
<#if message?? >
    <h2 class="error-message">${message}</h2>
</#if>
</body>
</html>

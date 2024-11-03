<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Catalog</title>
</head>
<body>
<h4>Авторизация</h4>
<form action="/login" method="post" enctype="multipart/form-data" class="form-container">
    <label for="username">Эл. почта:</label>
    <input type="email" id="username" name="username" value="2@2"><br><br>
    <label for="password">Пароль:</label>
    <input type="password" id="password" name="password" value="2"><br><br>
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <input type="submit" value="Вход" class="submit-button"/>
</form>
<a href="/registration">Зарегистрироваться</a>
<a href="/recovery/forgot-password">Забыли пароль?</a>
</body>
</html>

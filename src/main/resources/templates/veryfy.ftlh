<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Подтверждение Email</title>
</head>
<body>
<#if message??>
    <p>${message}</p>
</#if>
<form action="/verify" method="post">
    <input name="email" value="${user.email}">
    <label for="code">Код подтверждения:</label>
    <input type="text" id="code" name="code" required><br>
    <button type="submit">Подтвердить</button>
</form>
</body>
</html>

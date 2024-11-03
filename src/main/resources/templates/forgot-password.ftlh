<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Forgot Password</title>
</head>
<body>
<h1>Forgot Password</h1>
<form action="/recovery/send-reset-code" id="resetCodeForm" method="post">
    <label for="email">Enter your email:</label><br>
    <input type="email" id="email" name="email" required><br><br>
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <button type="submit">Отправить код восстановления</button>
</form>
</body>
</html>

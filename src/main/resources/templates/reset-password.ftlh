<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Reset Password</title>
</head>
<body>
<h1>Reset Password</h1>
<p>Enter your new password:</p>
<form action="/recovery/reset-password" method="post">
    <label for="newPassword">New Password:</label><br>
    <input type="hidden" name="email" value=${email}>
    <input type="password" id="newPassword" name="newPassword" required><br><br>
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <button type="submit">Reset Password</button>
</form>
<#if error??>
    <div style="color: red">${error}</div>
</#if>
</body>
</html>

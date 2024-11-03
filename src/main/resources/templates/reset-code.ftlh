<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Reset Code Verification</title>
</head>
<body>
<h1>Reset Code Verification</h1>
<form action="/recovery/verify-reset-code" method="post">
    <input type="hidden" name="email" value=${email}>
    <label for="resetCode">Enter the verification code:</label><br>
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <input type="text" id="resetCode" name="resetCode" required><br><br>
    <button type="submit">Verify Code</button>
</form>
</body>
</html>

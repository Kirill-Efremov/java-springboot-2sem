<!DOCTYPE html>
<html>
<head>
    <title>Сatalog</title>
</head>
<body>
<#if errorMessage??>
    <script>alert("${errorMessage}");</script>
</#if>
<h3>Редактирование пользователя ${user.name}</h3>
<form action="/admin/user/edit" method="post">
    <#list roles as role>
        <#if role != 'ROLE_SUPER_ADMIN'>
            <div>
                <label><input type="radio" name="selectedRole" value="${role}" ${user.roles?seq_contains(role)?string("checked", "")}>${role}</label>
            </div>
        </#if>
    </#list>
    <input type="hidden" value="${user.id}" name="userId">
    <input type="hidden" value="${_csrf.token}" name="_csrf">
    <button type="submit">Сохранить</button>
</form>
</body>
</html>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Все пользователи</title>
    <style>
        table { border-collapse: collapse; width: 100%; margin: 20px 0; }
        th, td { border: 1px solid #ddd; padding: 12px; text-align: left; }
        th { background-color: #f2f2f2; }
        tr:nth-child(even) { background-color: #f9f9f9; }
        .error { color: red; background-color: #ffe6e6; padding: 10px; border: 1px solid red; }
        .aButton {
            border: 2px solid black;
            display: inline-block;
            padding: 10px 20px;
            margin: 5px;
            background-color: white;
            color: black;
            text-decoration: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
        }
    </style>
</head>
<body>
<h1>Все пользователи</h1>

<#if error??>
    <div class="error">
        <strong>Ошибка:</strong> ${error}
    </div>
</#if>

<a href="/computerclub/admin" class="aButton">Домой</a>
<a href="addUser" class="aButton">Добавить пользователя</a>
<table>
    <tr>
        <th>ID</th>
        <th>Имя пользователя</th>
        <th>Телефон</th>
        <th>Email</th>
        <th>Имя</th>
        <th>Фамилия</th>
        <th>Пол</th>
        <th>День Рождения</th>
        <th>Роль</th>
        <th>Дата создания</th>
        <th>Дата обновления</th>
        <th>Действие</th>
    </tr>

    <#list users as user>
        <tr>
            <td>${user.id}</td>
            <td>${user.username}</td>
            <td>${user.phoneNumber}</td>
            <td>${user.email}</td>
            <td>${user.firstName!''}</td>
            <td>${user.lastName!''}</td>
            <td>${user.gender!''}</td>
            <td>${user.birthday!''}</td>
            <td>${user.role!''}</td>
            <td>${user.createdAt}</td>
            <td>${user.updatedAt}</td>
            <td><a href='userShowOne?id=${user.id}'>Изменить</a></td>
        </tr>
    </#list>
</table>

</body>
</html>
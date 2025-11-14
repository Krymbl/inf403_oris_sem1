<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Все компьютеры</title>
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
<h1>Все компьютеры</h1>

<#if error??>
    <div class="error">
        <strong>Ошибка:</strong> ${error}
    </div>
</#if>

<a href="/computerclub/admin" class="aButton">Домой</a>
<a href="addComputer" class="aButton">Добавить компьютер</a>

<table>
    <tr>
        <th>ID</th>
        <th>Название</th>
        <th>Процессор</th>
        <th>Оперативная память (GB)</th>
        <th>Видеокарта</th>
        <th>Дата создания</th>
        <th>Дата обновления</th>
        <th>Действие</th>
    </tr>

    <#list computers as computer>
        <tr>
            <td>${computer.id}</td>
            <td>${computer.name}</td>
            <td>${computer.cpu}</td>
            <td>${computer.ram}</td>
            <td>${computer.videoCard}</td>
            <td>${computer.createdAt}</td>
            <td>${computer.updatedAt}</td>
            <td><a href='computerShowOne?id=${computer.id}'>Изменить</a></td>
        </tr>
    </#list>
</table>

</body>
</html>
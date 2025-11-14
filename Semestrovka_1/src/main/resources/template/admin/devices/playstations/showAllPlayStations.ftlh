<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Все PlayStation</title>
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
<h1>Все PlayStation</h1>

<#if error??>
    <div class="error">
        <strong>Ошибка:</strong> ${error}
    </div>
</#if>

<a href="/computerclub/admin" class="aButton">Домой</a>
<a href="addPlayStation" class="aButton">Добавить PlayStation</a>

<table>
    <tr>
        <th>ID</th>
        <th>Модель</th>
        <th>Версия</th>
        <th>Дата создания</th>
        <th>Дата обновления</th>
        <th>Действие</th>
    </tr>

    <#list playStations as playStation>
        <tr>
            <td>${playStation.id}</td>
            <td>${playStation.model}</td>
            <td>${playStation.version}</td>
            <td>${playStation.createdAt}</td>
            <td>${playStation.updatedAt}</td>
            <td><a href='playStationShowOne?id=${playStation.id}'>Изменить</a></td>
        </tr>
    </#list>
</table>

</body>
</html>
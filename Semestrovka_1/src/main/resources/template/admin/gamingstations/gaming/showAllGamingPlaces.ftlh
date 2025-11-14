<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Все игровые места</title>
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
        .available { color: green; }
        .not-available { color: red; }
    </style>
</head>
<body>
<h1>Все игровые места</h1>

<#if error??>
    <div class="error">
        <strong>Ошибка:</strong> ${error}
    </div>
</#if>

<a href="/computerclub/admin" class="aButton">Домой</a>
<a href="addGamingPlace" class="aButton">Добавить игровое место</a>

<table>
    <tr>
        <th>ID</th>
        <th>Название</th>
        <th>Тип</th>
        <th>Категория</th>
        <th>Цена за час</th>
        <th>Статус</th>
        <th>Дата создания</th>
        <th>Дата обновления</th>
        <th>Действие</th>
    </tr>

    <#list gamingPlaces as gamingPlace>
        <tr>
            <td>${gamingPlace.id}</td>
            <td>${gamingPlace.name}</td>
            <td>${gamingPlace.type}</td>
            <td>${gamingPlace.category}</td>
            <td>${gamingPlace.pricePerHour}</td>
            <td class="${gamingPlace.available?string('available', 'not-available')}">
                ${gamingPlace.available?string('Доступно', 'Не доступно')}
            </td>
            <td>${gamingPlace.createdAt}</td>
            <td>${gamingPlace.updatedAt}</td>
            <td><a href='gamingPlaceShowOne?id=${gamingPlace.id}'>Изменить</a></td>
        </tr>
    </#list>
</table>

</body>
</html>
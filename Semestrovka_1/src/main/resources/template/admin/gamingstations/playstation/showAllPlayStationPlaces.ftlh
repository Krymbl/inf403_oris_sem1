<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Все PlayStation места</title>
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
<h1>Все PlayStation места</h1>

<#if error??>
    <div class="error">
        <strong>Ошибка:</strong> ${error}
    </div>
</#if>

<a href="/computerclub/admin" class="aButton">Домой</a>
<a href="addPlayStationPlace" class="aButton">Добавить плейстейшн место</a>

<table>
    <tr>
        <th>ID</th>
        <th>ID игрового места</th>
        <th>Телевизор</th>
        <th>Макс. игроков</th>
        <th>Дата создания</th>
        <th>Дата обновления</th>
        <th>Действие</th>
    </tr>

    <#list playStationPlaces as playStationPlace>
        <tr>
            <td>${playStationPlace.id}</td>
            <td>${playStationPlace.gamingPlaceId}</td>
            <td>${playStationPlace.tv}</td>
            <td>${playStationPlace.maxPlayers}</td>
            <td>${playStationPlace.createdAt}</td>
            <td>${playStationPlace.updatedAt}</td>
            <td><a href='playStationPlaceShowOne?id=${playStationPlace.id}'>Изменить</a></td>
        </tr>
    </#list>
</table>

</body>
</html>
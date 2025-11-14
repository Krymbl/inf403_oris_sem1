<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Все бронирования</title>
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
        .status-active { color: green; font-weight: bold; }
        .status-completed { color: blue; }
        .status-cancelled { color: red; }
    </style>
</head>
<body>
<h1>Все бронирования</h1>

<#if error??>
    <div class="error">
        <strong>Ошибка:</strong> ${error}
    </div>
</#if>

<a href="/computerclub/admin" class="aButton">Домой</a>
<a href="addReservation" class="aButton">Добавить бронирование</a>

<table>
    <tr>
        <th>ID</th>
        <th>ID пользователя</th>
        <th>ID игрового места</th>
        <th>Номер пользователя</th>
        <th>Время начала</th>
        <th>Время окончания</th>
        <th>Цена</th>
        <th>Статус</th>
        <th>Дата создания</th>
        <th>Дата обновления</th>
        <th>Действие</th>
    </tr>

    <#list reservations as reservation>
        <tr>
            <td>${reservation.id}</td>
            <td>${reservation.userId}</td>
            <td>${reservation.gamePlaceId}</td>
            <td>${reservation.personPhoneNumber}</td>
            <td>${reservation.startTime}</td>
            <td>${reservation.endTime}</td>
            <td>${reservation.totalPrice}</td>
            <td class="status-${reservation.status?lower_case}">${reservation.status}</td>
            <td>${reservation.createdAt}</td>
            <td>${reservation.updatedAt}</td>
            <td><a href='reservationShowOne?id=${reservation.id}'>Изменить</a></td>
        </tr>
    </#list>
</table>

</body>
</html>
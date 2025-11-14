<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Все назначения PlayStation</title>
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
        .active { color: green; font-weight: bold; }
        .inactive { color: gray; }
    </style>
</head>
<body>
<h1>Все назначения PlayStation</h1>

<#if error??>
    <div class="error">
        <strong>Ошибка:</strong> ${error}
    </div>
</#if>

<a href="/computerclub/admin" class="aButton">Домой</a>
<a href="addPlayStationAssignment" class="aButton">Добавить плейстейшн назначение</a>

<table>
    <tr>
        <th>ID</th>
        <th>ID PlayStation</th>
        <th>ID игрового(PS) места</th>
        <th>Время назначения</th>
        <th>Время снятия</th>
        <th>Статус</th>
        <th>Дата создания</th>
        <th>Дата обновления</th>
        <th>Действие</th>
    </tr>

    <#list playStationAssignments as assignment>
        <tr>
            <td>${assignment.id}</td>
            <td>${assignment.playStationId}</td>
            <td>${assignment.playStationPlaceId}</td>
            <td>${assignment.assignedAt}</td>
            <td>${assignment.unassignedAt!''}</td>
            <td class="${assignment.isActive?string('active', 'inactive')}">
                ${assignment.isActive?string('Активно', 'Неактивно')}
            </td>
            <td>${assignment.createdAt}</td>
            <td>${assignment.updatedAt}</td>
            <td><a href='playStationAssignmentShowOne?id=${assignment.id}'>Изменить</a></td>
        </tr>
    </#list>
</table>

</body>
</html>
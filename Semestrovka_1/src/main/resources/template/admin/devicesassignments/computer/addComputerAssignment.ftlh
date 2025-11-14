<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Добавить назначение компьютера</title>
    <style>
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
        .error {
            color: red;
            background-color: #ffe6e6;
            padding: 10px;
            border: 1px solid red;
            margin: 10px 0;
        }
    </style>
</head>
<body>
<h1>Добавить назначение компьютера</h1>
<a class="aButton" href="computerAssignments">Вернуться к списку</a>
<br><br>

<#if error??>
    <div class="error">
        <strong>Ошибка:</strong> ${error}
    </div>
</#if>

<form method='post' action='addComputerAssignment'>
    ID компьютера: <input type='number' name='computerId' value='' required><br><br>
    ID компьютерного места: <input type='number' name='computerPlaceId' value='' required><br><br>
    Время назначения: <input type='datetime-local' name='assignedAt' value='' required><br><br>
    Время снятия: <input type='datetime-local' name='unassignedAt' value=''><br><br>

    Активно:
    <input type='checkbox' name='isActive' value='true' checked> Да<br><br>
    <input type='submit' class="aButton" value='Добавить назначение'>
</form>

<script>
    const urlParams = new URLSearchParams(window.location.search);
    if (urlParams.has('error')) {
        const errorMessage = decodeURIComponent(urlParams.get('error'));
        alert('Ошибка при создании назначения: ' + errorMessage);
    }
    if (urlParams.has('success')) {
        alert('Назначение успешно добавлено!');
    }
</script>
</body>
</html>
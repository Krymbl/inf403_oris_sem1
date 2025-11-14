<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Изменить PlayStation</title>
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
    </style>
</head>
<body>
<h1>Изменить PlayStation</h1>
<a href="playstations" class="aButton">Вернуться к списку</a>
<br><br>

<form method='post' action='playStationShowOne'>
    <input type='hidden' name='id' value='${playStation.id}'>
    Модель: <input type='text' name='model' value='${playStation.model}' required><br><br>
    Версия: <input type='text' name='version' value='${playStation.version}' required><br><br>
    <input type='submit' value='Сохранить изменения' class="aButton">
    <button type="button" onclick="confirmDelete(${playStation.id}, '${playStation.model} ${playStation.version}')" class="aButton">Удалить PlayStation</button>
</form>

<script>
    function confirmDelete(playStationId, playStationName) {
        if (confirm(`Вы уверены, что хотите удалить PlayStation ` + playStationName + ` (ID:` + playStationId + `)?`)) {
            const form = document.createElement('form');
            form.method = 'POST';
            form.action = 'deletePlayStation';

            const playStationIdInput = document.createElement('input');
            playStationIdInput.type = 'hidden';
            playStationIdInput.name = 'id';
            playStationIdInput.value = playStationId;

            form.appendChild(playStationIdInput);
            document.body.appendChild(form);
            form.submit();
        }
    }

    const urlParams = new URLSearchParams(window.location.search);
    if (urlParams.has('error')) {
        const errorMessage = decodeURIComponent(urlParams.get('error'));
        alert('Ошибка при сохранении PlayStation: ' + errorMessage);
    }

    if (urlParams.has('success')) {
        alert('PlayStation успешно обновлен!');
    }
</script>
</body>
</html>
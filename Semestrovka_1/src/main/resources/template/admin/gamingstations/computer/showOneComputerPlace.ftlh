<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Изменить компьютерное место</title>
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
<h1>Изменить компьютерное место</h1>
<a href="computerPlaces" class="aButton">Вернуться к списку</a>
<br><br>

<form method='post' action='computerPlaceShowOne'>
    <input type='hidden' name='id' value='${computerPlace.id}'>
    ID игрового места: <input type='number' name='gamingPlaceId' value='${computerPlace.gamingPlaceId}' required><br><br>
    Клавиатура: <input type='text' name='keyboard' value='${computerPlace.keyboard}' required><br><br>
    Мышь: <input type='text' name='mouse' value='${computerPlace.mouse}' required><br><br>
    Наушники: <input type='text' name='headset' value='${computerPlace.headset}' required><br><br>
    Монитор: <input type='text' name='monitor' value='${computerPlace.monitor}' required><br><br>
    Стул: <input type='text' name='chair' value='${computerPlace.chair}' required><br><br>
    <input type='submit' value='Сохранить изменения' class="aButton">
    <button type="button" onclick="confirmDelete(${computerPlace.id})" class="aButton">Удалить компьютерное место</button>
</form>

<script>
    function confirmDelete(computerPlaceId) {
        if (confirm(`Вы уверены, что хотите удалить компьютерное место (ID:` + computerPlaceId + `)?`)) {
            const form = document.createElement('form');
            form.method = 'POST';
            form.action = 'deleteComputerPlace';

            const computerPlaceIdInput = document.createElement('input');
            computerPlaceIdInput.type = 'hidden';
            computerPlaceIdInput.name = 'id';
            computerPlaceIdInput.value = computerPlaceId;

            form.appendChild(computerPlaceIdInput);
            document.body.appendChild(form);
            form.submit();
        }
    }

    const urlParams = new URLSearchParams(window.location.search);
    if (urlParams.has('error')) {
        const errorMessage = decodeURIComponent(urlParams.get('error'));
        alert('Ошибка при сохранении компьютерного места: ' + errorMessage);
    }

    if (urlParams.has('success')) {
        alert('Компьютерное место успешно обновлено!');
    }
</script>
</body>
</html>
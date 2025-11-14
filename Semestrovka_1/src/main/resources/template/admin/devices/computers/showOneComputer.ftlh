<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Изменить компьютер</title>
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
<h1>Изменить компьютер</h1>
<a href="computers" class="aButton">Вернуться к списку</a>
<br><br>

<form method='post' action='computerShowOne'>
    <input type='hidden' name='id' value='${computer.id}'>
    Название: <input type='text' name='name' value='${computer.name}' required><br><br>
    Процессор: <input type='text' name='cpu' value='${computer.cpu}' required><br><br>
    Оперативная память (GB): <input type='number' name='ram' value='${computer.ram}' required><br><br>
    Видеокарта: <input type='text' name='videoCard' value='${computer.videoCard}' required><br><br>
    <input type='submit' value='Сохранить изменения' class="aButton">
    <button type="button" onclick="confirmDelete(${computer.id}, '${computer.name}')" class="aButton">Удалить компьютер</button>
</form>

<script>
    function confirmDelete(computerId, computerName) {
        if (confirm(`Вы уверены, что хотите удалить компьютер ` + computerName + ` c (ID: ` + computerId + `)?`)) {
            const form = document.createElement('form');
            form.method = 'POST';
            form.action = 'deleteComputer';

            const computerIdInput = document.createElement('input');
            computerIdInput.type = 'hidden';
            computerIdInput.name = 'id';
            computerIdInput.value = computerId;

            form.appendChild(computerIdInput);
            document.body.appendChild(form);
            form.submit();
        }
    }

    const urlParams = new URLSearchParams(window.location.search);
    if (urlParams.has('error')) {
        const errorMessage = decodeURIComponent(urlParams.get('error'));
        alert('Ошибка при сохранении компьютера: ' + errorMessage);
    }

    if (urlParams.has('success')) {
        alert('Компьютер успешно обновлен!');
    }
</script>
</body>
</html>
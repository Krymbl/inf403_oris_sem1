<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Изменить назначение компьютера</title>
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
<h1>Изменить назначение компьютера</h1>
<a href="computerAssignments" class="aButton">Вернуться к списку</a>
<br><br>

<form method='post' action='computerAssignmentShowOne'>
    <input type='hidden' name='id' value='${computerAssignment.id}'>
    ID компьютера: <input type='number' name='computerId' value='${computerAssignment.computerId}' required><br><br>
    ID компьютерного места: <input type='number' name='computerPlaceId' value='${computerAssignment.computerPlaceId}' required><br><br>
    Время назначения: <input type='datetime-local' name='assignedAt' value='${computerAssignment.assignedAt?string("yyyy-MM-dd'T'HH:mm")}' required><br><br>
    Время снятия: <input type='datetime-local' name='unassignedAt' value='${(computerAssignment.unassignedAt?string("yyyy-MM-dd'T'HH:mm"))!''}'><br><br>
    Активно:
    <input type='radio' name='isActive' value='true' ${computerAssignment.isActive?string('checked', '')}> Да
    <input type='radio' name='isActive' value='false' ${computerAssignment.isActive?string('', 'checked')}> Нет
    <br><br>
    <input type='submit' value='Сохранить изменения' class="aButton">
    <button type="button" onclick="confirmDelete(${computerAssignment.id})" class="aButton">Удалить назначение</button>
</form>

<script>
    function confirmDelete(assignmentId) {
        if (confirm(`Вы уверены, что хотите удалить назначение (ID:` + assignmentId + `)?`)) {
            const form = document.createElement('form');
            form.method = 'POST';
            form.action = 'deleteComputerAssignment';

            const assignmentIdInput = document.createElement('input');
            assignmentIdInput.type = 'hidden';
            assignmentIdInput.name = 'id';
            assignmentIdInput.value = assignmentId;

            form.appendChild(assignmentIdInput);
            document.body.appendChild(form);
            form.submit();
        }
    }

    const urlParams = new URLSearchParams(window.location.search);
    if (urlParams.has('error')) {
        const errorMessage = decodeURIComponent(urlParams.get('error'));
        alert('Ошибка при сохранении назначения: ' + errorMessage);
    }

    if (urlParams.has('success')) {
        alert('Назначение успешно обновлено!');
    }
</script>
</body>
</html>
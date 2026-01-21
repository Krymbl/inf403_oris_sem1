<!-- 14. Написать метод сервлета и шаблон,
// в котором пользователь выбирает
// язык (ru, en, da), сохранение выбора в cookie -->

<html>
<head>
    <meta charset="UTF-8">
</head>
<body>
    <form method="post" action="language">
        <select name="language">
            <option value="ru" <#if language?? && language=="ru">selected</#if>>RU</option>
            <option value="en" <#if language?? && language=="en">selected</#if>>EN</option>
            <option value="da" <#if language?? && language=="da">selected</#if>>DA</option>
        </select>
        <button>Отправить</button>
    </form>
</body>
</html>
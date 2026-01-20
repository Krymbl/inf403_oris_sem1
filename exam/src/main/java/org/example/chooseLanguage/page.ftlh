<!DOCTYPE HTML>
<html>
<head>
    <title>Выбор языка</title>
</head>
<body>
    <div>
        <span>Выбор языка</span>
        <form method="post" action="chooseLanguage">
            <select name="language">
                <option value="RU" ${(savedLanguage=="RU")?then("selected", "")}>RU</option>
                <option value="EN" ${(savedLanguage=="EN")?then("selected", "")}>EN</option>
                <option value="DE" ${(savedLanguage=="DE")?then("selected", "")}>DE</option>
            </select>
            <button type="submit">Сохранить выбор</button>
        </form>
    </div>
</body>
</html>
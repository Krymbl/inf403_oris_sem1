<!--//Написать сервлет и jsp/freemarker для получения всех админов сайта.
// У каждого админа есть имя, роль, может быть или не быть аватарка -->
<html>
<head>
    <meta charset="UTF-8">
</head>
<body>
<div>
    <table>
        <tr>
            <th>Имя</th>
            <th>Роль</th>
            <th>Аватарка</th>
        </tr>
        <#list admins as admin>
            <tr>
                <td>${admin.name}</td>
                <td>${admin.role}</td>
                <td>
                <#if admin.img??>
                    <img src="${admin.img}" alt="фото">
                <#else>
                    <span>Нет аватарки</span>
                </#if>
                </td>
            </tr>
        </#list>
    </table>
</div>

</body>
</html>
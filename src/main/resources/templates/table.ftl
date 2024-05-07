<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Table</title>
</head>
<body>
    <h1>Title</h1>
    <table>
        <#list vals as val>
            <th>${val}</th>
        </#list>
        <#list vals1 as val1>
            <td>${val1}</td>
        </#list>
    </table>


</body>
</html>
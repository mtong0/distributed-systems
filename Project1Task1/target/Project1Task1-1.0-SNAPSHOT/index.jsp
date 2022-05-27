<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<br/>
<form action="compute-hashes" method="post">
    <label for="s"> The string to be hashed: </label> <input type="text" id="s" name="s">
    <input id="md5" type="radio" value="MD5" name="hash">  <label for="md5">MD5</label>
    <input id="sha256" type="radio" value="SHA-256" name="hash"> <label for="sha256">SHA-256</label>
    <input type="submit" value="submit">
</form>

</body>
</html>
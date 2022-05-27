<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!--https://en.wikipedia.org/wiki/XHTML_Mobile_Profile-->
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.2//EN"
"http://www.openmobilealliance.org/tech/DTD/xhtml-mobile12.dtd">
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<h1>Distributed Systems Class Clicker
</h1>
<br/>
<form action="submit-answer" method="post">
    <div>Submit your answer to the current question: </div>
    <input type="radio" id="A" name="ans" value="A">
    <label for="A">A</label><br>
    <input type="radio" id="B" name="ans" value="B">
    <label for="B">B</label><br>
    <input type="radio" id="C" name="ans" value="C">
    <label for="C">C</label><br>
    <input type="radio" id="D" name="ans" value="D">
    <label for="D">D</label><br>
    <input type="submit" value="submit">
</form>

</body>
</html>
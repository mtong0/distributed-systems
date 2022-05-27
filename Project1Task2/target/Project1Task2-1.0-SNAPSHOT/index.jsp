<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<h1><%= "Dog Finder" %></h1>
</br>
<b>Created By Muyu Tong</b>
<h1>Dog Breeds</h1>
<form action="dog-finder" method="post">
    <label for="breed">Choose a dog breed</label>
    <select name="breed" id="breed">
        <option value="Borzoi">Borzoi</option>
        <option value="Boxer">Boxer</option>
        <option value="Chihuahua">Chihuahua</option>
        <option value="Collie">Collie</option>
        <option value="Dachshund">Dachshund</option>
        <option value="Dalmatian">Dalmatian</option>
        <option value="Maltese">Maltese</option>
        <option value="Otterhound">Otterhound</option>
        <option value="Poodle">Poodle</option>
        <option value="Rottweiler">Rottweiler</option>
        <option value="Saluki">Saluki</option>
        <option value="Whippet">Whippet</option>
    </select>
    <br>
    <input type="submit" value="submit">
</form>

<br/>
</body>
</html>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Display Data</title>
    <style>
        body {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
        }
        .form-container {
            text-align: center;
            border: 1px solid #ccc;
            padding: 20px;
            border-radius: 10px;
            background-color: #fff;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        input[type="text"] {
            display: block;
            width: 80%;
            padding: 10px;
            margin: 10px auto;
            border-radius: 5px;
            border: 1px solid #ccc;
        }
        input[type="submit"] {
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            background-color: #007BFF;
            color: #fff;
            cursor: pointer;
        }
        input[type="submit"]:hover {
            background-color: #0056b3;
        }
        .submitted-data {
            margin-top: 20px;
        }
    </style>
</head>
<body>
    <div class="form-container">
        <h1>User Registration</h1>
        <form action="myservlet" method="post">
            ID: <input type="text" name="id"><br>
            First Name: <input type="text" name="firstName"><br>
            Last Name: <input type="text" name="lastName"><br>
            <input type="submit" value="Submit">
        </form>
        <div class="submitted-data">
            <h2>Submitted Data:</h2>
            <p>ID: ${id}</p>
            <p>First Name: ${firstName}</p>
            <p>Last Name: ${lastName}</p>
        </div>
    </div>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="ISO-8859-1">
    <title>Gestion des hôtels</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <style>
        body {
            background-color: #F5F5F5;
        }

        .container {
            margin-top: 20px;
        }

        .btn-custom {
            background-color: #DFD7BF;
            color: #3F2305;
            border: none;
        }

        .btn-custom:hover {
            background-color: #F2EAD3;
            color: #3F2305;
        }

        .display-4 {
            color: #3F2305;
        }
    </style>
</head>

<body>
    <div class="container mt-5">
        <h1 class="display-4 text-center mb-4">Gestion des hôtels</h1>
        <form action="VilleController" method="get" class="mb-3">
            <button type="submit" class="btn btn-custom btn-block">Gestion des villes</button>
        </form>

        <form action="HotelController" method="get" class="mb-3">
            <button type="submit" class="btn btn-custom btn-block">Gestion des hôtels</button>
        </form>
    </div>

    <!-- Add Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</body>

</html>

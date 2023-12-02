<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <meta charset="UTF-8">
    <title>Villes List</title>
    <style>
        body {
            background-color: #F5F5F5;
        }

        .container {
            margin-top: 20px;
        }

        .d-flex {
            background-color: #DFD7BF;
            padding: 10px;
            color: #3F2305;
            border-radius: 5px;
        }

        .custom-btn {
            background-color: #F2EAD3;
            border: none;
            color: #3F2305;
            padding: 10px 20px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            font-size: 16px;
            margin: 4px 2px;
            cursor: pointer;
            border-radius: 5px;
        }

        .thead-light th,
        .modal-header,
        .modal-footer {
            background-color: #DFD7BF;
            color: #3F2305;
        }

        .btn-danger,
        .btn-secondary {
            background-color: #3F2305;
            color: #F2EAD3;
            border: none;
        }

        .btn-primary {
            background-color: #DFD7BF;
            border: none;
        }

        .btn-primary:hover {
            background-color: #F5F5F5;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <a href="index.jsp" class="btn btn-warning">Accueil</a>
            <h1 class="display-4">Gestion des Villes</h1>
            <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#VilleModalCenter">
                Ajouter une ville
            </button>
        </div>

        <!-- Add Ville Modal -->
        <div class="modal fade" id="VilleModalCenter" tabindex="-1" role="dialog" aria-labelledby="VilleModalCenterTitle" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <form action="VilleController" method="post">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="VilleModalCenterTitle">Ajouter une ville</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <label class="custom-modal-label" for="Nom">Nom</label>
                            <input type="text" name="Nom" class="form-control" required><br><br>

                            <!-- Ajouter d'autres champs si nécessaire -->

                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Fermer</button>
                            <input type="submit" class="btn btn-primary" value="Enregistrer">
                        </div>
                    </div>
                </form>
            </div>
        </div>

        <!-- Modify Ville Modal -->
        <div class="modal fade" id="ModifyVilleModal" tabindex="-1" role="dialog" aria-labelledby="ModifyVilleModalTitle" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <form id="modifyForm" action="VilleController" method="post">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="ModifyVilleModalTitle">Modifier une ville</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <label class="custom-modal-label" for="Nom">Nom</label>
                            <input type="text" name="Nom" class="form-control" id="modalVilleNom" required><br><br>

                            <!-- Ajouter d'autres champs si nécessaire -->

                            <input type="hidden" name="action" value="edit">
                            <input type="hidden" name="id" id="modalVilleId">
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Fermer</button>
                            <button type="button" class="btn btn-primary" onclick="submitModifyForm()">Enregistrer les modifications</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>

        <table class="table">
            <thead class="thead-light">
                <tr>
                    <th>ID</th>
                    <th>Nom</th>
                    <!-- Ajouter d'autres en-têtes si nécessaire -->
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${villes}" var="ville">
                    <tr>
                        <td>${ville.id}</td>
                        <td>${ville.nom}</td>
                        <!-- Afficher d'autres attributs si nécessaire -->
                        <td class="d-flex align-items-center">
                            <form action="VilleController" method="post">
                                <input type="hidden" name="action" value="delete">
                                <input type="hidden" name="id" value="${ville.id}">
                                <button type="submit" class="btn btn-danger">Supprimer</button>
                            </form>
                            <button type="button" class="btn btn-secondary ml-2" data-toggle="modal" data-target="#ModifyVilleModal"
                                    data-ville-id="${ville.id}" data-ville-nom="${ville.nom}">
                                Modifier
                            </button>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>

    <script>
        $('#ModifyVilleModal').on('show.bs.modal', function (event) {
            var button = $(event.relatedTarget);
            var villeId = button.data('ville-id');
            var villeNom = button.data('ville-nom');
            // Ajouter d'autres attributs si nécessaire
            var modal = $(this);

            modal.find('#modalVilleNom').val(villeNom);
            modal.find('#modalVilleId').val(villeId);
            // Mettre à jour d'autres champs si nécessaire
        });

        function submitModifyForm() {
            document.getElementById("modifyForm").submit();
        }
    </script>
</body>
</html>

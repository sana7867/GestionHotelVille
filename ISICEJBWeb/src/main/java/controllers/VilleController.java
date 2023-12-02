package controllers;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import entities.Ville;
import dao.IDaoLocale;

@WebServlet("/VilleController")
public class VilleController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @EJB
    private IDaoLocale<Ville> ejb;

    public VilleController() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Ville> villes = ejb.findAll();
        request.getSession().setAttribute("villes", villes);
        request.getRequestDispatcher("ville.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("delete".equals(action)) {
            handleDeleteAction(request, response);
        } else if ("edit".equals(action)) {
            handleEditAction(request, response);
        } else {
            handleAddAction(request, response);
        }

        List<Ville> villes = ejb.findAll();
        request.getSession().setAttribute("villes", villes);
        response.sendRedirect(request.getContextPath() + "/VilleController");
    }

    private void handleDeleteAction(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParameter = request.getParameter("id");

        if (idParameter != null && !idParameter.isEmpty()) {
            int villeId = Integer.parseInt(idParameter);
            Ville villeToDelete = ejb.findById(villeId);

            if (villeToDelete != null) {
                boolean deleted = ejb.delete(villeToDelete);

                if (deleted) {
                    log("Ville supprimée avec succès");
                } else {
                    log("La suppression de la ville a échoué");
                }
            } else {
                log("Ville non trouvée avec l'ID : " + villeId);
            }
        } else {
            log("ID de la ville à supprimer non spécifié");
        }
    }
    private void handleEditAction(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParameter = request.getParameter("id");
        String nom = request.getParameter("Nom");

        if (idParameter == null || idParameter.isEmpty() || nom == null || nom.isEmpty()) {
            log("Paramètres de modification de la ville non spécifiés");
            return;
        }

        try {
            int villeId = Integer.parseInt(idParameter);
            Ville villeToEdit = ejb.findById(villeId);

            if (villeToEdit != null) {
                villeToEdit.setNom(nom);

                Ville updatedVille = ejb.update(villeToEdit);

                if (updatedVille != null) {
                    log("Ville modifiée avec succès, ID : " + updatedVille.getId());
                } else {
                    log("La modification de la ville a échoué");
                }
            } else {
                log("Ville non trouvée avec l'ID : " + villeId);
            }
        } catch (NumberFormatException e) {
            log("ID de la ville à modifier n'est pas un entier valide");
        }
    }



    private void handleAddAction(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nom = request.getParameter("Nom");

        if (nom != null && !nom.isEmpty()) {
            Ville nouvelleVille = new Ville();
            nouvelleVille.setNom(nom);

            Ville addedVille = ejb.create(nouvelleVille);

            if (addedVille != null) {
                log("Nouvelle ville ajoutée avec succès, ID : " + addedVille.getId());
            } else {
                log("L'ajout de la nouvelle ville a échoué");
            }
        } else {
            log("Nom de la nouvelle ville non spécifié");
        }
    }
}

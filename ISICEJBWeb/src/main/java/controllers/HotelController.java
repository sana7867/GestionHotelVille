package controllers;

import java.io.IOException;
import java.util.List;

import dao.HotelLocale;
import dao.IDaoLocale;
import entities.Hotel;
import entities.Ville;
import jakarta.ejb.EJB;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/HotelController")
public class HotelController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @EJB
    private HotelLocale hdao;

    @EJB
    private IDaoLocale vdao;

    public HotelController() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Hotel> hotelList = hdao.findAll();
        List<Ville> villeList = vdao.findAll();
        request.setAttribute("hotels", hotelList);
        request.setAttribute("villes", villeList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/hotel.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("delete".equals(action)) {
            // Set success message
            request.setAttribute("successMessage", "Hôtel supprimé avec succès!");
            int hotelId = Integer.parseInt(request.getParameter("id"));
            Hotel hotelToDelete = hdao.findById(hotelId);

            if (hotelToDelete != null) {
                hdao.delete(hotelToDelete);
            }
            response.sendRedirect(request.getContextPath() + "/HotelController");
        } else if ("edit".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            String nom = request.getParameter("nom");
            String adresse = request.getParameter("adresse");
            String telephone = request.getParameter("telephone");

            int villeId = Integer.parseInt(request.getParameter("ville"));
            Hotel hotelToEdit = hdao.findById(id);

            if (hotelToEdit != null) {
                hotelToEdit.setNom(nom);
                hotelToEdit.setAdresse(adresse);
                hotelToEdit.setTelephone(telephone);
                // Mise à jour de la ville
                Ville nouvelleVille = (Ville) vdao.findById(villeId);
                hotelToEdit.setVille(nouvelleVille);
                hdao.update(hotelToEdit);
            }
            response.sendRedirect(request.getContextPath() + "/HotelController");
        } else if ("filterByVille".equals(action)) {
            int villeId = Integer.parseInt(request.getParameter("filterVille"));

            if (villeId == 0) {
                List<Hotel> hotelList = hdao.findAll();
                request.setAttribute("hotels", hotelList);
            } else {
                Ville ville = (Ville) vdao.findById(villeId);
                String villeNom = ville.getNom();
                List<Hotel> hotelList = hdao.findByVille(villeNom);
                request.setAttribute("hotels", hotelList);
            }

            List<Ville> villeList = vdao.findAll();
            request.setAttribute("villes", villeList);

            request.getRequestDispatcher("/hotel.jsp").forward(request, response);
        } else {
            String nom = request.getParameter("nom");
            String adresse = request.getParameter("adresse");
            String telephone = request.getParameter("telephone");
            int villeId = Integer.parseInt(request.getParameter("ville"));

            Ville v = (Ville) vdao.findById(villeId);
            Hotel newHotel = new Hotel(nom, adresse, telephone, v);

            if (hdao.create(newHotel) != null) {
                // Set success message
                request.setAttribute("successMessage", "Nouvel hôtel ajouté avec succès!");
                List<Hotel> hotelList = hdao.findAll();
                List<Ville> villeList = vdao.findAll();

                request.setAttribute("hotels", hotelList);
                request.setAttribute("villes", villeList);
                request.getRequestDispatcher("/hotel.jsp").forward(request, response);
            } else {
                System.out.println("Failure: Hôtel non créé.");
            }
        }

    }
}
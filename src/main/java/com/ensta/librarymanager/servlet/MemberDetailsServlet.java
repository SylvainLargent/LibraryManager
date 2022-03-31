package com.ensta.librarymanager.servlet;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.service.impl.*;
import com.ensta.librarymanager.utils.Abonnement;
import com.ensta.librarymanager.modele.Emprunt;
import com.ensta.librarymanager.modele.Livre;
import com.ensta.librarymanager.modele.Membre;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/membre_details")
public class MemberDetailsServlet extends HttpServlet {
    LivreService livreService = LivreService.getInstance();
    MembreService membreService = MembreService.getInstance();
    EmpruntService empruntService = EmpruntService.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int idMembre = Integer.parseInt(request.getParameter("id"), 10);
        List<Emprunt> listEmprunt;
        try {
            Membre membre = membreService.getById(idMembre);
            request.setAttribute("membre", membre);
            // Affichage de la liste d'emprunt en cours
            listEmprunt = this.empruntService.getListCurrentByMembre(idMembre);
            List<Livre> listLivre = new ArrayList<>();
            for (Emprunt emprunt : listEmprunt) {
                listLivre.add(livreService.getById(emprunt.getIdLivre()));
            }
            request.setAttribute("listEmprunt", listEmprunt);
            request.setAttribute("listLivre", listLivre);
        } catch (ServiceException e) {
            e.printStackTrace(); // Permet de remonter les erreurs de la suite d'exceptions
            throw new ServletException();
        }
        this.getServletContext().getRequestDispatcher("/WEB-INF/View/membre_details.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        Abonnement abonnement = Abonnement.valueOf(request.getParameter("abonnement"));
        String adresse = request.getParameter("adresse");
        String email = request.getParameter("email");
        String telephone = request.getParameter("telephone");
        int idMembre = Integer.parseInt(request.getParameter("id"), 10);
        Membre membre = new Membre(idMembre, nom, prenom, adresse, email, telephone, abonnement);
        try {
            membreService.update(membre);
        } catch (ServiceException e) {
            e.printStackTrace();
            throw new ServletException();
        }
        this.doGet(request, response);
    }
}
